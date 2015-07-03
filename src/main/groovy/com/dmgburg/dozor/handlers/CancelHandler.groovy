package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.domain.Message

class CancelHandler implements Handler{
    @Override
    void handle(Message message) {
        ChatStateRepository.instance.setState(message.chat,"noState")
        LocalApi.sendMessage(message.chat.id,"Ввод данных отменен")
    }

    @Override
    boolean isHandled(Message message) {
        return message.text.trim().toLowerCase().startsWith("/cancel")
    }
}
