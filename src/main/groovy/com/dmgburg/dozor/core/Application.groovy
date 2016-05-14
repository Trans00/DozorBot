package com.dmgburg.dozor.core

import com.dmgburg.dozor.CredentialsRepository
import com.dmgburg.dozor.domain.Update
import com.dmgburg.dozor.handlers.*
import com.dmgburg.dozor.handlers.plugin.DzzzrPlugin
import com.dmgburg.dozor.handlers.plugin.EncounterPlugin
import com.dmgburg.dozor.handlers.plugin.ManualKsPlugin
import com.dmgburg.dozor.handlers.plugin.Plugin
import groovy.util.logging.Slf4j

import static com.dmgburg.dozor.core.KsHandlerName.*

@Slf4j
class Application implements Runnable {
    static int lastUpdate = 0
    static long sleepTime = 1000
    private Map<String, Handler> handlersById = [start: new StartHandler(),
                                                 want : new WantHandler(),
                                                 tea  : new TeaHandler(),
                                                 admin: new AdminHandler(),
                                                 ks   : new DzzzrPlugin()]

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
                    log.info("Processing update: ${update.update_id} ${update.message}")

                    getHandlers(update).each { Handler handler ->
                        handler.handle(update.message)
                    }
                }
                sleep(sleepTime)
                if (CredentialsRepository.instance.applicationEnabled) {
                    if (lastUpdate == 0) {
                        updates = api.getUpdates()
                    } else {
                        updates = api.getUpdates(lastUpdate + 1)
                    }
                } else {
                    log.info("Skipping update: app disabled")
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

    String getKs() {
        Class aClass = handlersById.get("ks").class
        if (aClass == DzzzrPlugin) {
            return DZZZR
        } else if (aClass == EncounterPlugin) {
            return ENC
        } else {
            return ""
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
