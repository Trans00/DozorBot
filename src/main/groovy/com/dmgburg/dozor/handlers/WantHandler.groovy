package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatState
import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.ChatStateRepositoryImpl
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class WantHandler extends AbstractHandler{
    File file
    ChatStateRepository chatStateRepository
    Logger log = LoggerFactory.getLogger(WantHandler)

    WantHandler() {
        super()
        chatStateRepository = ChatStateRepositoryImpl.instance
        file = new File("wanties.txt")
    }

    WantHandler(TgApi tgApi) {
        super(tgApi)
        chatStateRepository = ChatStateRepositoryImpl.instance
        file = new File("wanties.txt")
    }

    WantHandler(TgApi tgApi, File file, ChatStateRepository chatStateRepository) {
        super(tgApi)
        this.file = file
        this.chatStateRepository = chatStateRepository
    }

    @Override
    boolean doIsHandled(Message message) {
        return message.text.startsWith("/want") ||
                chatStateRepository.getState(message.chat) == ChatState.want
    }

    @Override
    void doHandle(Message message) {
        if(message.text.trim().startsWith("/want")) {
            chatStateRepository.setState(message.chat, ChatState.want)
            api.sendMessage(message.chat.id, "Чего изволите?")
        } else {
            file.append("${message.from.name}: ${message.text}\n")
            chatStateRepository.setState(message.chat,ChatState.noState)
            api.sendMessage(message.chat.id,"Готово! Хотелка \"${message.text}\" сохранена")
        }

    }
}
