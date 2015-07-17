package com.dmgburg.dozor

import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.Message
import com.dmgburg.dozor.handlers.CancelHandler
import com.dmgburg.dozor.handlers.KsHandler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import static org.mockito.Mockito.*

class CancelHandlerTest {

    Message message
    CancelHandler handler
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
        handler = new CancelHandler(tgApi, chatStateRepository)
    }

    @Test
    void "should set chat state to noState"(){
        handler.handle(message)
        verify(chatStateRepository).setState(eq(chat),eq(ChatState.noState))
    }

    @Test
    void "should handle /cancel"(){
        message.text = "/cancel"
        assert handler.isHandled(message)
    }
}
