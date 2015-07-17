package com.dmgburg.dozor.core

import com.dmgburg.dozor.domain.TelegramBotError
import com.dmgburg.dozor.domain.Update
import com.dmgburg.dozor.domain.UpdatesResult
import groovy.transform.CompileStatic
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.log4j.Logger
import org.codehaus.jackson.map.ObjectMapper
import com.dmgburg.dozor.requests.GetUpdatesRequest
import com.dmgburg.dozor.requests.Request
import com.dmgburg.dozor.requests.SendMessageRequest

@Singleton
class LocalApi implements TgApi{
    private static Logger log = Logger.getLogger(LocalApi)

    private String doRequest(Request request){
        HttpResponseDecorator resp
        try {
            RESTClient client = new RESTClient("https://api.telegram.org/")
            client.contentType = ContentType.TEXT

            resp = client.post(path: "/bot${Credentials.AUTH_TOKEN}/${request.methodName}",
                    body: request.parameters, requestContentType: ContentType.URLENC )
        } catch (HttpResponseException exception){
            try {
                resp = exception.response
                log.error("TelegramBotError executing request: $request: ${resp.data.str}",exception)
            } catch (Exception e){
                log.error("Exception in catch: ", e)
            }
        }
        log.debug("Request $request processed with result code ${resp.status}")
        return resp.data.str
    }

    public void sendMessage(int chatId, String message) {
        def request = new SendMessageRequest(chatId,message)
        doRequest(request)
    }

    public List<Update> getUpdates(int offset = 0) {
        def request
        if (offset != 0) {
            request = new GetUpdatesRequest(offset)
        } else {
            request = new GetUpdatesRequest()
        }
        def respResult = doRequest(request)
        def updates = new ObjectMapper().readValue(respResult, UpdatesResult)
        if(updates.result.size()>0) {
            log.info("Received updates: $updates")
        }
        updates.result
    }
}
