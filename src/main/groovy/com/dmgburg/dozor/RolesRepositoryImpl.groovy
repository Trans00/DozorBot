package com.dmgburg.dozor

import com.dmgburg.dozor.domain.Chat
import com.dmgburg.dozor.domain.Player
import com.dmgburg.dozor.domain.User
import com.dmgburg.dozor.security.Role
import groovy.util.logging.Slf4j
import org.codehaus.jackson.map.ObjectMapper

import java.util.concurrent.*

import static com.dmgburg.dozor.security.Role.Team
import static com.dmgburg.dozor.security.Role.Unauthentificated

@Slf4j
@Singleton(strict = false)
//@CompileStatic
class RolesRepositoryImpl implements RolesRepository {
    Map<Integer, Player> playersByChatId = new ConcurrentHashMap<>()
    Queue<User> pendingRequests = new ArrayBlockingQueue<>(10)
    private ObjectMapper mapper = new ObjectMapper();
    private String filename;

    private RolesRepositoryImpl(){
        this(System.getenv().get("OPENSHIFT_DATA_DIR") + File.separator + "players.json");
    }

    private RolesRepositoryImpl(String filename) {
        this.filename = filename;
        playersByChatId.put(111268484, new Player(111268484, Role.values() as Set))
        playersByChatId.put(94601591, new Player(94601591, [Unauthentificated, Team] as Set))
        playersByChatId.put(23468109, new Player(23468109, [Unauthentificated, Team] as Set))
        playersByChatId.put(49007195, new Player(49007195, [Unauthentificated, Team] as Set)) //Zorgik
        playersByChatId.put(48569908, new Player(48569908, [Unauthentificated, Team] as Set)) //Dizax
        playersByChatId.put(33418283, new Player(33418283, [Unauthentificated, Team] as Set)) //Lion
        playersByChatId.put(34445229, new Player(34445229, [Unauthentificated, Team] as Set)) //Mari
        playersByChatId.put(49007195, new Player(49007195, [Unauthentificated, Team] as Set)) //Eugene
        playersByChatId.put(25055826, new Player(25055826, [Unauthentificated, Team] as Set)) //EKaterina
        playersByChatId.put(159080001, new Player(159080001, [Unauthentificated, Team] as Set)) //Ruslan
        playersByChatId.put(21103097, new Player(21103097, [Unauthentificated, Team] as Set)) //Dasha
        playersByChatId.put(94601591, new Player(94601591, [Unauthentificated, Team] as Set)) //Vladimir

        try {
            List<Player> players = mapper.readValue(new File(filename),
                    mapper.getTypeFactory().constructCollectionType(List.class, Player));
            for (Player player: players){
                playersByChatId.put(player.chatId,player)
            }
        } catch (EOFException ignore){
        }catch (Exception e) {
            log.error("Load players failed with unexpected error: ", e);
        }

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                saveGame();
            }
        }, 30, 30, TimeUnit.SECONDS);
    }

    void addPendingRequest(User user) {
        pendingRequests.add(user)
    }

    User getPendingRequest() {
        pendingRequests.poll()
    }

    @Override
    void addRole(Chat chat, Role role) {
        addRole(chat.id, role)
    }

    @Override
    void addRole(int chatId, Role role) {
        getRoles(chatId).add(role)
    }

    @Override
    void removeRole(Chat chat, Role role) {
        removeRole(chat.id, role)
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
        def player = playersByChatId.get(chatId)
        if (!player) {
            player = new Player(chatId, new ConcurrentSkipListSet<Role>([Unauthentificated]))
            playersByChatId.put(chatId, player)
        }
        return player.roles
    }

    void saveGame() {
        try {
            log.info("Persisting players: " + filename);
            mapper.writeValue(new File(filename), playersByChatId.values());
        } catch (IOException e) {
            log.error("Save players failed: ", e);
        }
    }

}
