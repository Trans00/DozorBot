package com.dmgburg.dozor

import groovy.util.slurpersupport.NodeChild
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.Method
import groovyx.net.http.URIBuilder

class EncounterWrapper implements EngineWrapper{
    CredentialsRepository credentialsRepository
    String authToken
    String sessionToken

    EncounterWrapper() {
        this.credentialsRepository = CredentialsRepository.instance
    }

    EncounterWrapper(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository
    }

    void login() {
        def baseUrl = credentialsRepository.url
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
                        Login   : credentialsRepository.userLogin,
                        Password: credentialsRepository.userPassword]

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
        if(!authToken && !sessionToken){
            login()
        }
        def baseUrl = credentialsRepository.url
        def localPath
        def matcher = baseUrl =~/http\:\/\/.*en.cx\/(.*)/
        if(matcher.matches()){
            localPath = matcher.group(1)
        }
        String result = new HTTPBuilder(baseUrl).request(Method.GET,ContentType.TEXT){ req ->
            URIBuilder uriBuilder = new URIBuilder(baseUrl)
            uriBuilder.path = "$localPath"
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
