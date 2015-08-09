package com.dmgburg.dozor

import groovy.util.slurpersupport.NodeChild
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.Method
import groovyx.net.http.URIBuilder

class EncounterWrapper implements EngineWrapper{
    String authToken
    String sessionToken
    String baseUrl
    String localPath

    @Override
    void login(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl
        new HTTPBuilder(baseUrl).request(Method.POST,ContentType.URLENC){
            req ->
                URIBuilder uriBuilder = new URIBuilder(baseUrl)
                uriBuilder.path = "/Login.aspx"
                uriBuilder.query = [return:"/"]
                uri = uriBuilder.toURI()

                headers.'Accept' = 'text/html'
                headers.'User-Agent' = "MyAgent"

                body = [socialAssign  : "0",
                        ddlNetwork  : "1",
                        EnButton1  : "Вход",
                        Login   : username,
                        Password: password]

                response.success = { resp, reader ->
                    resp.getHeaders('Set-Cookie').each {
                        def cookie = it.value.split(";")
                        if(cookie[0].contains("atoken=")){
                            authToken = cookie[0]
                        }
                        if(cookie[0].contains("stoken=")){
                            sessionToken = cookie[0]
                        }
                    }
                    return reader
                }
        }
    }

    @Override
    String getHtml() {
        if(!authToken){
            throw new IllegalStateException("Not logged in")
        }
        String result = new HTTPBuilder(baseUrl).request(Method.GET,ContentType.TEXT){ req ->
            URIBuilder uriBuilder = new URIBuilder(baseUrl)
            uriBuilder.path = "/$localPath"
            uri = uriBuilder.toURI()

            headers.'Accept' = "text/html"
            headers.'Accept-Encoding' = ""
            headers.'Accept-Language' = ""
            headers.'Cache-Control' = "max-age=0"
            headers.'User-Agent' = "MyAgent"
            headers.'Cookie' = "$authToken; $sessionToken"
            headers.'Upgrade-Insecure-Requests' = "1"

            response.success = { HttpResponseDecorator resp, reader ->
                return reader.text
            }

        }
        result
    }
}
