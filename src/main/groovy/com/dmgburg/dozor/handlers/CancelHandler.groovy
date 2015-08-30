package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatState
import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic

import static com.dmgburg.dozor.handlers.Command.CANCEL

@CompileStatic
class CancelHandler extends AbstractHandler{

    ChatStateRepository chatStateRepository

    CancelHandler(ChatStateRepository chatStateRepository) {
        super([CANCEL])
        this.chatStateRepository = chatStateRepository
    }

    CancelHandler(TgApi tgApi,ChatStateRepository chatStateRepository) {
        super([CANCEL],tgApi)
        this.chatStateRepository = chatStateRepository
    }

    @Override
    void doHandle(Message message) {
        chatStateRepository.setState(message.chat,ChatState.noState)
        api.sendMessage(message.chat.id,"Ввод данных отменен")
    }
}
