package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatState
import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.ChatStateRepositoryImpl
import com.dmgburg.dozor.KsRepository
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.omg.PortableInterceptor.SUCCESSFUL
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.dmgburg.dozor.handlers.Command.*

@Slf4j
@CompileStatic
class KsNewHandler extends AbstractHandler {

    KsRepository ksRepository
    ChatStateRepository chatStateRepository

    KsNewHandler(KsRepository ksRepository, ChatStateRepository chatStateRepository = ChatStateRepositoryImpl.instance) {
        super([KSNEW])
        this.ksRepository = ksRepository
        this.chatStateRepository = chatStateRepository
    }

    KsNewHandler(TgApi tgApi, KsRepository ksRepository, ChatStateRepository chatStateRepository = ChatStateRepositoryImpl.instance) {
        super([KSNEW],tgApi)
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
            chatStateRepository.setState(message.from.chat,ChatState.ksNew)
            api.sendMessage(message.chat.id, "Введите новые КС, фомат ввода: 1,1+,2,3,2+,1+,1,1,2+,null,null\n" +
                    "Для отмены введите /cancel")
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
        return super.doIsHandled(message) ||
                chatStateRepository.getState(message.from.chat) == ChatState.ksNew
    }
}
