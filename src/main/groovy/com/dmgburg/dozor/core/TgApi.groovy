package com.dmgburg.dozor.core

import com.dmgburg.dozor.domain.Message
import com.dmgburg.dozor.domain.Update
import groovy.transform.CompileStatic

@CompileStatic
interface TgApi {
    void sendMessage(int chatId, String message)
    List<Update> getUpdates()
}