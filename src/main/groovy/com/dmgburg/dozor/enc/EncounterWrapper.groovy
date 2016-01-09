package com.dmgburg.dozor.enc

import com.dmgburg.dozor.AuthorizationException
import com.dmgburg.dozor.CredentialsRepository
import com.dmgburg.dozor.EngineWrapper
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import static org.jsoup.Connection.Method.POST

class EncounterWrapper implements EngineWrapper {
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
        String localePath = ""
        Connection connection = Jsoup.connect(credentialsRepository.url)
        def matcher = baseUrl =~/(http\:\/\/.*)\/(.*)/
        if(matcher.matches()){
            localePath = matcher.group(1)
        }
        Map<String, String> cookies = connection
                .url("$localePath/Login.aspx")
                .header('Accept', 'text/html')
                .header('User-Agent', "MyAgent")
                .method(POST)
                .data([socialAssign: "0",
                       ddlNetwork  : "1",
                       EnButton1   : "Вход",
                       Login       : credentialsRepository.login,
                       Password    : credentialsRepository.password])
                .execute()
                .cookies()
        authToken = cookies.get("atoken")
        sessionToken = cookies.get("stoken")
        if(!authToken || !sessionToken){
            throw new AuthorizationException("Failed to login")
        }
    }

    @Override
    Document getHtml() {
        if (!authToken && !sessionToken || credentialsRepository.loginRequired) {
            login()
        }
        def baseUrl = credentialsRepository.url
        Connection connection = Jsoup.connect(credentialsRepository.url)
        connection.url(baseUrl)
            .header('Accept', 'text/html')
            .header('User-Agent', "MyAgent")
            .header('Accept-Encoding', "")
            .header('Accept-Language', "")
            .header('Cache-Control', "max-age=0")
            .cookies([atoken: authToken,
                      stoken: sessionToken])
            .get()
    }
}
