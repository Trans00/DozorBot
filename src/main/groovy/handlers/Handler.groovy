package handlers

import domain.Message
import domain.Update

/**
 * Created by Denis on 29.06.2015.
 */
interface Handler {
    void handle(Message message)
    boolean isHandled(Message message)
}