package com.dmgburg.dozor.domain

class Player {
    int id
    String name

    Player(){}

    Player(int id, String name) {
        this.id = id
        this.name = name
    }

    void setId(int chatId) {
        this.id = chatId
    }

    int getId() {
        return id
    }
}
