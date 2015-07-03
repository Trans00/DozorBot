package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.KcRepository
import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.domain.Message
import org.apache.log4j.Logger

class KcNewHandler implements Handler {
    Logger log = Logger.getLogger(KcNewHandler)
    @Override
    void handle(Message message) {
        if(message.text.trim().toLowerCase().startsWith("/ksnew")
                && message.text.trim().indexOf(" ") > 0
                && isKs(message.text.trim().substring(message.text.trim().indexOf(" ")).trim())) {
            List<String> ks = message.text.trim().substring(message.text.trim().indexOf(" ")).split(",")
            setKs(ks,message)
        } else if(ChatStateRepository.instance.getState(message.chat)== "ksnew"
                && isKs(message.text.trim())){
            List<String> ks = message.text.trim().split(",")
            setKs(ks,message)
        }else{
            ChatStateRepository.instance.setState(message.chat,"ksnew")
            LocalApi.sendMessage(message.chat.id, "Введите новые КС, фомат ввода: 1,1+,2,3,2+,1+,1,1,2+,null,null")
        }
    }

    public static boolean isKs(String str){
        str ==~ /(((\d(\+)?)|null),)*((\d(\+)?)|null)/
    }

    private setKs(List<String> ks,Message message){
        KcRepository.instance.setKs(ks)
        log.info("Установлены новые КС: ${KcRepository.instance.ks.toMapString()}")
        LocalApi.sendMessage(message.chat.id, "Установлены новые КС: \n${KcHandler.getKsString(KcRepository.instance.ks)}")
        ChatStateRepository.instance.setState(message.chat,"noState")
    }

    @Override
    boolean isHandled(Message message) {
        return message.text.toLowerCase().startsWith("/kcnew")||
               message.text.toLowerCase().startsWith("/ksnew")||
                ChatStateRepository.instance.getState(message.chat) == "ksnew"
    }
}
