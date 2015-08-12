package com.dmgburg.dozor.requests

import com.dmgburg.dozor.handlers.AbstractHandler

class SendStickerRequest extends AbstructRequest{

    final String methodName = "sendSticker"
    Map<String,String> parameters

    SendStickerRequest(int chatId, String stickerFileId) {
        this.parameters = [chat_id: chatId as String, sticker: stickerFileId]
    }
}
