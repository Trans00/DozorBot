package com.dmgburg.dozor

import com.dmgburg.dozor.core.TgApi
import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.Message
import com.dmgburg.dozor.handlers.KsHandler
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

    @Before
    void setUp(){
        MockitoAnnotations.initMocks(this)
        Chat chat = new Chat()
        chat.id = 1
        message = new Message()
        message.chat = chat
        handler = new KsHandler(tgApi, ksRepository)
    }

    @Test
    void "sould handle /ks"(){
        message.text = "/ks"
        assert handler.isHandled(message)
    }

    @Test
    void "sould handle /kc"(){
        message.text = "/kc"
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
}
