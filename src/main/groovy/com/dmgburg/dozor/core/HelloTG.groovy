package com.dmgburg.dozor.core

import com.dmgburg.dozor.domain.Update
import com.dmgburg.dozor.handlers.Handler
import com.dmgburg.dozor.handlers.HelpHandler
import com.dmgburg.dozor.handlers.KcHandler
import com.dmgburg.dozor.handlers.KcNewHandler
import com.dmgburg.dozor.handlers.NopHandler
import com.dmgburg.dozor.handlers.StartHandler
import org.apache.log4j.Logger

import static com.dmgburg.dozor.core.LocalApi.updates

class HelloTG {
    static int lastUpdate = 0
    static long sleepTime = 1000
    static Logger log = Logger.getLogger(HelloTG)
    static def handlers = [new StartHandler(),new HelpHandler(),new KcHandler(),new KcNewHandler()]

    public static void main(String[] args) {
        log.info("Application started")
        List<Update> updates = []
        while (updates.empty) {
            updates = getUpdates()
            sleep(sleepTime)
        }
        log.info("Received first update")
        while (true) {
            try {
                for (Update update : updates) {
                    lastUpdate = update.update_id
                    getHandlers(update).each { Handler handler ->
                        handler.handle(update.message)
                    }
                }
                sleep(sleepTime)
                updates = LocalApi.getUpdates(lastUpdate + 1)
            }catch (Throwable t){
                log.error("Unhandled exception: ", t)
            }
        }
    }

    static List<Handler> getHandlers(Update update) {
        def result = handlers.findAll { Handler handler ->
            handler.isHandled(update.message)
        }
        if (!result) {
            result = [new NopHandler()]
        }
        result
    }
}
