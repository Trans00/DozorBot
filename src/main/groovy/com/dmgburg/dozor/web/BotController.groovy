package com.dmgburg.dozor.web

import com.dmgburg.dozor.core.Application
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Controller

@Controller
@Slf4j
class BotController {
    private final Application app

    BotController() {
        app = new Application()
        println "Going to start Bot application"
        log.info("Going to start Bot application")
        app.run()
    }
}
