package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.domain.Message

class HelpHandler  implements Handler{
    @Override
    void handle(Message message) {
        if(message.chat.isUser()) {
            LocalApi.sendMessage(message.chat.id,"/start Send back greeting \n" +
                    "/help Get this help message")
        }
    }

    @Override
    boolean isHandled(Message message) {
        return message.text.toLowerCase().startsWith("/help")
    }
}
