package com.dmgburg.dozor

import groovy.util.slurpersupport.NodeChild
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.URIBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class EncounterKsRepository implements KsRepository{
    private static Logger log = LoggerFactory.getLogger(EncounterKsRepository)
    String login
    String password
    String authToken
    String sessionToken
    String GUID
    def cookies = [:]
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
        def handler = { resp, reader ->
            log.info("Reading cookies")
            resp.getHeaders('Set-Cookie').each {
                def cookie = it.value.split(";")
                log.debug("Adding cookie ${cookie}")
                if(cookie[0].contains("atoken=")){
                    authToken = cookie[0]
                }
                if(cookie[0].contains("stoken=")){
                    sessionToken = cookie[0]
                }
                if(cookie[0].contains("GUID=")){
                    GUID = cookie[0]
                }
                cookies.put(cookie[0].split("=")[0],cookie[0].split("=")[1])
            }
            return reader
        }
        String baseUrl = "http://world.en.cx"
        def http = new HTTPBuilder(baseUrl)
        http.contentType = ContentType.HTML

        def result

        log.debug("Post1")
        result = new HTTPBuilder(baseUrl).request(Method.POST,ContentType.URLENC){
            req ->
                URIBuilder uriBuilder = new URIBuilder(baseUrl)
                uriBuilder.path = "/Login.aspx"
                uriBuilder.query = [return:"/"]
                uri = uriBuilder.toURI()

                headers.'Accept' = 'text/html'
                headers.'User-Agent' = "MyAgent"
                headers.'Cookie' = getCookieString()

                body = [socialAssign  : "0",
                        ddlNetwork  : "1",
                        EnButton1  : "Вход",
                        Login   : "enc_test_user",
                        Password: "a123456789"]

                response.success = handler
        }

        log.debug("Get1")
        result = new HTTPBuilder(baseUrl).request(Method.GET,ContentType.HTML){ req ->
            URIBuilder uriBuilder = new URIBuilder(baseUrl)
            uriBuilder.path = "/"
            uri = uriBuilder.toURI()

            headers.'Accept' = "text/html"
            headers.'Accept-Encoding' = ""
            headers.'Accept-Language' = ""
            headers.'Cache-Control' = "max-age=0"
            headers.'User-Agent' = "MyAgent"
            headers.'Cookies' = authToken
            headers.'Upgrade-Insecure-Requests' = "1"

            response.success = handler
        }
        result
    }

    String getCookieString(){
        StringBuilder sb = new StringBuilder()
        cookies.each {String key, String value ->
            sb.append("$key=$value; ")
        }
        return sb.toString()
    }
}
