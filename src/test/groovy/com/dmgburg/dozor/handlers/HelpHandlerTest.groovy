package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.core.Application
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
    @Mock Application app
    Chat chat

    @Before
    void setUp(){
        MockitoAnnotations.initMocks(this)
        chat = new Chat()
        chat.id = 1
        message = new Message()
        message.chat = chat
        handler = new HelpHandler(app, tgApi, chatStateRepository)
    }

    @Test
    void "should handle /help"(){
        message.text = "/help"
        assert handler.isHandled(message)
    }
}
