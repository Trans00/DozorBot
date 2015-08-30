package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.domain.Message
import groovy.util.logging.Slf4j

import static com.dmgburg.dozor.handlers.Command.TEA

@Slf4j
class TeaHandler extends AbstractHandler {

    List<String> teaStickers = []
    Random generator = new Random();

    TeaHandler() {
        super([TEA])
        dropEmptyText = false
        TeaHandler.class.classLoader.getResourceAsStream("teaStickers.txt")?.readLines()?.each {
            teaStickers << it
        }
    }

    @Override
    void doHandle(Message message) {
        api.sendSticker(message.from.id, teaStickers[getRandomSticker()])
    }

    int getRandomSticker() {
        return generator.nextInt(teaStickers.size() - 1)
    }
}
