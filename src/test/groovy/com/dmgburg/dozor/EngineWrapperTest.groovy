package com.dmgburg.dozor

import groovy.util.slurpersupport.NodeChild
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class EngineWrapperTest {
    EngineWrapper htmlExtractor

    @Before
    void onInit(){
        htmlExtractor = new EngineWrapperImpl("http://habrahabr.ru/company/golovachcourses/blog/251239/")
    }

    @Test
    void "first test"(){
        NodeChild result =  htmlExtractor.html
        println result."**".findAll {
            it.@class.toString().contains("clear")
        }
    }
}
