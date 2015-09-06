package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatState
import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.ChatStateRepositoryImpl
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import groovy.util.logging.Slf4j

import static com.dmgburg.dozor.handlers.Command.WANT

@Slf4j
class WantHandler extends AbstractHandler{
    File file
    ChatStateRepository chatStateRepository

    WantHandler() {
        super([WANT])
        chatStateRepository = ChatStateRepositoryImpl.instance
        file = new File("wanties.txt")
    }

    WantHandler(TgApi tgApi, File file = new File("wanties.txt"), ChatStateRepository chatStateRepository = ChatStateRepositoryImpl.instance) {
        super([WANT],tgApi)
        this.file = file
        this.chatStateRepository = chatStateRepository
    }

    @Override
    boolean doIsHandled(Message message) {
        return super.doIsHandled(message) ||
                chatStateRepository.getState(message.chat) == ChatState.WANT
    }

    @Override
    void doHandle(Message message) {
        if(message.text.trim().startsWith("/want")) {
            chatStateRepository.setState(message.chat, ChatState.WANT)
            tgApi.sendMessage(message.chat.id, "Чего изволите?")
        } else {
            file.append("${message.from.name}: ${message.text}\n")
            chatStateRepository.setState(message.chat,ChatState.NO_STATE)
            tgApi.sendMessage(message.chat.id,"Готово! Хотелка \"${message.text}\" сохранена")
        }

    }
}
