package com.dmgburg.dozor.domain

import org.codehaus.jackson.annotate.JsonProperty

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
