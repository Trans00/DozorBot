package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic

@CompileStatic
abstract class AbstractHandler implements Handler{
    protected TgApi api

    AbstractHandler(TgApi tgApi) {
        api = tgApi
    }

    @Override
    void handle(Message message) {
        doHandle(processMessage(message))
    }

    @Override
    boolean isHandled(Message message) {
        return doIsHandled(processMessage(message))
    }

    public static Message processMessage(Message message){
        Message processedMess = message
        processedMess.text = processedMess?.text?.trim()?.toLowerCase()
        return processedMess
    }

    abstract boolean doIsHandled(Message message)

    abstract void doHandle(Message message)

}
