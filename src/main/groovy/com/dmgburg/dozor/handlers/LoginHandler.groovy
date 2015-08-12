package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.CredentialsRepository
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message

class LoginHandler extends AbstractHandler{

    LoginHandler() {
        super()
    }

    LoginHandler(TgApi tgApi) {
        super(tgApi)
    }

    @Override
    boolean doIsHandled(Message message) {
        return message.text.startsWith("/login")
    }

    @Override
    void doHandle(Message message) {
        def cred = message.text.split(" ")[1].split(":")
        CredentialsRepository.instance.login = cred[0]
        CredentialsRepository.instance.password = cred[1]
        api.sendMessage(message.chat.id,
                "Установлены новые пользовательские данные: логин '${CredentialsRepository.instance.login}', пароль '${CredentialsRepository.instance.password}'")
    }
}
