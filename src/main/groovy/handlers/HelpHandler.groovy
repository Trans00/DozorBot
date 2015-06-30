package handlers

import core.LocalApi
import domain.Message

/**
 * Created by Denis on 29.06.2015.
 */
class HelpHandler  implements Handler{
    @Override
    void handle(Message message) {
        if(message.chat.isUser()) {
            LocalApi.sendMessage("/start Send back greeting" +
                    "/help Get this help message")
        }
    }

    @Override
    boolean isHandled(Message message) {
        return message.text.startsWith("/help")
    }
}
