package com.dmgburg.dozor

import groovy.util.logging.Slf4j
import org.apache.http.HttpStatus
import org.jsoup.Connection
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import static org.jsoup.Connection.Method.POST

@Slf4j
class DzzzrWrapper implements EngineWrapper{

    CredentialsRepository credentialsRepository
    Map<String,String> cookies

    DzzzrWrapper() {
        this.credentialsRepository = CredentialsRepository.instance
    }

    DzzzrWrapper(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository
    }

    @Override
    Document getHtml() {
        try {
            log.info("Requesting ${credentialsRepository.url} for ks")
            String baseUrl = credentialsRepository.url
            Connection connection = Jsoup.connect(credentialsRepository.url)
            cookies = connection
                    .url(baseUrl)
                    .header('Referer', 'http://classic.dzzzr.ru/moscow/go/')
                    .header('Origin', 'http://classic.dzzzr.ru')
                    .header('Authorization', 'Basic ' + "${credentialsRepository.gameLogin}:${credentialsRepository.gamePassword}".getBytes('iso-8859-1').encodeBase64())
                    .method(POST)
                    .data([notags  : "",
                           action  : "auth",
                           login   : credentialsRepository.login,
                           password: credentialsRepository.password])
                    .execute()
                    .cookies()
            return connection
                    .url(credentialsRepository.url)
                    .cookies(cookies)
                    .data([notags: "", err: "22"])
                    .get()
        } catch (HttpStatusException e){
            if(HttpStatus.SC_UNAUTHORIZED == e.statusCode){
                throw new AuthorizationException("Autorization failed: ", e);
            }
            throw e;
        }
    }
}
