package com.dmgburg.dozor.requests

import com.dmgburg.dozor.domain.Message
import com.dmgburg.dozor.domain.ReplyKeyboardMarkup
import groovy.json.JsonBuilder
import org.codehaus.jackson.map.ObjectMapper

class SendMessageRequest extends AbstructRequest {
    static ObjectMapper mapper = new ObjectMapper();
    final String methodName = "sendMessage"
    Map<String, String> parameters

    SendMessageRequest(int chatId, String message, ReplyKeyboardMarkup markup) {
        this.parameters = [chat_id: chatId as String, text: message]
        if (markup) {
            parameters.put("reply_markup", mapper.writeValueAsString(markup))
        }
    }


}
