package com.dmgburg.dozor.handlers.plugin

import com.dmgburg.dozor.domain.Message
import com.dmgburg.dozor.handlers.Command
import com.dmgburg.dozor.handlers.Handler

class Plugin implements Handler{

    List<Handler> handlers

    Plugin(List<Handler> handlers) {
        this.handlers = handlers
    }

    @Override
    void handle(Message message) {
        handlers.each {
            if(it.isHandled(message)){
                it.handle(message)
            }
        }
    }

    @Override
    boolean isHandled(Message message) {
        handlers.each {
            if(it.isHandled(message)){
                return true
            }
            return false
        }
    }

    @Override
    List<Command> getCommands() {
        def commands = []
        handlers.each{commands.addAll(it.commands)}
        commands
    }
}
