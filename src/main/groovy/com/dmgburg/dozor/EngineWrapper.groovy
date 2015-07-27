package com.dmgburg.dozor

import groovy.util.slurpersupport.NodeChild
import groovyx.net.http.HttpResponseDecorator

interface EngineWrapper {
    public void login(String url, String username, String password)
    public NodeChild getHtml()
}
