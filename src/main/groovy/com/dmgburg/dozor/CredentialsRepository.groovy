package com.dmgburg.dozor

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

@CompileStatic
@Slf4j
class CredentialsRepository {
    private static CredentialsRepository instance;
    Properties props = new Properties()

    String filename
    CredentialsRepository() {
        this(System.getenv().get("OPENSHIFT_DATA_DIR") + File.separator + "credentials.properties");
    }

    public static synchronized CredentialsRepository getInstance() {
        if (instance == null) {
            instance = new CredentialsRepository();
        }
        return instance;
    }

    CredentialsRepository(String filename) {
        this.filename = filename;
        if (new File(filename).isFile()) {
            def propStream = new FileInputStream(new File(filename))
            props.load(propStream)
        }
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                saveCredentials();
            }
        }, 30, 30, TimeUnit.SECONDS);
    }

    Boolean getApplicationEnabled() {
        return props.applicationEnabled != null ? Boolean.valueOf(props.applicationEnabled.toString()) : true
    }

    void setApplicationEnabled(Boolean applicationEnabled) {
        props.applicationEnabled = applicationEnabled
    }


    public String getGameLogin() {
        return props.gameLogin
    }

    public String getGamePassword() {
        return props.gamePassword
    }

    public String getLogin() {
        return props.login
    }

    public String getPassword() {
        return props.password
    }

    public String getUrl() {
        return props.url
    }

    void setTryEnabled(boolean tryEnbled) {
        props.'tryEnabled' = tryEnbled
    }

    String getTryEnabled() {
        props.'tryEnabled'?:false
    }

    void setGameLogin(String gameLogin) {
        props.gameLogin = gameLogin
        props.loginRequired = true
    }

    void setGamePassword(String gamePassword) {
        props.gamePassword = gamePassword
        props.loginRequired = true
    }

    void setLogin(String login) {
        props.login = login
        props.loginRequired = true
    }

    void setPassword(String password) {
        props.password = password
        props.loginRequired = true
    }

    void setUrl(String url) {
        props.url = url
        props.loginRequired = true
    }

    void setLoginRequired(boolean loginRequired) {
        props.loginRequired = loginRequired
    }

    boolean getLoginRequired() {
        def result = loginRequired
        props.loginRequired = false
        return result
    }

    private void saveCredentials() {
        try {
            log.info("Persisting props: " + filename);
            props.store(new FileOutputStream(new File(filename)), "")
        } catch (IOException e) {
            log.error("Save game failed: ", e);
        }
    }

    @Override
    public String toString() {
        return "CredentialsRepository $props"
    }
}