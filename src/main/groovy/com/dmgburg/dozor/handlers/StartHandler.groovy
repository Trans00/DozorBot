package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.Message

class StartHandler implements Handler {

    @Override
    void handle(Message message) {
        Chat chat = message.chat
        GString text
        if(chat.isUser()){
            text = "Привет ${message.from.name}, зачем ты пробудил меня?"
        }else if(chat.isGroup()){
            text = "Привет жителям ${chat.title}, я дурак и не лечесь и теперь я буду жить с вами!"
        }
        LocalApi.sendMessage(chat.id, text)
    }

    @Override
    boolean isHandled(Message message) {
        return message.text.toLowerCase().startsWith("/start")
    }
}
