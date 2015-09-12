package com.dmgburg.dozor.configs

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Slf4j
@Controller
@SuppressWarnings("UnusedDeclaration")
public class HtmlPublishingContext {

    static String name
    static Mode mode
    static String AUTH_TOKEN = 'validToken'
    static String SESSION_TOKEN = 'validSession'
    static String gameLogin = 'gameLogin'
    static String gamePass = 'gamePass'
    static String userLogin = 'userLogin'
    static String userPass = 'userPass'

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public String showIndexGet(@CookieValue(value = 'atoken', defaultValue = "") String aToken,
                               @CookieValue(value = 'stoken', defaultValue = "") String sToken,
                               @RequestHeader(value = "Authorization", required = false) String basicAuth) {
        try {
            log.info("atoken=$aToken; stoken=$sToken; basicAuth=${basicAuth}")
            if (mode == Mode.Dzzzr) {
                assert aToken == AUTH_TOKEN && authValid(basicAuth)
                log.info("Dzzzr: forward:/html/${name}.html")
                return "forward:/html/${name}.html"
            } else if (mode == Mode.Enc) {
                assert aToken == AUTH_TOKEN && sToken == SESSION_TOKEN
                log.info("Encounter: forward:/html/${name}.html")
                return "forward:/html/${name}.html"
            } else {
                return "forward:/html/${name}.html"
            }
        } catch (AssertionError e) {
            log.error("Get request: $mode: \n${e.message}")
            return "forward:/html/dzzzr_login_failed.html"
        }
    }

    private static boolean authValid(String basicAuth) {
        basicAuth == 'Basic ' + "$gameLogin:$gamePass".getBytes('iso-8859-1').encodeBase64()
    }

    @RequestMapping(value = "/**", method = RequestMethod.POST)
    @ResponseBody
    public String showIndexPost(HttpServletResponse response,
                                @RequestHeader(value = "Authorization", required = false) String basicAuth,
                                @RequestParam(value="login", required=false) String login,
                                @RequestParam(value="password", required=false) String password,
                                @RequestParam(value="action", required=false) String action) {
        try {
            log.info("Post request: basicAuth = $basicAuth; action=$action; login=$login;  password=$password")
            if (mode == Mode.Dzzzr) {
                assert authValid(basicAuth)
                if(action != "auth" || login != userLogin || password != userPass){
                    log.info("login failed")
                    return "forward:/html/dzzzr_login_failed.html"
                }
                response.addCookie(new Cookie('atoken', AUTH_TOKEN))
                return "forward:/html/${name}.html"
            }
        } catch (AssertionError e){
            log.error("Post request: $mode: \n${e.message}")
            return response.sendError(401, "Unauthorized")
        }
    }

    enum Mode {
        Dzzzr,
        Enc,
        Unit
    }
}