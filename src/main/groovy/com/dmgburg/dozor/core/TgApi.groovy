package com.dmgburg.dozor.core

import com.dmgburg.dozor.domain.Message
import com.dmgburg.dozor.domain.ReplyKeyboardMarkup
import com.dmgburg.dozor.domain.Update
import groovy.transform.CompileStatic

@CompileStatic
interface TgApi {
    void sendMessage(int chatId, String message)
    void sendMessage(int chatId, String message, ReplyKeyboardMarkup keyboardMarkup)
    void sendSticker(int chatId, String stickerId)
    List<Update> getUpdates()
}