package com.dmgburg.dozor

import com.dmgburg.dozor.handlers.KsNewHandler
import groovy.util.slurpersupport.NodeChild
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.Method
import groovyx.net.http.RESTClient
import groovyx.net.http.URIBuilder
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class EngineWrapperTest {
    EngineWrapper htmlExtractor

    Logger log = LoggerFactory.getLogger(EngineWrapperTest)
    @Before
    void onInit() {
        htmlExtractor = new EngineWrapperImpl("http://google.com/")
    }

    @Test
    void "first test"() {
        def http = new HTTPBuilder("http://google.com/")
        http.request(Method.GET){

        }
    }
}
