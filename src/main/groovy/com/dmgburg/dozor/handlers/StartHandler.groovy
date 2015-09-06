package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.RolesRepository
import com.dmgburg.dozor.RolesRepositoryImpl
import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors

@CompileStatic
class StartHandler extends AbstractHandler {

    StartHandler(TgApi tgApi = LocalApi.instance, RolesRepository rolesRepository = RolesRepositoryImpl.instance) {
        super([Command.START],tgApi,rolesRepository)
    }

    @Override
    void doHandle(Message message) {
        Chat chat = message.chat
        GString text
        if(chat.isUser()){
            rolesRepository.addPendingRequest(message.from)
            text = "Привет ${message.from.name}, твой id ${message.from.id}, заявка отправлена администратору"
        } else if(chat.isGroup()){
            text = "Привет жителям ${chat.title}, я дурак и а вы не лечитесь и теперь я буду жить с вами!"
        }
        tgApi.sendMessage(chat.id, text)
    }
}
