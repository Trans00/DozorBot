package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.KsRepository
import com.dmgburg.dozor.KsRepositoryImpl
import com.dmgburg.dozor.RolesRepository
import com.dmgburg.dozor.RolesRepositoryImpl
import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic

import static com.dmgburg.dozor.handlers.Command.KS

@CompileStatic
class KsHandler extends AbstractHandler{
    KsRepository ksRepository

    KsHandler(TgApi tgApi = LocalApi.instance,
              KsRepository ksRepository = KsRepositoryImpl.instance,
              RolesRepository rolesRepository = RolesRepositoryImpl.instance) {
        super([KS],tgApi,rolesRepository)
        acceptUnauthentificated = false
        this.ksRepository = ksRepository
    }

    @Override
    void doHandle(Message message) {
        Map<String,String> ks = ksRepository.ks
        if(ks.size() >0) {
            String result = getKsString(ks)
            tgApi.sendMessage(message.chat.id, result)
        } else {
            tgApi.sendMessage(message.chat.id, "Все коды взяты")
        }
    }

    public static String getKsString(Map<String,String> ks){
        StringBuilder sb = new StringBuilder()
        ks.each {key,value ->
            sb.append(key).append(":").append(value).append("\n")
        }
        sb.toString()
    }
}
