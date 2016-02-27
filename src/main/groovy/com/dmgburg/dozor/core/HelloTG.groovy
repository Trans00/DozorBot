package com.dmgburg.dozor.core

import com.dmgburg.dozor.ChatStateRepositoryImpl
import com.dmgburg.dozor.domain.Update
import com.dmgburg.dozor.dzzzr.DzzzrKsRepository
import com.dmgburg.dozor.handlers.*
import groovy.util.logging.Slf4j

@Slf4j
class HelloTG {
    static int lastUpdate = 0
    static long sleepTime = 1000
    static List<Handler> handlers = [new StartHandler(),
                                     new HelpHandler(),
                                     new WantHandler(),
                                     new KsHandler(new DzzzrKsRepository()),
                                     new CancelHandler(ChatStateRepositoryImpl.instance),
                                     new TeaHandler()]

    public static void main(String[] args) {
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
