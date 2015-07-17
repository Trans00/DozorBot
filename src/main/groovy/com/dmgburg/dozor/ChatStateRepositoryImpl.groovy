package com.dmgburg.dozor

import com.dmgburg.dozor.domain.Chat

import java.util.concurrent.ConcurrentHashMap

@Singleton
class ChatStateRepositoryImpl implements ChatStateRepository {
    Map<Chat,ChatState> stateByChat = new ConcurrentHashMap<>()

    public ChatState getState(Chat chat){
        stateByChat.get(chat)
    }

    public void setState(Chat chat, ChatState state){
        stateByChat.put(chat,state)
    }
}
