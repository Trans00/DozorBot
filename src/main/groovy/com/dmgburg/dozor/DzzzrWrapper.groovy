package com.dmgburg.dozor

import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.URIBuilder

@Slf4j
class DzzzrWrapper implements EngineWrapper{

    CredentialsRepository credentialsRepository

    DzzzrWrapper() {
        this.credentialsRepository = CredentialsRepository.instance
    }

    DzzzrWrapper(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository
    }

    @Override
    String getHtml() {
        log.info("Requesting ${credentialsRepository.url} for ks")
        String baseUrl = credentialsRepository.url
        def http = new HTTPBuilder(baseUrl)
        http.contentType = ContentType.HTML
        http.headers['Authorization'] = 'Basic ' + "${credentialsRepository.gameLogin}:${credentialsRepository.gamePassword}".getBytes('iso-8859-1').encodeBase64()
        def cookies = []
        http.request(Method.GET,ContentType.URLENC){ req ->
            URIBuilder uriBuilder = new URIBuilder(baseUrl)
            uriBuilder.path = "/"

            headers.'Accept' = ContentType.HTML
            headers.'User-Agent' = "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36"
            headers.'Cookie' = cookies.join(";")

            response.success = { resp, reader ->
                cookies = []
                resp.getHeaders('Set-Cookie').each {
                    def cookie = it.value.split(";")
                    log.debug("Adding cookie ${cookie}")
                    cookies.addAll(cookie)
                }
                return reader
            }
        }

        http.request(Method.POST,ContentType.URLENC){
            req ->
                URIBuilder uriBuilder = new URIBuilder("/moscow/go/")
                uriBuilder.path = "/"
                uriBuilder.query = [notags: "", "err": "25"]

                headers.'Accept' = 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8'
                headers.'User-Agent' = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36"
                headers.'Cookie' = cookies.join(";")
                headers.'Connection' = 'keep-alive'
                headers.'Origin' = 'http://classic.dzzzr.ru'
                headers.'Referer' =  'http://classic.dzzzr.ru/moscow/go/'

                body = [notags  : "",
                        action  : "auth",
                        login   : credentialsRepository.login,
                        password: credentialsRepository.password]

                response.success = { resp, reader ->
                    cookies = []
                    resp.getHeaders('Set-Cookie').each {
                        def cookie = it.value.split(";")
                        log.debug("Adding cookie ${cookie}")
                        cookies.addAll(cookie)
                    }
                    return reader
                }
        }

        def result = http.request(Method.GET,ContentType.BINARY){ req ->
            URIBuilder uriBuilder = new URIBuilder(baseUrl)
            uriBuilder.path = "/moscow/go/"
            uriBuilder.query = [notags:"",err:"22"]

            headers.'User-Agent' = "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36"
            headers.'Cookie' = cookies.join(";")

            response.success = { resp, reader ->
                return reader.text
            }

        }
        result
    }
}
