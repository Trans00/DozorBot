package com.dmgburg.dozor.domain

import groovy.transform.CompileStatic
import org.codehaus.jackson.annotate.JsonProperty

@CompileStatic
class TelegramBotError {
    boolean ok
    @JsonProperty(value = "error_code")int errorCode
    String description


    @Override
    public String toString() {
        return "TelegramBotError{" +
                "ok=" + ok +
                ", errorCode=" + errorCode +
                ", description='" + description + '\'' +
                '}';
    }
}
