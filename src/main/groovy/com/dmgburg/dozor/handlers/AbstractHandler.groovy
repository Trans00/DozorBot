package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Slf4j
@CompileStatic
abstract class AbstractHandler implements Handler {
    protected TgApi api
    protected dropEmptyText = true
    protected List<Command> commands
    AbstractHandler(List<Command> commands = [], TgApi tgApi = LocalApi.instance) {
        this.commands = commands
        api = tgApi
    }

    @Override
    void handle(Message message) {
        try {
            doHandle(message)
        } catch (Exception e) {
            log.error("doHandle of ${this.class} threw an exeption on message ${message.toString()}: ", e)
        }
    }

    @Override
    boolean isHandled(Message message) {
        try {
            if (!message.text && dropEmptyText) {
                return false
            }
            return doIsHandled(message.normalize())
        } catch (Exception e) {
            log.error("doIshandled of ${this.class} threw an exeption on message ${message.toString()}: ", e)
        }
    }

    @Override
    List<Command> getCommands() {
        return commands
    }

    boolean doIsHandled(Message message){
        return getCommands().find{it.command == message.getCommand()}
    }

    abstract void doHandle(Message message)

}
