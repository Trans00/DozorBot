package com.dmgburg.dozor.configs

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Slf4j
@Controller
@SuppressWarnings("UnusedDeclaration")
public class HtmlPublishingContext {

    static String name

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public String showIndexGet() {
        log.info("forward:/html/${name}.html")
        return "forward:/html/${name}.html"
    }


    @RequestMapping(value = "/**", method = RequestMethod.POST)
    @ResponseBody
    public String showIndexPost() {
        return "forward:/html/${name}.html"
    }

}