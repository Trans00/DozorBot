package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatState
import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.ChatStateRepositoryImpl
import com.dmgburg.dozor.KsRepository
import com.dmgburg.dozor.RolesRepository
import com.dmgburg.dozor.RolesRepositoryImpl
import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import static com.dmgburg.dozor.handlers.Command.*
import static com.dmgburg.dozor.security.Role.Team

@Slf4j
@CompileStatic
class KsNewHandler extends AbstractHandler {

    KsRepository ksRepository
    ChatStateRepository chatStateRepository

    KsNewHandler(TgApi tgApi = LocalApi.instance,
                 KsRepository ksRepository,
                 ChatStateRepository chatStateRepository = ChatStateRepositoryImpl.instance,
                 RolesRepository rolesRepository = RolesRepositoryImpl.instance) {
        super([KSNEW], tgApi, rolesRepository)
        acceptedRoles = [Team]
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
        } else if(chatStateRepository.getState(message.chat)== ChatState.KS_NEW
                && isKs(message.text)){
            List<String> ks = message.text.split(",") as List<String>
            setKs(ks,message)
        }else{
            chatStateRepository.setState(message.from.chat,ChatState.KS_NEW)
            tgApi.sendMessage(message.chat.id, "Введите новые КС, фомат ввода: 1,1+,2,3,2+,1+,1,1,2+,null,null\n" +
                    "Для отмены введите /cancel")
        }
    }

    public static boolean isKs(String str){
        str ==~ /(((\d(\+)?)|null),)*((\d(\+)?)|null)/
    }

    private setKs(List<String> ks,Message message){
        ksRepository.setKs(ks)
        log.info("Установлены новые КС: ${ksRepository.ks.toMapString()}")
        tgApi.sendMessage(message.chat.id, "Установлены новые КС: \n${KsHandler.getKsString(ksRepository.ks)}")
        chatStateRepository.setState(message.chat,ChatState.NO_STATE)
    }

    @Override
    boolean doIsHandled(Message message) {
        return super.doIsHandled(message) ||
                chatStateRepository.getState(message.from.chat) == ChatState.KS_NEW
    }
}
