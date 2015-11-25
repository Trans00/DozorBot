package com.dmgburg.dozor

@Singleton
class CredentialsRepository {
    String gameLogin = "moscow_Mari_larina"
    String gamePassword = "825322"
    String login = "golden_surfer"
    String password = "a123456789"
    String url = "http://classic.dzzzr.ru/moscow/go/"
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