package com.dmgburg.dozor

import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.security.Role
import org.junit.Before
import org.junit.Test

import static com.dmgburg.dozor.security.Role.Admin
import static com.dmgburg.dozor.security.Role.Unauthentificated

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
        rolesRepository.addRole(chat,Admin)
        assert rolesRepository.getRoles(chat) == [Unauthentificated,Admin]
    }

    @Test
    void "sould return unauthentifcated, when user was not added role when it was added"(){
        assert rolesRepository.getRoles(chat) == [Unauthentificated]
    }

    @Test
    void "sould remove role on remove role"(){
        rolesRepository.addRole(chat,Admin)
        rolesRepository.removeRole(chat,Admin)
        assert rolesRepository.getRoles(chat) == [Unauthentificated]
    }

    @Test
    void "sould do nothing when romove role user doesn't have"(){
        rolesRepository.removeRole(chat,Admin)
        assert rolesRepository.getRoles(chat) == [Unauthentificated]
    }
}
