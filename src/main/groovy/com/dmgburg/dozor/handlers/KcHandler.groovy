package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.KcRepository
import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.domain.Message

class KcHandler  implements Handler{
    @Override
    void handle(Message message) {
        Map<Integer,String> ks = KcRepository.instance.ks
        if(ks.size() >0) {
            String result = getKsString(ks)
            LocalApi.sendMessage(message.chat.id, result)
        } else {
            LocalApi.sendMessage(message.chat.id, "Все коды взяты")
        }
    }

    @Override
    boolean isHandled(Message message) {
        return message.text.trim().toLowerCase() =="/кс" ||
                message.text.trim().toLowerCase() == "/kc" ||
                message.text.trim().toLowerCase() == "/ks"
    }

    public static String getKsString(Map<Integer,String> ks){
        StringBuilder sb = new StringBuilder()
        ks.each {Integer key,String value ->
            sb.append(key).append(": ").append(value).append("\n")
        }
        sb.toString()
    }
}
