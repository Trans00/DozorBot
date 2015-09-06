package com.dmgburg.dozor

@Singleton
class CredentialsRepository {
    String gameLogin
    String gamePassword
    String login
    String password
    String url = "http://moscow.en.cx/gameengines/encounter/play/52550"
    boolean loginRequired

    void setGameLogin(String gameLogin) {
        this.gameLogin = gameLogin
        loginRequired = true
    }

    void setGamePassword(String gamePassword) {
        this.gamePassword = gamePassword
        loginRequired = true
    }

    void setLogin(String login) {
        this.login = login
        loginRequired = true
    }

    void setPassword(String password) {
        this.password = password
        loginRequired = true
    }

    void setUrl(String url) {
        this.url = url
        loginRequired = true
    }

    boolean getLoginRequired() {
        def result = loginRequired
        loginRequired = false
        return result
    }
}