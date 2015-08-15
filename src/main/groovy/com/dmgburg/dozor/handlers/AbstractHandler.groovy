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
abstract class AbstractHandler implements Handler{
    protected TgApi api
    protected dropEmptyText = true

    AbstractHandler(){
        api = LocalApi.instance
    }

    AbstractHandler(TgApi tgApi) {
        api = tgApi
    }

    @Override
    void handle(Message message) {
        try {
            doHandle(processMessage(message))
        } catch (Exception e){
            log.error("doHandle of ${this.class} threw an exeption on message ${message.toString()}: ",e)
        }
    }

    @Override
    boolean isHandled(Message message) {
        try {
            if(!message.text && dropEmptyText){
                return false
            }
            return doIsHandled(processMessage(message))
        } catch (Exception e){
            log.error("doIshandled of ${this.class} threw an exeption on message ${message.toString()}: ",e)
        }
    }

    public static Message processMessage(Message message){
        Message processedMess = message
        processedMess.text = processedMess?.text?.trim()?.toLowerCase()
        return processedMess
    }

    abstract boolean doIsHandled(Message message)

    abstract void doHandle(Message message)

}
