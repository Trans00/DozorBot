package com.dmgburg.dozor.domain

import com.dmgburg.dozor.security.Role

class Player {
    int chatId
    Set<Role> roles

    Player(int chatId, Set<Role> roles) {
        this.chatId = chatId
        this.roles = roles
    }
}
