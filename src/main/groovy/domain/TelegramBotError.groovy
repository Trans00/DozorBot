package domain

import org.codehaus.jackson.annotate.JsonProperty

/**
 * Created by Denis on 28.06.2015.
 */
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
