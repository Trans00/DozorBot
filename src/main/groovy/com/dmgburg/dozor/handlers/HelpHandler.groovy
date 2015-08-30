package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic

@CompileStatic
class HelpHandler extends AbstractHandler{

    HelpHandler() {
        super([Command.HELP])
    }

    HelpHandler(TgApi tgApi) {
        super([Command.HELP],tgApi)
    }

    @Override
    void doHandle(Message message) {
            api.sendMessage(message.chat.id,"Здесь будет сообщение о возможных сейчас командах, а пока здесь саммон: " +
                    "@${message?.from?.username}")
    }
}
