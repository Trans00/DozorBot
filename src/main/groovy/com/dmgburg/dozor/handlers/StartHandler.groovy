package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic

@CompileStatic
class StartHandler extends AbstractHandler {

    StartHandler(TgApi tgApi) {
        super(tgApi)
    }

    @Override
    void doHandle(Message message) {
        Chat chat = message.chat
        GString text
        if(chat.isUser()){
            text = "Привет ${message.from.name}, зачем ты пробудил меня?"
        }else if(chat.isGroup()){
            text = "Привет жителям ${chat.title}, я дурак и не лечесь и теперь я буду жить с вами!"
        }
        api.sendMessage(chat.id, text)
    }

    @Override
    boolean doIsHandled(Message message) {
        return message.text.toLowerCase().startsWith("/start")
    }
}
