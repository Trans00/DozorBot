package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.core.LocalApi
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Message
import groovy.transform.CompileStatic

@CompileStatic
class HelpHandler extends AbstractHandler{
    HelpHandler(TgApi tgApi) {
        super(tgApi)
    }

    @Override
    void doHandle(Message message) {
        if(message.chat.isUser()) {
            api.sendMessage(message.chat.id,"/start Пооздороваться \n" +
                    "/help Выдать это сообщение \n" +
                    "/ksnew Ввести новые КС\n" +
                    "/ks Отобразить имеющиеся КС\n" +
                    "/pass Ввести номер взятого кода\n" +
                    "/cancel Прервать ожидания ввода данных для предыдущей команды\n")
        }
    }

    @Override
    boolean doIsHandled(Message message) {
        return message.text.toLowerCase().startsWith("/help")
    }
}
