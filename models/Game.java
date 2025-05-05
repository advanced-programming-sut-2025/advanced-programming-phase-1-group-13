package models;

import models.enums.environment.Time;
import models.enums.environment.Weather;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private ArrayList<User> players; // The 3 players
    private GameMap gameMap;
    private GameState gameState;
    private ArrayList<NPC> npcs;
    private HashMap<User, HashMap<User, Friendship>> userFriendships;
    private HashMap<User, HashMap<NPC, Friendship>> npcFriendships;

    public Game(ArrayList<User> players) {
        this.players = players;

        this.npcs = new ArrayList<>(List.of(
                new NPC(NPCType.CLINT), new NPC(NPCType.MORRIS), new NPC(NPCType.PIERRE),
                new NPC(NPCType.ROBIN), new NPC(NPCType.WILLY), new NPC(NPCType.MARNIE),
                new NPC(NPCType.GUS), new NPC(NPCType.SEBASTAIN), new NPC(NPCType.ABIGAIL),
                new NPC(NPCType.HARVEY), new NPC(NPCType.LEA)
        ));

        this.userFriendships = new HashMap<>();
        this.npcFriendships = new HashMap<>();

        for (User player : this.players) {
            HashMap<User, Friendship> playerFriendMap = new HashMap<>();
            for (User other : this.players) {
                if (!player.equals(other)) {
                    playerFriendMap.put(other, new Friendship());
                }
            }
            userFriendships.put(player, playerFriendMap);

            HashMap<NPC, Friendship> npcFriendMap = new HashMap<>();
            for (NPC npc : this.npcs) {
                npcFriendMap.put(npc, new Friendship());
            }
            npcFriendships.put(player, npcFriendMap);
        }
    }

    public ArrayList<User> getPlayers() {
        return players;
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void newGame(String username1, String username2, String username3) {
        // TODO
    }

    public void setGameMap(int mapNumber) {
        // TODO
    }

    public void loadGame(String callerUsername) {
        // TODO
    }

    public boolean forceTerminateGame(boolean vote1, boolean vote2, boolean vote3) {
        // TODO
        return false;
    }

    public void nextTurn(String callerUsername) {
        // TODO
    }

    public Friendship getUserFriendship(User user1, User user2) {
        HashMap<User, Friendship> friendshipHashMap = userFriendships.get(user1);
        if (friendshipHashMap != null) {
            return friendshipHashMap.get(user2);
        }
        return null;
    }

    public Friendship getNpcFriendship(User player, NPC npc) {
        HashMap<NPC, Friendship> friendshipHashMap = npcFriendships.get(player);
        if (friendshipHashMap != null) {
            return friendshipHashMap.get(npc);
        }
        return null;
    }

    public void increaseFriendship(User player, NPC npc, int amount) {
        Friendship friendship = getNpcFriendship(player, npc);
        if (friendship != null) {
            friendship.increase(amount);
        }
    }

    public void increaseFriendship(User user1, User user2, int amount) {
        Friendship friendship = getUserFriendship(a, b);
        if (friendship != null) {
            friendship.increase(amount);
        }
    }
}