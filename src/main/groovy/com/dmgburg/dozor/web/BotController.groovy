package com.dmgburg.dozor.web

import com.dmgburg.dozor.core.Application
import org.springframework.stereotype.Controller

@Controller
class BotController {
    private final Application app

    BotController() {
        app = new Application()
        app.run()
    }
}
