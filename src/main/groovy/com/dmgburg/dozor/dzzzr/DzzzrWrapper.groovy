package com.dmgburg.dozor.dzzzr

import com.dmgburg.dozor.AuthorizationException
import com.dmgburg.dozor.CredentialsRepository
import com.dmgburg.dozor.PhantomJSWraper
import groovy.util.logging.Slf4j
import org.apache.http.HttpStatus
import org.jsoup.HttpStatusException
import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxProfile

@Slf4j
class DzzzrWrapper {

    CredentialsRepository credentialsRepository
    WebDriver driver
    DzzzrWrapper() {
        this.credentialsRepository = CredentialsRepository.instance
    }

    DzzzrWrapper(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository
    }

    String getHtml() {
        try {
            log.info("Requesting ${credentialsRepository.url} for ks")
            String baseUrl = credentialsRepository.url
            WebDriver driver = loginIfRequired(baseUrl)
            driver.get(baseUrl.replace("http://", "http://${credentialsRepository.gameLogin}:${credentialsRepository.gamePassword}@"))
            return driver.pageSource
        } catch (HttpStatusException e) {
            if (HttpStatus.SC_UNAUTHORIZED == e.statusCode) {
                throw new AuthorizationException("Autorization failed: ", e);
            }
            throw e;
        }
    }

            String tryCode(String code) {
                try {
                    log.info("Trying code $code")
            String baseUrl = credentialsRepository.url
            WebDriver driver = loginIfRequired(baseUrl)
            log.info("Login cookies not found, trying to log in")

            driver.get(baseUrl.replace("http://","http://${credentialsRepository.gameLogin}:${credentialsRepository.gamePassword}@"))
            driver.findElement(By.cssSelector("form[name=\"codeform\"]>div[class=\"codeform\"]>input[name=\"cod\"]")).sendKeys(code)
            driver.findElement(By.cssSelector("form[name=\"codeform\"]>div[class=\"codeform\"]>input[name=\"cod\"]")).sendKeys(Keys.ENTER)

            log.info("Page after submit: ${driver.pageSource}")
            driver.findElement(By.cssSelector("div[class=\"sysmsg\"]")).text
        } catch (HttpStatusException e) {
            if (HttpStatus.SC_UNAUTHORIZED == e.statusCode) {
                throw new AuthorizationException("Autorization failed: ", e);
            }
            credentialsRepository.loginRequired = true
            throw e;
        }
    }

    private WebDriver loginIfRequired(String baseUrl) {
        if(driver == null || credentialsRepository.loginRequired) {
            log.info("Login cookies not found, trying to log in")
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("network.http.phishy-userpass-length", 255);
//            driver = new FirefoxDriver(profile);
            driver = PhantomJSWraper.instance.driver
            driver.get(baseUrl.replace("http://","http://${credentialsRepository.gameLogin}:${credentialsRepository.gamePassword}@"))
            driver.findElement(By.cssSelector("input[type=\"text\"][name=\"login\"]")).sendKeys(credentialsRepository.login)
            driver.findElement(By.cssSelector("input[type=\"password\"][name=\"password\"]")).sendKeys(credentialsRepository.password)
            driver.findElement(By.cssSelector("input[type=\"submit\"][value=\" ok \"]")).submit()
        }
        return driver
    }
}
