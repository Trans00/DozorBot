package com.dmgburg.dozor

import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.User
import com.dmgburg.dozor.security.Role
import groovy.transform.CompileStatic

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

import static com.dmgburg.dozor.security.Role.Team
import static com.dmgburg.dozor.security.Role.Unauthentificated

@Singleton(strict = false)
@CompileStatic
class RolesRepositoryImpl implements RolesRepository{
    Map<Integer, List<Role>> rolesByChatId = new ConcurrentHashMap<>()
    Queue<User> pendingRequests = new ArrayBlockingQueue<>(10)

    RolesRepositoryImpl(){
        rolesByChatId.put(111268484,Role.values() as List)
        rolesByChatId.put(94601591,[Unauthentificated,Team])
        rolesByChatId.put(23468109,[Unauthentificated,Team])
        rolesByChatId.put(49007195,[Unauthentificated,Team]) //Zorgik
        rolesByChatId.put(48569908,[Unauthentificated,Team]) //Dizax
        rolesByChatId.put(21333294,[Unauthentificated,Team]) //Ksu-Ksu
        rolesByChatId.put(33418283,[Unauthentificated,Team]) //Lion
    }

    void addPendingRequest(User user){
        pendingRequests.add(user)
    }

    User getPendingRequest(){
        pendingRequests.poll()
    }

    @Override
    void addRole(Chat chat, Role role) {
        addRole(chat.id,role)
    }

    @Override
    void addRole(int chatId, Role role) {
        getRoles(chatId).add(role)
    }

    @Override
    void removeRole(Chat chat, Role role) {
        removeRole(chat.id,role)
    }

    @Override
    void removeRole(int chatId, Role role) {
        getRoles(chatId).remove(role)
    }

    @Override
    List<Role> getRoles(Chat chat) {
        getRoles(chat.id)
    }

    @Override
    List<Role> getRoles(int chatId) {
        def roles = rolesByChatId.get(chatId)
        if(!roles){
            roles = new CopyOnWriteArrayList<Role>([Unauthentificated])
            rolesByChatId.put(chatId,roles)
        }
        return roles
    }
}
