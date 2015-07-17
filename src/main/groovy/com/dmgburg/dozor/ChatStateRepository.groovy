package com.dmgburg.dozor

import com.dmgburg.dozor.domain.Chat

interface ChatStateRepository {
    public ChatState getState(Chat chat)

    public void setState(Chat chat, ChatState state)
}