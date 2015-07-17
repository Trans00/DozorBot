package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatState
import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic

@CompileStatic
class CancelHandler extends AbstractHandler{

    ChatStateRepository chatStateRepository
    CancelHandler(TgApi tgApi,ChatStateRepository chatStateRepository) {
        super(tgApi)
        this.chatStateRepository = chatStateRepository
    }

    @Override
    void doHandle(Message message) {
        chatStateRepository.setState(message.chat,ChatState.noState)
        api.sendMessage(message.chat.id,"Ввод данных отменен")
    }

    @Override
    boolean doIsHandled(Message message) {
        return message.text.trim().toLowerCase().startsWith("/cancel")
    }
}
