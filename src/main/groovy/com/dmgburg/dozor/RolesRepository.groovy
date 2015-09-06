package com.dmgburg.dozor

import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.User
import com.dmgburg.dozor.security.Role

interface RolesRepository {

    void addPendingRequest(User user)
    User getPendingRequest()
    void addRole(Chat chat,Role role)
    void addRole(int chatId,Role role)
    void removeRole(Chat chat,Role role)
    void removeRole(int chatId,Role role)
    List<Role> getRoles(Chat chat)
    List<Role> getRoles(int chatId)
}