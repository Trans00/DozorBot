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
        htmlExtractor = new EngineWrapperImpl("http://classic.dzzzr.ru/moscow/go/")
    }

    @Test
    void "first test"() {
        NodeChild result =  htmlExtractor.html
        def zads =  result."**".findAll {
            it.@class.toString().contains("zad")
        }

        def level = zads.find {NodeChild it ->
            it."**".find{
                it.text().contains("бонусные коды:")||it.text().contains("основные коды:")
            }
        }

        level."**".each{ NodeChild it ->
            if(it.text().contains("основные коды:")) {
                List<String> ks = []
                Iterator<String> iter = it.localText().join("").replaceAll("\\,+", ",").split(",|:").iterator()
                while (iter.hasNext()) {
                    String next = iter.next()
                    if (next.contains("основные коды")) {
                        break
                    }
                }
                while (iter.hasNext()) {
                    String next = iter.next().trim()
                    if (next ==~ /((\d(\+)?)|null)/) {
                        ks << next
                    } else {
                        break
                    }
                }
                log.info(ks.join(","))
            }
//            if (it.text().contains("бонусные коды:")){
//                String text = it.text().replaceAll("\\s+","")
//                def matcher = text =~ /бонусныекоды:(((\d(\+)?)|null),)*((\d(\+)?)|null)/
//                log.info("Бонусные коды")
//                matcher[1].each {
//                    log.info(it)
//                }
//            }else if(it.text().contains("осноыные коды:")){
//                String text = it.text().replaceAll("\\s+","")
//                def matcher = text =~ /основныекоды:(((\d(\+)?)|null),)*((\d(\+)?)|null)/
//                log.info("Основные коды")
//                matcher[1].each {
//                    log.info(it)
//                }
//            }
        }
    }
}
