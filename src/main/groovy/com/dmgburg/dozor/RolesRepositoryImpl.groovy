package com.dmgburg.dozor

import com.dmgburg.dozor.domain.Player
import com.dmgburg.dozor.domain.User
import groovy.util.logging.Slf4j
import org.codehaus.jackson.map.ObjectMapper

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

@Slf4j
@Singleton(strict = false)
//@CompileStatic
class RolesRepositoryImpl implements RolesRepository {
    Map<Integer, Player> playersByChatId = new ConcurrentHashMap<>()
    Map<Integer, User> pendingRequestsById = new ConcurrentHashMap<>()
    private ObjectMapper mapper = new ObjectMapper();
    private String filename;

    private RolesRepositoryImpl() {
        this(System.getenv().get("OPENSHIFT_DATA_DIR") + File.separator + "players.json");
    }

    private RolesRepositoryImpl(String filename) {
        this.filename = filename;
        playersByChatId.put(111268484, new Player(111268484, "DinyaG"))
        playersByChatId.put(49007195, new Player(100394567, "GuerreDLC"))
        playersByChatId.put(49007195, new Player(46698719, "Yohanga "))
        playersByChatId.put(49007195, new Player(23468109, "Damet"))
        playersByChatId.put(49007195, new Player(49007195, "Zorgik"))
        playersByChatId.put(48569908, new Player(48569908, "Dizax"))
        playersByChatId.put(33418283, new Player(33418283, "Lion"))
        playersByChatId.put(34445229, new Player(34445229, "Mari"))
        playersByChatId.put(25055826, new Player(25055826, "EKaterina"))
        playersByChatId.put(159080001, new Player(159080001, "Ruslan"))
        playersByChatId.put(21103097, new Player(21103097, "Dasha"))
        playersByChatId.put(94601591, new Player(94601591, "Vladimir"))

        try {
            List<Player> players = mapper.readValue(new File(filename),
                    mapper.getTypeFactory().constructCollectionType(List.class, Player));
            for (Player player : players) {
                playersByChatId.put(player.id, player)
            }
        } catch (EOFException ignore) {
        } catch (Exception e) {
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
        pendingRequestsById.put(user.id, user)
    }

    @Override
    void authentificate(int id, boolean auth) {
        User user = pendingRequestsById.remove(id)
        if (!auth) {
            playersByChatId.remove(id)
        } else if (user) {
            playersByChatId.put(user.id, new Player(id, user.username))
        }
    }

    @Override
    boolean getAuthentificated(int id) {
        return playersByChatId.containsKey(id)
    }

    void saveGame() {
        try {
            def file = new File(filename)
            log.info("Persisting players: " + file.absolutePath);
            mapper.writeValue(file, playersByChatId.values());
        } catch (IOException e) {
            log.error("Save players failed: ", e);
        }
    }

}
