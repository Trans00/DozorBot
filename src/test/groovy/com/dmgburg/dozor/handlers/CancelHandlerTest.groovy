package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatState
import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.Message
import com.dmgburg.dozor.domain.User
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
        message.from = new User(id: 1)
        message.chat = chat
        handler = new CancelHandler(tgApi, chatStateRepository)
    }

    @Test
    void "should set chat state to noState"(){
        handler.handle(message)
        verify(chatStateRepository).setState(eq(chat),eq(ChatState.NO_STATE))
    }

    @Test
    void "should handle /cancel"(){
        message.text = "/cancel"
        assert handler.isHandled(message)
    }
}
