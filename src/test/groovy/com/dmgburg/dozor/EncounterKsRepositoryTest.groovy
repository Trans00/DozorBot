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

import static org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class EncounterKsRepositoryTest {

    EncounterKsRepository repository
    String userName ="enc_test_user"
    String password = "a123456789"
    String url = "http://moscow.en.cx/gameengines/encounter/play/52259/"
    static TestJettyServer server

    @BeforeClass
    static void start(){
        CountDownLatch latch = new CountDownLatch(1)
        server = new TestJettyServer(latch)
        server.start()
        latch.await(60, TimeUnit.SECONDS)
    }
    @AfterClass
    static void destroy(){
        server.stop()
    }

    @Before
    void setUp(){
        MockitoAnnotations.initMocks(this)
//        repository = new EncounterKsRepository(url, userName, password)
        repository = new EncounterKsRepository("http://localhost:8080/", userName, password)
    }

    @Test
    void "should login as test user"(){
        def html = repository.html
        Document parsed = Jsoup.parse(html)
        parsed?.select("td")?.attr("class","leftPnl leftMenu")?.attr("id","tdContentLeft")?.each {
            for(Element child :it.childNodes()){
                println child.text()
            }
        }
//        assert b.BODY.TABLE.find {it.@id == "tableContent"}.TBODY.TR.find{it.@id =="contentRow"}.TD.TABLE.TBODY.TR.TD.find{it.@id == "tdContentLeft"}
//                .DIV.find{it.@id=="boxUser"}.DIV.DIV.TABLE.TBODY.TR.TD.text().contains(userName)
    }

    @Test
    void "should return bonus text"(){
        HtmlPublishingContext.name = "enc3"
        println repository.ks
    }

}
