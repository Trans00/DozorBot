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

import static com.dmgburg.dozor.handlers.Command.PASSED
import static com.dmgburg.dozor.security.Role.Team

@Slf4j
@CompileStatic
class PassedHandler extends AbstractHandler {


    KsRepository ksRepository
    ChatStateRepository chatStateRepository

    PassedHandler(TgApi tgApi = LocalApi.instance,
                  KsRepository ksRepository,
                  ChatStateRepository chatStateRepository = ChatStateRepositoryImpl.instance,
                  RolesRepository rolesRepository = RolesRepositoryImpl.instance) {
        super([PASSED], tgApi, rolesRepository)
        acceptedRoles = [Team]
        this.ksRepository = ksRepository
        this.chatStateRepository = chatStateRepository
    }

    @Override
    void doHandle(Message message) {
        if (message.text.trim().startsWith("/pass")) {
            chatStateRepository.setState(message.chat, ChatState.PASS)
            tgApi.sendMessage(message.chat.id, "Введите номер взятого кода")
        } else if (chatStateRepository.getState(message.chat) == ChatState.PASS
                && message.text ==~ /\d+/) {
            ksRepository.removeKs(Integer.valueOf(message.text))
            log.info("Код ${message.text} взят")
            tgApi.sendMessage(message.chat.id, "Код ${message.text} взят")
            chatStateRepository.setState(message.chat, ChatState.NO_STATE)
        }
    }

    @Override
    boolean doIsHandled(Message message) {
        return message.text.startsWith("/pass") ||
                chatStateRepository.getState(message.chat) == ChatState.PASS
    }
}
