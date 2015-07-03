package com.dmgburg.dozor

import com.dmgburg.dozor.domain.Chat

import java.util.concurrent.ConcurrentHashMap

/**
 *
 * Created by Denis on 01.07.2015.
 */
@Singleton
class ChatStateRepository {
    Map<Chat,String> stateByChat = new ConcurrentHashMap<>()

    public String getState(Chat chat){
        stateByChat.get(chat)
    }

    public void setState(Chat chat, String state){
        stateByChat.put(chat,state)
    }
}
