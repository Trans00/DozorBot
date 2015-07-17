package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.KsRepository
import com.dmgburg.dozor.KsRepositoryImpl
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic

@CompileStatic
class KsHandler extends AbstractHandler{
    KsRepository ksRepository

    KsHandler(TgApi tgApi, KsRepository ksRepository) {
        super(tgApi)
        this.ksRepository = ksRepository
    }

    @Override
    void doHandle(Message message) {
        Map<Integer,String> ks = ksRepository.ks
        if(ks.size() >0) {
            String result = getKsString(ks)
            api.sendMessage(message.chat.id, result)
        } else {
            api.sendMessage(message.chat.id, "Все коды взяты")
        }
    }

    @Override
    boolean doIsHandled(Message message) {
        return message.text.trim().toLowerCase() == "/kc" ||
                message.text.trim().toLowerCase() == "/ks"
    }

    public static String getKsString(Map<Integer,String> ks){
        StringBuilder sb = new StringBuilder()
        ks.each {Integer key,String value ->
            sb.append(key).append(":").append(value).append("\n")
        }
        sb.toString()
    }
}
