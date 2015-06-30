package core

import domain.TelegramBotError
import domain.Update
import domain.UpdatesResult
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.log4j.Logger
import org.codehaus.jackson.map.ObjectMapper
import requests.GetUpdatesRequest
import requests.Request
import requests.SendMessageRequest

import java.nio.charset.Charset

/**
 * Created by Denis on 27.06.2015.
 */
class LocalApi {
    private static Logger log = Logger.getLogger(LocalApi)

    public static String doRequest(Request request){
        def mapper = new ObjectMapper()
        HttpResponseDecorator resp
        try {
            RESTClient client = new RESTClient("https://api.telegram.org/")
            client.contentType = ContentType.URLENC

            resp = client.post(path: "/bot${Credentials.AUTH_TOKEN}/${request.methodName}",
                    body: request.parameters )
        } catch (HttpResponseException exception){
            resp = exception.response
            def responseData = resp.data as Map<String,String>
            log.error("TelegramBotError executing request: $request: " +
                    "${mapper.readValue(responseData.keySet().last(),TelegramBotError).description}")
        }
        log.debug("Request $request processed with result code ${resp.status}")
        if(resp.data.size()>1){
            log.error("Responce data has more than one key for request $request")
        }
        return resp.data.keySet().last()
    }

    public static void sendMessage(int chatId, String message) {
        def request = new SendMessageRequest(chatId,message)
        doRequest(request)
    }

    public static List<Update> getUpdates(int offset = 0) {
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

    private static Map<String,byte[]> encode(Map<String,String> parameters){
        Map<String,byte[]> map = [:]
        parameters.each {String key, String value ->
            map.put(key,Charset.forName("KOI8-R").encode(value.toString()).array())
        }
        map
    }
}
