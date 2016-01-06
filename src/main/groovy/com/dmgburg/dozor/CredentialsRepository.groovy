package com.dmgburg.dozor

@Singleton
class CredentialsRepository {
    String gameLogin
    String gamePassword
    String login
    String password
    String url
    boolean loginRequired

    {
        Properties props = new Properties()
        def propStream = this.class.getResourceAsStream("/credentials.properties")
        if (propStream){
            props.load(propStream)
        }
        gameLogin = props.'gameLogin'
        gamePassword = props.'gamePassword'
        login = props.'login'
        password = props.'password'
        url = props.'url'
    }

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

    @Override
    String toString() {
        return "CredentialsRepository {gameLogin:$gameLogin, login:$login, url:$url}"
    }
}