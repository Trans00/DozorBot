package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.domain.Message
import groovy.util.logging.Slf4j

@Slf4j
class TeaHandler extends AbstractHandler {

    List<String> teaStickers = []
    Random generator = new Random();

    TeaHandler() {
        dropEmptyText = false
        TeaHandler.class.classLoader.getResourceAsStream("teaStickers.txt")?.readLines()?.each {
            teaStickers << it
        }
    }

    @Override
    boolean doIsHandled(Message message) {
        return message.text.startsWith("/tea")
    }

    @Override
    void doHandle(Message message) {
        api.sendSticker(message.from.id, teaStickers[getRandomSticker()])
    }

    int getRandomSticker() {
        return generator.nextInt(teaStickers.size() - 1)
    }
}
