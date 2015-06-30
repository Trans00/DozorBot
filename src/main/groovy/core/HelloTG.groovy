package core

import domain.Update
import handlers.Handler
import handlers.HelpHandler
import handlers.NopHandler
import handlers.StartHandler
import org.apache.log4j.Logger

import static core.LocalApi.updates

class HelloTG {
    static int lastUpdate = 0
    static long sleepTime = 1000
    static Logger log = Logger.getLogger(HelloTG)
    static def handlers = [new StartHandler(),new HelpHandler()]

    public static void main(String[] args) {
        log.info("Application started")
        List<Update> initUpdates = []
        while (initUpdates.empty) {
            initUpdates = getUpdates()
            sleep(sleepTime)
        }
        lastUpdate = initUpdates.last().update_id
        log.info("Received first update")
        while (true) {
            try {
                List<Update> updates = LocalApi.getUpdates(lastUpdate + 1)
                for (Update update : updates) {
                    lastUpdate = update.update_id
                    Handler handler = getHandler(update)
                    handler.handle(update.message)
                }
                sleep(sleepTime)
            }catch (Throwable t){
                log.error("Unhandled exception: ", t)
            }
        }
    }

    static Handler getHandler(Update update) {
        def result = handlers.find { Handler handler ->
            handler.isHandled(update.message)
        }
        if (!result) {
            result = new NopHandler()
        }
        result
    }
}
