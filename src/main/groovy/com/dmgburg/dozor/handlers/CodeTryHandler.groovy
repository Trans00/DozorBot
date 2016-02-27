package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.KsRepository
import com.dmgburg.dozor.RolesRepository
import com.dmgburg.dozor.RolesRepositoryImpl
import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import com.dmgburg.dozor.security.Role
import groovy.transform.CompileStatic

@CompileStatic
class CodeTryHandler extends AbstractHandler{
    KsRepository ksRepository

    CodeTryHandler(List<Command> commands = [],
                   TgApi tgApi = LocalApi.instance,
                   RolesRepository rolesRepository = RolesRepositoryImpl.instance,
                   KsRepository ksRepository) {
        super(commands, tgApi, rolesRepository)
        this.ksRepository = ksRepository
        acceptedRoles = [Role.Team]
    }

    @Override
    void doHandle(Message message) {
        String response = ksRepository.tryCode(prepareCode(message))
        tgApi.sendMessage(message.from.chat.id,response)
    }

    private static String prepareCode(Message message) {
        return message.text.substring(2).replace("д","d").replace("р","r")
    }

    @Override
    boolean doIsHandled(Message message) {
        return message.text.startsWith(".,")
    }

}
