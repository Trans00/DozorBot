package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic

@CompileStatic
class StartHandler extends AbstractHandler {

    StartHandler() {
        super([Command.START])
    }

    StartHandler(TgApi tgApi) {
        super([Command.START],tgApi)
    }

    @Override
    void doHandle(Message message) {
        Chat chat = message.chat
        GString text
        if(chat.isUser()){
            text = "Привет ${message.from.name}, зачем ты пробудил меня?"
        } else if(chat.isGroup()){
            text = "Привет жителям ${chat.title}, я дурак и а вы не лечитесь и теперь я буду жить с вами!"
        }
        api.sendMessage(chat.id, text)
    }
}
