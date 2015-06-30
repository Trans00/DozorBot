package handlers

import core.LocalApi
import domain.Chat
import domain.Message

import java.nio.charset.Charset

/**
 * Created by Denis on 29.06.2015.
 */
class StartHandler implements Handler {

    @Override
    void handle(Message message) {
        Chat chat = message.chat
        String text
        if(chat.isUser()){
            text = "Привет ${message.from}, зачем ты призвал меня?"
        }else if(chat.isGroup()){
            text = "Привет населению ${chat.title}"
        }
        LocalApi.sendMessage(chat.id, text)
    }

    @Override
    boolean isHandled(Message message) {
        return message.text.startsWith("/start")
    }
}
