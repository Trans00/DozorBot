package com.dmgburg.dozor

import com.dmgburg.dozor.domain.Chat
import org.junit.Before
import org.junit.Test

class RolesRepositoryImplTest {
    RolesRepository rolesRepository
    Chat chat

    @SuppressWarnings("NewInstanceOfSingleton")
    @Before
    void setUp(){
        chat = new Chat(id:1)
        rolesRepository = new RolesRepositoryImpl()
    }

    @Test
    void "sould return user role when it was added"(){
        rolesRepository.authentificate(chat.id, true)
        assert rolesRepository.getAuthentificated(chat.id) == true
    }

    @Test
    void "sould return unauthentifcated, when user was not added role when it was added"(){
        assert rolesRepository.getAuthentificated(chat.id) == false
    }

    @Test
    void "sould remove role on remove role"(){
        rolesRepository.authentificate(chat.id, true)
        rolesRepository.authentificate(chat.id, false)
        assert rolesRepository.getAuthentificated(chat.id) == false
    }

    @Test
    void "sould do nothing when romove role user doesn't have"(){
        rolesRepository.authentificate(chat.id, false)
        assert rolesRepository.getAuthentificated(chat.id) == false
    }
}
