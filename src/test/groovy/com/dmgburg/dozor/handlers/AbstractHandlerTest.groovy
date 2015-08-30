package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.Message
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class AbstractHandlerTest {
    private static Message message
    private static AbstractHandler handler
    private static Message processedMessage
    @Mock TgApi api

    @Before
    void setUp(){
        Chat chat = new Chat()
        chat.id = 1
        message = new Message()
        message.chat = chat
        handler = new TestHandler(api)
    }



    class TestHandler extends AbstractHandler{

        TestHandler(TgApi tgApi) {
            super(tgApi)
        }

        @Override
        boolean doIsHandled(Message message) {
            return false
        }

        @Override
        void doHandle(Message message) {
            processedMessage = message
        }
    }
}
