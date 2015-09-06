package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.RolesRepository
import com.dmgburg.dozor.RolesRepositoryImpl
import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.Message
import com.dmgburg.dozor.security.Role
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Slf4j
@CompileStatic
abstract class AbstractHandler implements Handler {
    Logger messageLog = LoggerFactory.getLogger("messages")
    protected TgApi tgApi
    protected dropEmptyText = true
    protected List<Command> commands
    protected List<Role> acceptedRoles = [Role.Unauthentificated]
    protected RolesRepository rolesRepository
    AbstractHandler(List<Command> commands = [],
                    TgApi tgApi = LocalApi.instance,
                    RolesRepository rolesRepository = RolesRepositoryImpl.instance) {
        this.commands = commands
        this.tgApi = tgApi
        this.rolesRepository = rolesRepository
    }

    @Override
    void handle(Message message) {
        try {
            messageLog.info("${message.from.name}: ${message.text}. $message")
            if(isAuthentificated(message)) {
                doHandle(message)
            }else if(!authorized(message.from.chat)) {
                tgApi.sendMessage(message.chat.id,"У Вас нет прав на выполнение этой команды, для авторизации введите команду /start")
            } else {
                tgApi.sendMessage(message.chat.id,"Невозможно выполнить команду в этом чате")
            }
        } catch (Exception e) {
            log.error("doHandle of ${this.class} threw an exeption on message ${message.toString()}: ", e)
        }
    }

    boolean isAuthentificated(Message message) {
        authorized(message.chat)&& authorized(message.from.chat)
    }

    private boolean authorized(Chat chat){
        rolesRepository.getRoles(chat).find{acceptedRoles.contains(it)}
    }

    @Override
    boolean isHandled(Message message) {
        try {
            if (!message.text && dropEmptyText) {
                return false
            }
            return doIsHandled(message.normalize())
        } catch (Exception e) {
            log.error("doIshandled of ${this.class} threw an exeption on message ${message.toString()}: ", e)
        }
    }

    @Override
    List<Command> getCommands() {
        return commands
    }

    boolean doIsHandled(Message message){
        return commands.find{it.command == message.command}
    }

    abstract void doHandle(Message message)

}
