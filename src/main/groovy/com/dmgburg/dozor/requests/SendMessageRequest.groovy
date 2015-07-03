package com.dmgburg.dozor.requests

class SendMessageRequest extends AbstructRequest{
    final String methodName = "sendMessage"
    Map<String,String> parameters

    SendMessageRequest(int chatId, String message) {
        this.parameters = [chat_id: chatId as String, text: message]
    }


}
