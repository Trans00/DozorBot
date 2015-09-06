package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatState
import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.ChatStateRepositoryImpl
import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors

import static com.dmgburg.dozor.handlers.Command.CANCEL

@CompileStatic
class CancelHandler extends AbstractHandler {

    ChatStateRepository chatStateRepository

    CancelHandler(TgApi tgApi = LocalApi.instance,
                  ChatStateRepository chatStateRepository = ChatStateRepositoryImpl.instance) {
        super([CANCEL], tgApi)
        this.chatStateRepository = chatStateRepository
    }

    @Override
    void doHandle(Message message) {
        chatStateRepository.setState(message.chat, ChatState.NO_STATE)
        tgApi.sendMessage(message.chat.id, "Ввод данных отменен")
    }
}
