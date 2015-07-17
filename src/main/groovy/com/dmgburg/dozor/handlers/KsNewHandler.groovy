package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatState
import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.KsRepository
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic
import org.apache.log4j.Logger

@CompileStatic
class KsNewHandler extends AbstractHandler {
    Logger log = Logger.getLogger(KsNewHandler)

    KsRepository ksRepository
    ChatStateRepository chatStateRepository

    KsNewHandler(TgApi tgApi, KsRepository ksRepository, ChatStateRepository chatStateRepository) {
        super(tgApi)
        this.ksRepository = ksRepository
        this.chatStateRepository = chatStateRepository
    }

    @Override
    void doHandle(Message message) {
        if(message.text.startsWith("/ksnew")
                && message.text.indexOf(" ") > 0
                && isKs(message.text.substring(message.text.indexOf(" ")+1).trim())) {
            List<String> ks = message.text.substring(message.text.indexOf(" ")+1).split(",") as List<String>
            setKs(ks,message)
        } else if(chatStateRepository.getState(message.chat)== ChatState.ksNew
                && isKs(message.text)){
            List<String> ks = message.text.split(",") as List<String>
            setKs(ks,message)
        }else{
            chatStateRepository.setState(message.chat,ChatState.ksNew)
            api.sendMessage(message.chat.id, "Введите новые КС, фомат ввода: 1,1+,2,3,2+,1+,1,1,2+,null,null")
        }
    }

    public static boolean isKs(String str){
        str ==~ /(((\d(\+)?)|null),)*((\d(\+)?)|null)/
    }

    private setKs(List<String> ks,Message message){
        ksRepository.setKs(ks)
        log.info("Установлены новые КС: ${ksRepository.ks.toMapString()}")
        api.sendMessage(message.chat.id, "Установлены новые КС: \n${KsHandler.getKsString(ksRepository.ks)}")
        chatStateRepository.setState(message.chat,ChatState.noState)
    }

    @Override
    boolean doIsHandled(Message message) {
        return message.text.startsWith("/kcnew")||
               message.text.startsWith("/ksnew")||
                chatStateRepository.getState(message.chat) == ChatState.ksNew
    }
}
