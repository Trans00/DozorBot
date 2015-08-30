package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.domain.Message

class NopHandler implements Handler{
    @Override
    void handle(Message update) {

    }

    @Override
    boolean isHandled(Message update) {
        return false
    }

    @Override
    List<Command> getCommands() {
        return []
    }
}
