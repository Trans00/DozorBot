package com.dmgburg.dozor

import com.dmgburg.dozor.configs.HtmlPublishingContext
import com.dmgburg.dozor.engine.TestJettyServer
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.MockitoAnnotations

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

import static org.mockito.Mockito.*

class DzzzrKsRepositoryTest {
    private static final String ADDRESS = "http://localhost:8080"
    DzzzrKsRepository repository
    DzzzrWrapper wrapper
    static TestJettyServer server

    @Before
    void setUp(){
        MockitoAnnotations.initMocks(this)
        wrapper = new DzzzrWrapper("$ADDRESS")
        repository = new DzzzrKsRepository(wrapper)
    }

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
    @Test
    void "should return ks from html"(){
        HtmlPublishingContext.name = "dzzzr2"
        assert repository.ks == [1:"1", 2:"2", 7:"1+", 13:"2", 18:"2", 26:"null"]
    }

    @Test
    void "should return no ks from html when no ks exist"(){
        HtmlPublishingContext.name = "dzzzr3"
        assert repository.ks == [:]
    }

    @Test
    void "should return 1 ks from html when 1 ks exist"(){
        HtmlPublishingContext.name = "dzzzr1"
        assert repository.ks == [1:"null"]
    }

    @Test
    void "should return ks from html when first ks taken"(){
        HtmlPublishingContext.name = "dzzzr4"
        assert repository.ks == [2:"1+",3:"1",4:"2",5:"1",7:"3",8:"2+",9:"2",10:"1",
                                 11:"1",12:"1+",13:"1",14:"1+",15:"1+",16:"1+",17:"1+",
                                 18:"1",19:"1+",20:"1",21:"1+",22:"1+",23:"1",24:"2",
                                 25:"1+",26:"1+",27:"1+",28:"1+",29:"1+",30:"1",31:"1+",
                                 32:"1+",33:"1",34:"1",35:"1+",36:"null"]
    }
}
