package com.dmgburg.dozor

import groovy.util.slurpersupport.NodeChild
import groovyx.net.http.HTTPBuilder

class EngineWrapperImpl implements EngineWrapper{

    String uri

    EngineWrapperImpl(String uri) {
        this.uri = uri
    }

    @Override
    NodeChild getHtml() {
        HTTPBuilder http = new HTTPBuilder(uri)
        return http.get([:])
    }
}
