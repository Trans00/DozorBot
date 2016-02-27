package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatState
import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.ChatStateRepositoryImpl
import com.dmgburg.dozor.RolesRepository
import com.dmgburg.dozor.RolesRepositoryImpl
import com.dmgburg.dozor.core.Application
import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import com.dmgburg.dozor.domain.ReplyKeyboardMarkup
import groovy.transform.CompileStatic

import static com.dmgburg.dozor.security.Role.Team

@CompileStatic
class HelpHandler extends AbstractHandler{
    Application application
    ChatStateRepository chatStateRepository

    public HelpHandler(Application application,
                TgApi tgApi = LocalApi.instance,
                ChatStateRepository chatStateRepository = ChatStateRepositoryImpl.instance,
                RolesRepository rolesRepository = RolesRepositoryImpl.instance) {
        super([Command.HELP], tgApi,rolesRepository)
        acceptedRoles = [Team]
        this.application = application
        this.chatStateRepository = chatStateRepository
    }

    @Override
    void doHandle(Message message) {
        if(!application) {
            tgApi.sendMessage(message.chat.id, "Здесь будет сообщение о возможных сейчас командах, а пока здесь саммон: " +
                    "@${message?.from?.username}")
        }
        def markup = new ReplyKeyboardMarkup([['1','2','3'],['5'],['7','8','9']] as String[][])
        tgApi.sendMessage(message.chat.id,'Some reply',markup)
        ChatState state = chatStateRepository.getState(message.from.chat)
        if(state != ChatState.NO_STATE){

        }
    }
}
