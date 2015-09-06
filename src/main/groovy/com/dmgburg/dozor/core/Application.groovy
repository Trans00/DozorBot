package com.dmgburg.dozor.core

import com.dmgburg.dozor.domain.Update
import com.dmgburg.dozor.handlers.AdminHandler
import com.dmgburg.dozor.handlers.Handler
import com.dmgburg.dozor.handlers.HelpHandler
import com.dmgburg.dozor.handlers.NopHandler
import com.dmgburg.dozor.handlers.StartHandler
import com.dmgburg.dozor.handlers.TeaHandler
import com.dmgburg.dozor.handlers.WantHandler
import com.dmgburg.dozor.handlers.plugin.DzzzrPlugin
import com.dmgburg.dozor.handlers.plugin.EncounterPlugin
import com.dmgburg.dozor.handlers.plugin.ManualKsPlugin
import com.dmgburg.dozor.handlers.plugin.Plugin
import groovy.util.logging.Slf4j

import static com.dmgburg.dozor.core.KsHandlerName.*

@Slf4j
class Application {
    static int lastUpdate = 0
    static long sleepTime = 1000
    private Map<String, Handler> handlersById = [start: new StartHandler(),
                                                 help : new HelpHandler(this),
                                                 want : new WantHandler(),
                                                 tea  : new TeaHandler(),
                                                 admin: new AdminHandler(),
                                                 ks   : new EncounterPlugin()]

    public static void main(String[] args) {
        new Application().run()
    }

    void run() {
        LocalApi api = LocalApi.instance
        log.info("Application started")
        List<Update> updates = []
        while (true) {
            try {
                for (Update update : updates) {
                    lastUpdate = update.update_id
                    getHandlers(update).each { Handler handler ->
                        handler.handle(update.message)
                    }
                }
                sleep(sleepTime)
                if(lastUpdate==0){
                    updates = api.getUpdates()
                } else {
                    updates = api.getUpdates(lastUpdate + 1)
                }
            } catch (Throwable t) {
                log.error("Unhandled exception: ", t)
                if (updates.size() > 0) {
                    updates.remove(0)
                }
            }
        }
    }

    void setKs(KsHandlerName name) {
        switch (name) {
            case MANUAL: setKsPlugin(new ManualKsPlugin())
                break
            case DZZZR: setKsPlugin(new DzzzrPlugin())
                break
            case ENC: setKsPlugin(new EncounterPlugin())
                break
            default: throw new IllegalArgumentException("Unknown plugin :$name")
        }
    }

    private Handler setKsPlugin(Plugin plugin) {
        handlersById.put("ks", plugin)
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
