package com.dmgburg.dozor.dzzzr

import com.dmgburg.dozor.AuthorizationException
import com.dmgburg.dozor.CredentialsRepository
import com.dmgburg.dozor.PhantomJSWraper
import groovy.util.logging.Slf4j
import org.apache.http.HttpStatus
import org.jsoup.Connection
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

import static org.jsoup.Connection.Method.POST

@Slf4j
class DzzzrWrapper {

    CredentialsRepository credentialsRepository
    Map<String, String> cookies

    DzzzrWrapper() {
        this.credentialsRepository = CredentialsRepository.instance
    }

    DzzzrWrapper(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository
    }

    Document getHtml() {
        try {
            log.info("Requesting ${credentialsRepository.url} for ks")
            String baseUrl = credentialsRepository.url
            Connection connection = Jsoup.connect(credentialsRepository.url)
            loginIfRequired(connection, baseUrl)
            return connection
                    .url(credentialsRepository.url)
                    .header('Authorization', 'Basic ' + "${credentialsRepository.gameLogin}:${credentialsRepository.gamePassword}".getBytes('iso-8859-1').encodeBase64())
                    .cookies(cookies)
                    .data([notags: "", err: "22"])
                    .get()
        } catch (HttpStatusException e) {
            if (HttpStatus.SC_UNAUTHORIZED == e.statusCode) {
                throw new AuthorizationException("Autorization failed: ", e);
            }
            throw e;
        }
    }

    Document tryCode(String code) {
        try {
            log.info("Trying code $code")
            String baseUrl = credentialsRepository.url
            Connection connection = Jsoup.connect(credentialsRepository.url)
            loginIfRequired(connection, baseUrl)
            def result = connection.url(baseUrl)
                    .header('Referer', 'http://classic.dzzzr.ru/moscow/go/')
                    .header('Origin', 'http://classic.dzzzr.ru')
                    .header('Authorization', 'Basic ' + "${credentialsRepository.gameLogin}:${credentialsRepository.gamePassword}".getBytes('iso-8859-1').encodeBase64())
                    .postDataCharset("windows-1251")
                    .method(POST)
                    .cookies(cookies)
                    .data([log    : "on",
                           mes    : "",
                           legend : "on",
                           nostat : "",
                           notext : "",
                           refresh: "0",
                           bonus  : "",
                           kladMap: "",
                           notags : "",
                           cod    : code,
                           action : "entcod"])
                    .execute()
                    result.parse()
        } catch (HttpStatusException e) {
            if (HttpStatus.SC_UNAUTHORIZED == e.statusCode) {
                throw new AuthorizationException("Autorization failed: ", e);
            }
            throw e;
        }
    }

    private void loginIfRequired(Connection connection, String baseUrl) {
        if (!cookies) {
            log.info("Login cookies not found, trying to log in")
            WebDriver driver = PhantomJSWraper.instance.getDriver()
            driver.get("http://classic.dzzzr.ru/moscow/")
            driver.findElement(By.cssSelector("input[type=\"text\"][name=\"login\"]")).sendKeys("golden_surfer")
            driver.findElement(By.cssSelector("input[type=\"password\"][name=\"password\"]")).sendKeys("a123456789")
            driver.findElement(By.cssSelector("input[type=\"submit\"][value=\"ok\"]")).click()
            cookies = [:]
            driver.manage().cookies.each{
                cookies.put(it.name,it.value)
            }
            log.info("Coockies found: $cookies")
        }
    }
}
