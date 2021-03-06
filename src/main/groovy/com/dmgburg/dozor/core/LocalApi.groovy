package com.dmgburg.dozor.core

import com.dmgburg.dozor.CredentialsRepository
import com.dmgburg.dozor.domain.ReplyKeyboardMarkup
import com.dmgburg.dozor.domain.Update
import com.dmgburg.dozor.domain.UpdatesResult
import com.dmgburg.dozor.requests.GetUpdatesRequest
import com.dmgburg.dozor.requests.Request
import com.dmgburg.dozor.requests.SendMessageRequest
import com.dmgburg.dozor.requests.SendStickerRequest
import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.codehaus.jackson.map.ObjectMapper

@Slf4j
@Singleton
class LocalApi implements TgApi {

    private String doRequest(Request request) {
        HttpResponseDecorator resp
        try {
            RESTClient client = new RESTClient("https://api.telegram.org/")
            client.contentType = ContentType.TEXT

            resp = client.post(path: "/bot${CredentialsRepository.instance.getAuthToken()}/${request.methodName}",
                    body: request.parameters, requestContentType: ContentType.URLENC)
        } catch (HttpResponseException exception) {
            try {
                resp = exception.response
                log.error("TelegramBotError executing request: $request: ${resp.data.str}", exception)
            } catch (Exception e) {
                log.error("Exception in catch: ", e)
            }
        }
        log.debug("Request $request processed with result code ${resp.status}")
        return resp.data.str
    }

    public void sendMessage(int chatId, String message, ReplyKeyboardMarkup markup = null) {
        def request = new SendMessageRequest(chatId, message, markup)
        doRequest(request)
    }

    public void sendSticker(int chatId, String stickerId) {
        def request = new SendStickerRequest(chatId, stickerId)
        doRequest(request)
    }

    public List<Update> getUpdates(int offset = 0) {
        try {
            def request
            if (offset != 0) {
                request = new GetUpdatesRequest(offset)
            } else {
                request = new GetUpdatesRequest()
            }
            def respResult = doRequest(request)
            def updates = new ObjectMapper().readValue(respResult, UpdatesResult)
            if (updates.result.size() > 0) {
                log.debug("Received updates: $updates")
            }
            updates.result
        } catch (Exception e) {
            log.error("Unhandled exceptiuon on get updates:", e)
        }
    }
}
