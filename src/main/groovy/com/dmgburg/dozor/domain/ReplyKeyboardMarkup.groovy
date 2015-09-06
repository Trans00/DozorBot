package com.dmgburg.dozor.domain

import groovy.transform.CompileStatic
import org.codehaus.jackson.annotate.JsonProperty

@CompileStatic
class ReplyKeyboardMarkup {
    String[][] keyboard
    @JsonProperty(value = "resize_keyboard")
    boolean resizeKeyboard
    @JsonProperty(value = "one_time_keyboard")
    boolean oneTimeKeyboard
    boolean selective

    ReplyKeyboardMarkup(List<List<String>> keyboard, boolean oneTime = false) {
        this.keyboard = keyboard
        this.oneTimeKeyboard = oneTime
    }

    ReplyKeyboardMarkup(String[][] keyboard) {
        this.keyboard = keyboard
    }

    boolean getSelective(){selective}

    boolean getResizeKeyboard() {
        return resizeKeyboard
    }

    boolean getOneTimeKeyboard() {
        return oneTimeKeyboard
    }
}
