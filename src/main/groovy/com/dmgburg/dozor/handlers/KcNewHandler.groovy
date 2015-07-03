package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.KcRepository
import com.dmgburg.dozor.domain.Message
import org.apache.log4j.Logger

class KcNewHandler implements Handler {
    Logger log = Logger.getLogger(KcNewHandler)
    @Override
    void handle(Message message) {
        List<String> ks = message.text.trim().substring(5).split(",")
        KcRepository.instance.setKs(ks)
        log.info("Установлены новые КС: ${KcRepository.instance.ks.toMapString()}")
    }

    @Override
    boolean isHandled(Message message) {
        return message.text.toLowerCase().startsWith("/kcnew")||
               message.text.toLowerCase().startsWith("/ksnew")
    }
}
