package com.dmgburg.dozor

import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.Message
import com.dmgburg.dozor.handlers.CancelHandler
import com.dmgburg.dozor.handlers.HelpHandler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HelpHandlerTest {

    Message message
    HelpHandler handler
    @Mock ChatStateRepository chatStateRepository
    @Mock TgApi tgApi
    Chat chat

    @Before
    void setUp(){
        MockitoAnnotations.initMocks(this)
        chat = new Chat()
        chat.id = 1
        message = new Message()
        message.chat = chat
        handler = new HelpHandler(tgApi)
    }

    @Test
    void "should handle /help"(){
        message.text = "/help"
        assert handler.isHandled(message)
    }
}
