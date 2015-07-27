package com.dmgburg.dozor

import groovy.util.slurpersupport.NodeChild
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.URIBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class EncounterKsRepository implements KsRepository{
    EngineWrapper wrapper

    EncounterKsRepository(String baseUrl, String username, String password) {
        wrapper = new EncounterWrapper()
        wrapper.login(baseUrl,username,password)
    }

    EncounterKsRepository(EngineWrapper wrapper){
        this.wrapper = wrapper

    }
    @Override
    void setKs(List<String> ks) {
        throw new UnsupportedOperationException("Can't set KS on parsing repository")
    }

    @Override
    Map<Integer, String> getKs() {
        NodeChild html = getHtml()

        return null
    }

    @Override
    void removeKs(int number) {
        throw new UnsupportedOperationException("Can't remove KS on parsing repository")
    }

    NodeChild getHtml() {
        return wrapper.html
    }

    String getCookieString(){
        StringBuilder sb = new StringBuilder()
        cookies.each {String key, String value ->
            sb.append("$key=$value; ")
        }
        return sb.toString()
    }
}
