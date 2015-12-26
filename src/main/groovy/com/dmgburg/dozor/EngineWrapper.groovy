package com.dmgburg.dozor

import groovy.util.slurpersupport.NodeChild
import groovyx.net.http.HttpResponseDecorator
import org.jsoup.nodes.Document

interface EngineWrapper {
    public Document getHtml()
}
