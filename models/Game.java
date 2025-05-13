package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.enums.FriendshipLevel;
import models.enums.environment.Time;
import models.enums.types.ItemType;
import models.enums.types.NPCType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private final ArrayList<User> players; // The 3 players
    private GameMap gameMap;
    private GameState gameState;
    private final ArrayList<NPC> npcs;
    private HashMap<User, HashMap<User, Friendship>> userFriendships;
    private HashMap<User, HashMap<NPC, Integer>> npcFriendships;
    private HashMap<User, HashMap<User, HashMap<String, Boolean>>> talkHistory;
    // Each inner HashMap stores the messages and boolean of have they been read by the receiver

    public Game(ArrayList<User> players, int mapNumber) {
        this.players = players;
        this.gameMap = new GameMap(mapNumber);

        this.npcs = new ArrayList<>();

        for (NPCType npcType : NPCType.values()) {
            this.npcs.add(new NPC(npcType));
        }

        this.userFriendships = new HashMap<>();
        this.npcFriendships = new HashMap<>();

        for (User player : this.players) {
            HashMap<User, Friendship> playerFriendMap = new HashMap<>();
            for (User other : this.players) {
                if (!player.equals(other)) {
                    playerFriendMap.put(other, new Friendship());
                    player.setTalkedToToday(other, false);
                    player.setExchangedGiftToday(other, false);
                    player.setHasHuggedToday(other, false);
                    player.setExchangedFlowerToday(other, false);
                }
            }
            userFriendships.put(player, playerFriendMap);

            HashMap<NPC, Integer> npcFriendMap = new HashMap<>();
            for (NPC npc : this.npcs) {
                npcFriendMap.put(npc, 0);
                npc.setReceivedGiftToday(player, false);
                npc.setTalkedToToday(player, false);
            }
            npcFriendships.put(player, npcFriendMap);

            this.talkHistory = new HashMap<>();
            for (User sender : this.players) {
                HashMap<User, HashMap<String, Boolean>> talkMap = new HashMap<>();
                for (User receiver : this.players) {
                    if (!sender.equals(receiver)) {
                        talkMap.put(receiver, new HashMap<>());
                    }
                }
                talkHistory.put(sender, talkMap);
            }
        }

        for (NPC npc : this.npcs) {
            HashMap<User, Integer> thirdQuestTimeMap = new HashMap<>();
            HashMap<User, Boolean> thirdQuestBooleanMap = new HashMap<>();
            for (User player : this.players) {
                thirdQuestTimeMap.put(player, null);
                thirdQuestBooleanMap.put(player, false);
            }
            npc.setDaysLeftToUnlockThirdQuest(thirdQuestTimeMap);
            npc.setThirdQuestUnlocked(thirdQuestBooleanMap);
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

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

    public HashMap<User, HashMap<User, Friendship>> getUserFriendships() {
        return userFriendships;
    }

    public HashMap<User, HashMap<NPC, Integer>> getNpcFriendships() {
        return npcFriendships;
    }

    public HashMap<User, HashMap<User, HashMap<String, Boolean>>> getTalkHistory() {
        return talkHistory;
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
        // TODO: show unread messages when starting new turn
        // TODO: call changeDay() here if day has changed
        for (User player : this.players) {
            for (Artisan artisan : player.getFarm().getArtisans()) {
                if (artisan.getItemPending() != null) {
                    artisan.setTimeLeft(Math.max(artisan.getTimeLeft() - 1, 0));
                }
            }
        }
        saveGameState();
    }


    public Result changeDay() {
        StringBuilder message = new StringBuilder("A new day has begun. Here are the updates for today:\n");

        for (Farm farm : this.getGameMap().getFarms()) {
            message.append(farm.updateAnimals());
        }

        for (User player : this.players) {

            if (player.isDepressed()) {
                int days = Time.differenceInDays(player.getRejectionTime(), this.getGameState().getTime());
                message.append(player.getUsername()).append("'s marriage proposal was rejected ").append(days)
                        .append(" ago. Their energy for today is half the usual.");
                if (days > 7) {
                    player.setDepressed(false);
                }
                player.setEnergy(100);
            } else {
                player.setEnergy(200);
            }

            for (int i = this.players.indexOf(player); i < this.players.size(); i++) {
                User otherPlayer = this.players.get(i);
                if (!player.hasInteractedToday(otherPlayer)) {
                    this.changeFriendship(player, otherPlayer, -10);
                    message.append(player).append(" did not interact with ").append(otherPlayer.getUsername())
                            .append(". Their friendship decreased and is now ")
                            .append(this.getUserFriendship(player, otherPlayer).toString()).append(".\n");
                }
            }

            for (NPC npc : this.npcs) {
                if (this.getNpcFriendshipPoints(player, npc) / 200 >= 3) {
                    if (Math.random() < 0.5) {
                        ItemType itemType = npc.getType().getRandomGift();
                        Item item = Item.getItemByItemType(itemType);
                        Result result = player.getBackpack().addToInventory(item, 1);
                        if (result.success()) {
                            assert item != null;
                            message.append(npc.getName()).append(" has given you a ").append(item.getName() +
                                    " as a gift!");
                        }
                    }
                }

                if (npc.getDaysLeftToUnlockThirdQuest().get(player) != null) {
                    npc.changeThirdQuestTime(player, -1);
                    if (npc.getDaysLeftToUnlockThirdQuest().get(player) == 0) {
                        npc.unlockThirdQuest(player);
                        message.append(npc.getName()).append("'s third quest unlocked for ")
                                .append(player.getUsername()).append(".\n");
                    }
                }
            }
        }

        return new Result(true, message.toString());
    }

    public User getPlayerByUsername(String username) {
        for (User user : this.getPlayers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public NPC getNPCByName(String name) {
        for (NPC npc : this.getNpcs()) {
            if (npc.getName().equals(name)) {
                return npc;
            }
        }
        return null;
    }

    public Friendship getUserFriendship(User user1, User user2) {
        HashMap<User, Friendship> friendshipHashMap = userFriendships.get(user1);
        if (friendshipHashMap != null) {
            return friendshipHashMap.get(user2);
        }
        return null;
    }

    public int getNpcFriendshipPoints(User player, NPC npc) {
        HashMap<NPC, Integer> friendshipHashMap = npcFriendships.get(player);
        if (friendshipHashMap != null) {
            return friendshipHashMap.getOrDefault(npc, -1);
        }
        return -1;
    }

    public void changeFriendship(User player, NPC npc, int amount) {
        int friendshipPoints = getNpcFriendshipPoints(player, npc);
        if (friendshipPoints != -1) {
            friendshipPoints += amount;
            if (friendshipPoints > 799) {
                friendshipPoints = 799;
            }
            npcFriendships.get(player).put(npc, friendshipPoints);
            saveGameState();
        }
    }

    public void changeFriendship(User user1, User user2, int amount) {
        Friendship friendship = getUserFriendship(user1, user2);
        if (friendship != null) {
            friendship.updateFriendship(amount);
        }
    }


    public void changeFriendshipLevel(User user1, User user2, FriendshipLevel friendshipLevel) {
        Friendship friendship = getUserFriendship(user1, user2);
        if (friendship != null) {
            friendship.setLevel(friendshipLevel);
            saveGameState();
        }
    }

    private void saveGameState() {
        try (FileWriter writer = new FileWriter("games.json")) {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(App.getGames()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage(User sender, User receiver, String message) {
        if (talkHistory.containsKey(sender) && talkHistory.get(sender).containsKey(receiver)) {
            talkHistory.get(sender).get(receiver).put(message, false);
        }
    }

    public boolean isQuestUnlocked(NPC npc, User user, int index) {
        if (index == 0) {
            return true;
        }

        if (index == 1) {
            return this.getNpcFriendshipPoints(user, npc) / 200 >= 1;
        }

        if (index == 2) {
            return npc.getThirdQuestUnlocked().get(user);
        }

        return false;
    }
}