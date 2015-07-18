package com.dmgburg.dozor

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import static org.mockito.Mockito.*

class PrasingKsRepositoryTest {
    ParsingKsRepository repository
    @Mock EngineWrapper wrapper

    @Before
    void setUp(){
        MockitoAnnotations.initMocks(this)
        repository = new ParsingKsRepository(wrapper)

        def html = this.getClass().getResource('/1.htm').text
        def slurper  = new XmlSlurper()
        def parsed = slurper.parseText(html)
        parsed.childNodes()
//        when(wrapper.html).thenReturn(())
//        wrapper.html
    }

    @Test
    void "should return ks from html"(){
        repository.ks == [1:"null"]
    }
}
