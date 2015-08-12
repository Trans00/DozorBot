package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.domain.Message

import java.util.stream.IntStream

class TeaHandler extends AbstractHandler{

    List<String> teaStickers = []
    Random generator = new Random();
    TeaHandler(){
        dropEmptyText = false
        new File("123.txt").readLines().each {
            teaStickers << it
        }
    }

    @Override
    boolean doIsHandled(Message message) {
        return message.text.startsWith("/tea")
    }

    @Override
    void doHandle(Message message) {
        api.sendSticker(message.from.id,teaStickers[getRandomSticker()])
    }

    int getRandomSticker() {
        return generator.nextInt(teaStickers.size()-1)
    }
}
