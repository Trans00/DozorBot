package com.dmgburg.dozor.configs

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@SuppressWarnings("UnusedDeclaration")
public class HtmlPublishingContext {

    Logger logger = LoggerFactory.getLogger(HtmlPublishingContext)
    static String name

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showIndexGet() {
        logger.info("forward:/html/${name}.html")
        return "forward:/html/${name}.html"
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public String showIndexPost() {
        return "forward:/html/${name}.html"
    }

}