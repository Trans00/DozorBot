package com.dmgburg.dozor

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import static org.mockito.Mockito.*

class EnkounterWrapperTest {

    EncounterWrapper wrapper
    String userName ="enc_test_user"
    String password = "a123456789"
    String url = "http://moscow.en.cx/"
    @Mock CredentialsRepository credentialsRepository

    @Before
    void setUp(){
        MockitoAnnotations.initMocks(this)
        wrapper = new EncounterWrapper(credentialsRepository)
        when(credentialsRepository.url).thenReturn(url)
        when(credentialsRepository.userLogin).thenReturn(userName)
        when(credentialsRepository.userPassword).thenReturn(password)
    }

    @Test
    void "should login as test user"(){
        def html = wrapper.html
        Element parsed = Jsoup.parse(html)
        parsed?.getElementsByAttributeValue("id","boxUser")[0].text().contains(userName)
    }
}
