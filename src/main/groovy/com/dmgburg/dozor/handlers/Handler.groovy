package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.domain.Message

interface Handler {
    void handle(Message message)
    boolean isHandled(Message message)
}