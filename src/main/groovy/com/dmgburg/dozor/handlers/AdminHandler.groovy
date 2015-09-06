package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatState
import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.ChatStateRepositoryImpl
import com.dmgburg.dozor.CredentialsRepository
import com.dmgburg.dozor.RolesRepository
import com.dmgburg.dozor.RolesRepositoryImpl
import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import com.dmgburg.dozor.domain.ReplyKeyboardMarkup
import com.dmgburg.dozor.security.Role


class AdminHandler extends AbstractHandler {

    List<ChatState> acceptedStates = [ChatState.ADMIN,
                                      ChatState.LOGIN,
                                      ChatState.PASSWORD,
                                      ChatState.GAME_LOGIN,
                                      ChatState.GAME_PASSWORD,
                                      ChatState.MANAGE_USERS]

    ChatStateRepository chatStateRepository
    CredentialsRepository credentialsRepository

    AdminHandler(TgApi tgApi = LocalApi.instance,
                 ChatStateRepository chatStateRepository = ChatStateRepositoryImpl.instance,
                 RolesRepository rolesRepository = RolesRepositoryImpl.instance,
                 CredentialsRepository credentialsRepository = CredentialsRepository.instance) {
        super([Command.ADMIN,
               Command.LOGIN,
               Command.PASSWORD,
               Command.GAME_LOGIN,
               Command.GAME_PASSWORD,
               Command.MANAGE_USERS], tgApi, rolesRepository)
        acceptedRoles = [Role.Admin]
        this.chatStateRepository = chatStateRepository
        this.credentialsRepository = credentialsRepository
    }

    @Override
    void doHandle(Message message) {
        if (message.command) {
            handleCommand(message)
        } else if (message.argument) {
            def state = chatStateRepository.getState(message.from.chat)
            switch (state) {
                case ChatState.LOGIN:
                    credentialsRepository.login = message.argument
                    tgApi.sendMessage(message.from.chat.id, "Установлено новый логин пользователя: ${credentialsRepository.login}")
                    chatStateRepository.setState(message.from.chat, ChatState.ADMIN)
                    break
                case ChatState.PASSWORD:
                    credentialsRepository.password = message.argument
                    tgApi.sendMessage(message.from.chat.id, "Установлено новый пароль пользователя: ${credentialsRepository.password}")
                    chatStateRepository.setState(message.from.chat, ChatState.ADMIN)
                    break
                case ChatState.GAME_LOGIN:
                    credentialsRepository.gameLogin = message.argument
                    tgApi.sendMessage(message.from.chat.id, "Установлено новый логин игры: ${credentialsRepository.gameLogin}")
                    chatStateRepository.setState(message.from.chat, ChatState.ADMIN)
                    break
                case ChatState.GAME_PASSWORD:
                    credentialsRepository.gamePassword = message.argument
                    tgApi.sendMessage(message.from.chat.id, "Установлено новый пароль игры: ${credentialsRepository.gamePassword}")
                    chatStateRepository.setState(message.from.chat, ChatState.ADMIN)
                    break
                case ChatState.MANAGE_USERS:
                    int id = Integer.valueOf(message.argument.split(" ")[0])
                    def choise = message.argument.split(" ")[1]
                    def role
                    switch (choise) {
                        case "Команда":
                            role = Role.Team
                            break
                        case "Админ":
                            role = Role.Admin
                            break
                        default: role = Role.Unauthentificated
                    }
                    rolesRepository.addRole(id, role)
                    tgApi.sendMessage(message.from.chat.id, "Для пользователя $id добавлена роль: ${rolesRepository.getRoles(id)}")
                    chatStateRepository.setState(message.from.chat, ChatState.ADMIN)
                    break
                case ChatState.URL:
                    credentialsRepository.url = message.argument
                    tgApi.sendMessage(message.from.chat.id, "Установлено новый url игры: ${credentialsRepository.url}")
                    chatStateRepository.setState(message.from.chat, ChatState.ADMIN)
                    break
                default: throw new IllegalArgumentException("Unknown chat state: $state")
            }
        }
    }

    private void handleCommand(Message message) {
        switch (message.command){
            case Command.ADMIN.command:
                if (message.chat.isGroup()) {
                    tgApi.sendMessage(message.chat.id, "Администрирование недоступно в общем чате")
                }
                chatStateRepository.setState(message.from.chat, ChatState.ADMIN)
                List markup = []
                commands.findAll { it != Command.ADMIN }.each { markup << ["/${it.command}"] }
                tgApi.sendMessage(message.chat.id, "Режим администратора", new ReplyKeyboardMarkup(markup))
                break
            case Command.LOGIN.command:
                chatStateRepository.setState(message.from.chat, ChatState.LOGIN)
                tgApi.sendMessage(message.chat.id, "Введите новый логин")
                break
            case Command.PASSWORD.command:
                chatStateRepository.setState(message.from.chat, ChatState.PASSWORD)
                tgApi.sendMessage(message.chat.id, "Введите новый пароль")
                break
            case Command.GAME_LOGIN.command:
                chatStateRepository.setState(message.from.chat, ChatState.GAME_LOGIN)
                tgApi.sendMessage(message.chat.id, "Введите новый логин игры")
                break
            case Command.GAME_PASSWORD.command:
                chatStateRepository.setState(message.from.chat, ChatState.GAME_PASSWORD)
                tgApi.sendMessage(message.chat.id, "Введите новый пароль игры")
                break
            case Command.MANAGE_USERS.command:
                def user = rolesRepository.pendingRequest
                if (user) {
                    chatStateRepository.setState(message.from.chat, ChatState.MANAGE_USERS)
                    List markup = [["${user.id} Команда"], ["${user.id} Админ"], ["${user.id} Отклонить"]]
                    tgApi.sendMessage(message.chat.id, "Заявка: ${user?.name} ${user.id}", new ReplyKeyboardMarkup(markup,true))
                }
                break
            case Command.URL.command:
                chatStateRepository.setState(message.from.chat, ChatState.URL)
                tgApi.sendMessage(message.chat.id, "Введите новый url игры")
                break
            default:
                throw new IllegalArgumentException("Unknown command: ${message.command}")
        }
    }

    @Override
    boolean doIsHandled(Message message) {
        return super.doIsHandled(message) ||
                acceptedStates.contains(chatStateRepository.getState(message.from.chat))
    }
}
