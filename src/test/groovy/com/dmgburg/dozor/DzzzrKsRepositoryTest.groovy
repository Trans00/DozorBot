package com.dmgburg.dozor

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import static org.mockito.Mockito.*

class DzzzrKsRepositoryTest {
    DzzzrKsRepository repository
    @Mock EngineWrapper wrapper

    @Before
    void setUp(){
        MockitoAnnotations.initMocks(this)
        repository = new DzzzrKsRepository(wrapper)
    }

    @Test
    void "should return ks from html"(){
        def html = this.getClass().getResource('/dzzzr2.html').text
        when(wrapper.getHtml()).thenReturn(html)
        assert repository.ks == [1:"1", 2:"2", 7:"1+", 13:"2", 18:"2", 26:"null"]
    }

    @Test
    void "should return no ks from html when no ks exist"(){
        def html = this.getClass().getResource('/dzzzr3.html').text
        when(wrapper.getHtml()).thenReturn(html)
        assert repository.ks == [:]
    }

    @Test
    void "should return 1 ks from html when 1 ks exist"(){
        def html = this.getClass().getResource('/dzzzr1.html').text
        when(wrapper.getHtml()).thenReturn(html)
        assert repository.ks == [1:"null"]
    }

    @Test
    void "should return ks from html when first ks taken"(){
        def html = this.getClass().getResource('/dzzzr4.html').text
        when(wrapper.getHtml()).thenReturn(html)
        assert repository.ks == [2:"1+",3:"1",4:"2",5:"1",7:"3",8:"2+",9:"2",10:"1",
                                 11:"1",12:"1+",13:"1",14:"1+",15:"1+",16:"1+",17:"1+",
                                 18:"1",19:"1+",20:"1",21:"1+",22:"1+",23:"1",24:"2",
                                 25:"1+",26:"1+",27:"1+",28:"1+",29:"1+",30:"1",31:"1+",
                                 32:"1+",33:"1",34:"1",35:"1+",36:"null"]
    }
}
