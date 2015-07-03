package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.KcRepository
import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.domain.Message
import org.apache.log4j.Logger

class PassedHandler implements Handler{
static Logger log = Logger.getLogger(PassedHandler)
    @Override
    void handle(Message message) {
        if(message.text.trim().startsWith("/pass")) {
            ChatStateRepository.instance.setState(message.chat, "pass")
            LocalApi.sendMessage(message.chat.id, "Введите номер взятого кода")
        } else if(ChatStateRepository.instance.getState(message.chat)== "pass"
            && message.text.trim() ==~ /\d+/){
            KcRepository.instance.removeKs(Integer.valueOf(message.text.trim()))
            log.info("Код ${message.text.trim()} взят")
            LocalApi.sendMessage(message.chat.id, "Код ${message.text.trim()} взят")
            ChatStateRepository.instance.setState(message.chat, "noState")
        }
    }

    @Override
    boolean isHandled(Message message) {
        return message.text.trim().toLowerCase().startsWith("/pass") ||
                ChatStateRepository.instance.getState(message.chat) == "pass"
    }
}
