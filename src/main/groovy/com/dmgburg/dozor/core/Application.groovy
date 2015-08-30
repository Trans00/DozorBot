package com.dmgburg.dozor.core

import com.dmgburg.dozor.domain.Update
import com.dmgburg.dozor.handlers.Handler
import com.dmgburg.dozor.handlers.HelpHandler
import com.dmgburg.dozor.handlers.NopHandler
import com.dmgburg.dozor.handlers.StartHandler
import com.dmgburg.dozor.handlers.TeaHandler
import com.dmgburg.dozor.handlers.WantHandler
import com.dmgburg.dozor.handlers.plugin.ManualKsPlugin
import groovy.util.logging.Slf4j

@Slf4j
class Application {
    static int lastUpdate = 0
    static long sleepTime = 1000
    private Map<String, Handler> handlersById = [start: new StartHandler(),
                                                 help : new HelpHandler(),
                                                 want : new WantHandler(),
                                                 tea  : new TeaHandler(),
                                                 ks   : new ManualKsPlugin()]

    public static void main(String[] args) {
        new Application().run()
    }

    void run() {
        LocalApi api = LocalApi.instance
        log.info("Application started")
        List<Update> updates = []
        while (updates.empty) {
            updates = api.getUpdates()
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
                updates = api.getUpdates(lastUpdate + 1)
            }catch (Throwable t){
                log.error("Unhandled exception: ", t)
                if(updates.size()>0) {
                    updates.remove(0)
                }
            }
        }
    }

    List<Handler> getHandlers(Update update) {
        def result = handlersById.values().findAll { Handler handler ->
            handler.isHandled(update.message)
        }
        if (!result) {
            result = [new NopHandler()]
        }
        result
    }
}
