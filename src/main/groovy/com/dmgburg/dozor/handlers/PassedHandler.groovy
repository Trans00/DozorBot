package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatState
import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.KsRepository
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Slf4j
@CompileStatic
class PassedHandler extends AbstractHandler{


    KsRepository ksRepository
    ChatStateRepository chatStateRepository

    PassedHandler(KsRepository ksRepository, ChatStateRepository chatStateRepository) {
        super()
        this.ksRepository = ksRepository
        this.chatStateRepository = chatStateRepository
    }

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
