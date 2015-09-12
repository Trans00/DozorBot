package com.dmgburg.dozor

import com.dmgburg.dozor.configs.HtmlPublishingContext
import com.dmgburg.dozor.engine.TestJettyServer
import groovy.util.slurpersupport.NodeChild
import groovy.xml.StreamingMarkupBuilder
import org.cyberneko.html.parsers.SAXParser
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.Mock

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

import static com.dmgburg.dozor.configs.HtmlPublishingContext.*
import static com.dmgburg.dozor.configs.HtmlPublishingContext.Mode.*

import static org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class EncounterKsRepositoryTest {

    EncounterKsRepository repository
    EncounterWrapper wrapper
    static TestJettyServer server
    @Mock CredentialsRepository credentialsRepository

    @BeforeClass
    static void start(){
        CountDownLatch latch = new CountDownLatch(1)
        server = new TestJettyServer(latch)
        server.start()
        mode = Unit
        latch.await(60, TimeUnit.SECONDS)
    }
    @AfterClass
    static void destroy(){
        server.stop()
    }

    @Before
    void setUp(){
        MockitoAnnotations.initMocks(this)
        wrapper = new EncounterWrapper(credentialsRepository)
        repository = new EncounterKsRepository(wrapper)
        when(credentialsRepository.url)thenReturn("http://localhost:8080/")
    }

    @Test
    void "should return bonus text"(){
        HtmlPublishingContext.name = "enc3"
        println repository.ks
    }

    @Test
    void "should return bonus text after proper login"(){
        mode = Enc
        HtmlPublishingContext.name = "enc3"
        when(credentialsRepository.login)thenReturn("userLogin")
        when(credentialsRepository.login)thenReturn("userLogin")
        println repository.ks
    }
}
