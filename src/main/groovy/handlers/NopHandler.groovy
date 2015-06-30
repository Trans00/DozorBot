package handlers

import domain.Message

/**
 * Created by Denis on 29.06.2015.
 */
class NopHandler implements Handler{
    @Override
    void handle(Message update) {

    }

    @Override
    boolean isHandled(Message update) {
        return false
    }
}
