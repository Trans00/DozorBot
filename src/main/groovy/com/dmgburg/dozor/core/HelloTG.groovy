package com.dmgburg.dozor.core

import com.dmgburg.dozor.ChatStateRepositoryImpl
import com.dmgburg.dozor.EncounterKsRepository
import com.dmgburg.dozor.KsRepositoryImpl
import com.dmgburg.dozor.DzzzrKsRepository
import com.dmgburg.dozor.domain.Update
import com.dmgburg.dozor.handlers.CancelHandler
import com.dmgburg.dozor.handlers.Handler
import com.dmgburg.dozor.handlers.HelpHandler
import com.dmgburg.dozor.handlers.KsHandler
import com.dmgburg.dozor.handlers.KsNewHandler
import com.dmgburg.dozor.handlers.LoginHandler
import com.dmgburg.dozor.handlers.NopHandler
import com.dmgburg.dozor.handlers.PassedHandler
import com.dmgburg.dozor.handlers.StartHandler
import com.dmgburg.dozor.handlers.TeaHandler
import com.dmgburg.dozor.handlers.WantHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class HelloTG {
    static int lastUpdate = 0
    static long sleepTime = 1000
    static Logger log = LoggerFactory.getLogger(HelloTG)
    static List<Handler> handlers = [new StartHandler(),
                                     new HelpHandler(),
                                     new WantHandler(),
                                     new KsHandler(KsRepositoryImpl.instance),
                                     new KsNewHandler(KsRepositoryImpl.instance,
                                             ChatStateRepositoryImpl.instance),
                                     new PassedHandler(KsRepositoryImpl.instance,
                                             ChatStateRepositoryImpl.instance),
                                     new CancelHandler(ChatStateRepositoryImpl.instance),
                                     new LoginHandler(),
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
