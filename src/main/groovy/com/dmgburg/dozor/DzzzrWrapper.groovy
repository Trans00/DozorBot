package com.dmgburg.dozor
import groovy.util.logging.Slf4j
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import static org.jsoup.Connection.Method.GET
import static org.jsoup.Connection.Method.POST

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
    Document getHtml() {
        log.info("Requesting ${credentialsRepository.url} for ks")
        String baseUrl = credentialsRepository.url
        Connection connection = Jsoup.connect(credentialsRepository.url)
        Map <String,String> cookies = connection
                .url("http://classic.dzzzr.ru/moscow/go/")
                .header('Authorization','Basic ' + "${credentialsRepository.gameLogin}:${credentialsRepository.gamePassword}".getBytes('iso-8859-1').encodeBase64())
                .method(GET)
                .execute()
                .cookies()
        cookies = connection
                .url("http://classic.dzzzr.ru/moscow/go/")
                .header('Referer','http://classic.dzzzr.ru/moscow/go/')
                .header('Origin','http://classic.dzzzr.ru')
                .header('Authorization','Basic ' + "${credentialsRepository.gameLogin}:${credentialsRepository.gamePassword}".getBytes('iso-8859-1').encodeBase64())
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
                .data([notags:"",err:"22"])
                .get()
    }
}
