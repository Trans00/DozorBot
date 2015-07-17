package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatState
import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.KsRepository
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic
import org.apache.log4j.Logger

@CompileStatic
class PassedHandler extends AbstractHandler{

static Logger log = Logger.getLogger(PassedHandler)

    KsRepository ksRepository
    ChatStateRepository chatStateRepository

    PassedHandler(TgApi tgApi, KsRepository ksRepository, ChatStateRepository chatStateRepository) {
        super(tgApi)
        this.ksRepository = ksRepository
        this.chatStateRepository = chatStateRepository
    }

    @Override
    void doHandle(Message message) {
        if(message.text.trim().startsWith("/pass")) {
            chatStateRepository.setState(message.chat, ChatState.pass)
            api.sendMessage(message.chat.id, "Введите номер взятого кода")
        } else if(chatStateRepository.getState(message.chat)== ChatState.pass
            && message.text ==~ /\d+/){
            ksRepository.removeKs(Integer.valueOf(message.text))
            log.info("Код ${message.text} взят")
            api.sendMessage(message.chat.id, "Код ${message.text} взят")
            chatStateRepository.setState(message.chat, ChatState.noState)
        }
    }

    @Override
    boolean doIsHandled(Message message) {
        return message.text.startsWith("/pass") ||
                chatStateRepository.getState(message.chat) == ChatState.pass
    }
}
