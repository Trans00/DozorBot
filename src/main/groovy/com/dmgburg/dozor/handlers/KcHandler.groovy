package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.KcRepository
import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.domain.Message

class KcHandler  implements Handler{
    @Override
    void handle(Message message) {
        StringBuilder sb = new StringBuilder()
        KcRepository.instance.ks.each {Integer key,String value ->
            sb.append(key).append(": ").append(value).append("\n")
        }
        LocalApi.sendMessage(message.chat.id, sb.toString())
    }

    @Override
    boolean isHandled(Message message) {
        return message.text.trim().toLowerCase() =="/кс" ||
                message.text.trim().toLowerCase() == "/kc" ||
                message.text.trim().toLowerCase() == "/ks"
    }
}
