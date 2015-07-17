package com.dmgburg.dozor

import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.Message
import com.dmgburg.dozor.handlers.KsNewHandler
import com.dmgburg.dozor.handlers.PassedHandler
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import static org.mockito.Mockito.*

class PassedHandlerTest {
    Message message
    PassedHandler handler
    @Mock ChatStateRepository chatStateRepository
    @Mock KsRepository ksRepository
    @Mock TgApi tgApi
    Chat chat

    @Before
    void setUp(){
        MockitoAnnotations.initMocks(this)
        chat = new Chat()
        chat.id = 1
        message = new Message()
        message.chat = chat
        message.text = ""
        handler = new PassedHandler(tgApi, ksRepository, chatStateRepository)
    }

    @Test
    void "should remove ks from ksRepository"(){
        when(chatStateRepository.getState(chat)).thenReturn(ChatState.pass)
        message.text = 2
        handler.handle(message)
        verify(ksRepository).removeKs(eq(2))
    }

    @Test
    void "should not remove ks when message is not number"(){
        message.text = "some text"
        when(chatStateRepository.getState(chat)).thenReturn(ChatState.pass)
        handler.handle(message)
        verify(ksRepository,never()).removeKs(anyInt())
    }
}
