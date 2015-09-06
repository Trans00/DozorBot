package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.CredentialsRepository
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message

import static com.dmgburg.dozor.handlers.Command.LOGIN

class LoginHandler extends AbstractHandler{

    LoginHandler() {
        super()
    }

    LoginHandler(TgApi tgApi) {
        super([LOGIN], tgApi)
    }

    @Override
    boolean doIsHandled(Message message) {
        return message.text.startsWith("/login")
    }

    @Override
    void doHandle(Message message) {
        def cred = message.text.split(" ")[1].split(":")
        CredentialsRepository.instance.gameLogin = cred[0]
        CredentialsRepository.instance.gamePassword = cred[1]
        tgApi.sendMessage(message.chat.id,
                "Установлены новые пользовательские данные: логин '${CredentialsRepository.instance.gameLogin}', пароль '${CredentialsRepository.instance.gamePassword}'")
    }
}
