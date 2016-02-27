package com.dmgburg.dozor

import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.User
import com.dmgburg.dozor.security.Role
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentSkipListSet

import static com.dmgburg.dozor.security.Role.Team
import static com.dmgburg.dozor.security.Role.Unauthentificated

@Slf4j
@Singleton(strict = false)
@CompileStatic
class RolesRepositoryImpl implements RolesRepository{
    Map<Integer, Set<Role>> rolesByChatId = new ConcurrentHashMap<>()
    Queue<User> pendingRequests = new ArrayBlockingQueue<>(10)

    private RolesRepositoryImpl(){
        rolesByChatId.put(111268484,Role.values() as Set)
        rolesByChatId.put(94601591,[Unauthentificated,Team] as Set)
        rolesByChatId.put(23468109,[Unauthentificated,Team] as Set)
        rolesByChatId.put(49007195,[Unauthentificated,Team] as Set) //Zorgik
        rolesByChatId.put(48569908,[Unauthentificated,Team] as Set) //Dizax
        rolesByChatId.put(21333294,[Unauthentificated,Team] as Set) //Ksu-Ksu
        rolesByChatId.put(33418283,[Unauthentificated,Team] as Set) //Lion
        rolesByChatId.put(34445229,[Unauthentificated,Team] as Set) //Mari
        rolesByChatId.put(49007195,[Unauthentificated,Team] as Set) //Eugene
        rolesByChatId.put(25055826,[Unauthentificated,Team] as Set) //EKaterina
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
    Collection<Role> getRoles(Chat chat) {
        getRoles(chat.id)
    }

    @Override
    Collection<Role> getRoles(int chatId) {
        def roles = rolesByChatId.get(chatId)
        if(!roles){
            roles = new ConcurrentSkipListSet<Role>([Unauthentificated])
            rolesByChatId.put(chatId,roles)
        }
        return roles
    }
}
