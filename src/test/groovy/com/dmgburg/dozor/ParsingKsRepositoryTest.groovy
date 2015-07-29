package com.dmgburg.dozor

import groovy.util.slurpersupport.NodeChild
import org.cyberneko.html.parsers.SAXParser
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import static org.mockito.Mockito.*

class ParsingKsRepositoryTest {
    ParsingKsRepository repository
    @Mock EngineWrapper wrapper

    @Before
    void setUp(){
        MockitoAnnotations.initMocks(this)
        repository = new ParsingKsRepository(wrapper)

        def html = this.getClass().getResource('/dzzzr2.html').text
        when(wrapper.getHtml()).thenReturn(html)
    }

    @Test
    void "should return ks from html"(){
        assert repository.ks == [1:"1", 2:"2", 7:"1+", 13:"2", 18:"2", 26:"null"]
    }
}
