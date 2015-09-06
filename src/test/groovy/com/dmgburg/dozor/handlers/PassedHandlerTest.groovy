package com.dmgburg.dozor.handlers

import com.dmgburg.dozor.ChatState
import com.dmgburg.dozor.ChatStateRepository
import com.dmgburg.dozor.KsRepository
import com.dmgburg.dozor.RolesRepository
import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.Message
import com.dmgburg.dozor.domain.User
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import static com.dmgburg.dozor.security.Role.Team
import static com.dmgburg.dozor.security.Role.Unauthentificated
import static org.mockito.Mockito.*

class PassedHandlerTest {
    Message message
    PassedHandler handler
    @Mock ChatStateRepository chatStateRepository
    @Mock KsRepository ksRepository
    @Mock TgApi tgApi
    @Mock RolesRepository rolesRepository
    Chat chat

    @Before
    void setUp(){
        MockitoAnnotations.initMocks(this)
        chat = new Chat()
        chat.id = 1
        message = new Message()
        message.chat = chat
        message.from = new User(id: 1)
        message.text = ""
        when(rolesRepository.getRoles(chat)).thenReturn([Team])
        handler = new PassedHandler(tgApi, ksRepository, chatStateRepository,rolesRepository)
    }

    @Test
    void "should remove ks from ksRepository"(){
        when(chatStateRepository.getState(chat)).thenReturn(ChatState.PASS)
        message.text = 2
        handler.handle(message)
        verify(ksRepository).removeKs(eq(2))
    }

    @Test
    void "should not remove ks when message is not number"(){
        message.text = "some text"
        when(chatStateRepository.getState(chat)).thenReturn(ChatState.PASS)
        handler.handle(message)
        verify(ksRepository,never()).removeKs(anyInt())
    }

    @Test
    void "should not remove ks when user unauthentificated"(){
        when(rolesRepository.getRoles(chat)).thenReturn([Unauthentificated])
        message.text = "some text"
        when(chatStateRepository.getState(chat)).thenReturn(ChatState.PASS)
        handler.handle(message)
        verify(ksRepository,never()).removeKs(anyInt())
    }
}
