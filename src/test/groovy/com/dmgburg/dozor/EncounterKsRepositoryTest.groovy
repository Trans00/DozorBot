package com.dmgburg.dozor

import groovy.util.slurpersupport.NodeChild
import groovy.xml.StreamingMarkupBuilder
import org.cyberneko.html.parsers.SAXParser
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import static org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class EncounterKsRepositoryTest {

    EncounterKsRepository repository
    @Mock EngineWrapper wrapper
    String userName ="enc_test_user"
    String password = "a123456789"
    String url = "http://world.en.cx"

    @Before
    void setUp(){
        MockitoAnnotations.initMocks(this)
        repository = new EncounterKsRepository(url, userName, password)
    }

    @Test
    void "should login as test user"(){
        def b = repository.html
        assert b.BODY.TABLE.find {it.@id == "tableContent"}.TBODY.TR.find{it.@id =="contentRow"}.TD.TABLE.TBODY.TR.TD.find{it.@id == "tdContentLeft"}
                .DIV.find{it.@id=="boxUser"}.DIV.DIV.TABLE.TBODY.TR.TD.text().contains(userName)
    }

    @Test
    void "should return ks"(){
        def html = this.getClass().getResource('/dzzzr2.html').text
        def slurper  = new XmlSlurper(new SAXParser())
        NodeChild parsed = slurper.parseText(html)
        parsed.childNodes()
        when(wrapper.getHtml()).thenReturn(parsed)
        repository = new EncounterKsRepository(wrapper)
    }

}
