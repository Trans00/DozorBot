package com.dmgburg.dozor.handlers

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

import static org.mockito.Mockito.*

class KsHandlerTest {
    Message message
    KsHandler handler
    @Mock KsRepository ksRepository
    @Mock TgApi tgApi
    @Mock RolesRepository rolesRepository

    @Before
    void setUp(){
        MockitoAnnotations.initMocks(this)
        Chat chat = new Chat()
        chat.id = 1
        message = new Message()
        message.chat = chat
        message.from = new User(id:1)
        handler = new KsHandler(tgApi, ksRepository,rolesRepository)
        when(rolesRepository.getAuthentificated(any(Chat))).thenReturn(true)
    }

    @Test
    void "sould handle /ks"(){
        message.text = "/ks"
        assert handler.isHandled(message)
    }

    @Test
    void "should return ks when present"(){
        when(ksRepository.getKs()).thenReturn([1:"1",2:"2+",3:"1",4:"null"])
        handler.handle(message)
        verify(tgApi).sendMessage(eq(1),eq("1:1\n2:2+\n3:1\n4:null\n"))
    }

    @Test
    void "should return all done when no ks present"(){
        when(ksRepository.getKs()).thenReturn([:])
        handler.handle(message)
        verify(tgApi).sendMessage(eq(1),eq("Все коды взяты"))
    }

    @Test
    void "should send reject message when called for unauthorized chat"(){
        when(rolesRepository.getAuthentificated(any(Chat))).thenReturn([Unauthentificated])
        handler.handle(message)
        verify(tgApi).sendMessage(eq(message.chat.id),eq("У Вас нет прав на выполнение этой команды, для авторизации введите команду /start"))
    }
}
