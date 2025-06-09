package com.ap_project.models;

import com.ap_project.models.enums.FriendshipLevel;
import com.ap_project.models.enums.Menu;
import com.ap_project.models.enums.environment.Time;
import com.ap_project.models.enums.types.ItemType;
import com.ap_project.models.enums.types.NPCType;
import com.ap_project.models.enums.types.ShopType;
import com.ap_project.models.enums.types.TileType;
import com.ap_project.models.farming.Crop;
import com.ap_project.models.farming.Tree;
import com.ap_project.models.trade.Trade;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private final ArrayList<User> players; // The 3 players
    private GameMap gameMap;
    private NPCVillage village;
    private boolean isInNPCVillage;
    private GameState gameState;
    private final ArrayList<NPC> npcs;
    private HashMap<User, HashMap<User, Friendship>> userFriendships;
    private HashMap<User, HashMap<NPC, Integer>> npcFriendships;
    private final ArrayList<Trade> trades;
    private HashMap<User, HashMap<User, HashMap<String, Boolean>>> talkHistory;

    public Game(ArrayList<User> players) {
        this.players = players;
        this.gameState = new GameState();
        this.isInNPCVillage = false;
        this.village = new NPCVillage();
        this.gameMap = null;
        App.setCurrentGame(this);

        this.npcs = new ArrayList<>();

        for (NPCType npcType : NPCType.values()) {
            this.npcs.add(new NPC(npcType));
        }

        this.userFriendships = new HashMap<>();
        this.npcFriendships = new HashMap<>();
        this.trades = new ArrayList<>();

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

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        //saveGameState();
    }

    public void setUserFriendships(HashMap<User, HashMap<User, Friendship>> userFriendships) {
        this.userFriendships = userFriendships;
        //saveGameState();
    }

    public void setNpcFriendships(HashMap<User, HashMap<NPC, Integer>> npcFriendships) {
        this.npcFriendships = npcFriendships;
        //saveGameState();
    }

    public void setTalkHistory(HashMap<User, HashMap<User, HashMap<String, Boolean>>> talkHistory) {
        this.talkHistory = talkHistory;
        // saveGameState();
    }

    public NPCVillage getVillage() {
        return village;
    }

    public boolean isInNPCVillage() {
        return isInNPCVillage;
    }

    public void setInNPCVillage(boolean inNPCVillage) {
        isInNPCVillage = inNPCVillage;
    }

    public void setVillage(NPCVillage village) {
        this.village = village;
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
        //saveGameState();
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

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public void addTrade(Trade trade) {
        this.trades.add(trade);
    }

    public void forceTerminateGame(boolean vote1, boolean vote2, boolean vote3) {
        if (vote1 && vote2 && vote3) {
            for (User player : this.players) {
                player.setActiveGame(null);
                if (player.getMostEarnedMoney() < player.getBalance()) {
                    player.setMostEarnedMoney((int) player.getBalance());
                }
                player.resetPlayer();
            }
        }
        App.setCurrentMenu(Menu.PRE_GAME_MENU);
    }

    public String nextTurn(User previousPlayer) {
        int previousPlayerIndex = players.indexOf(previousPlayer);
        User newPlayer = players.get((previousPlayerIndex + 1) % players.size());
        App.setLoggedIn(newPlayer);

        if (previousPlayerIndex == players.size() - 1) {
            Time.advanceOneHour(this);
        }

        StringBuilder resultMessage = new StringBuilder("New messages:\n");
        HashMap<User, HashMap<String, Boolean>> hashmap1 = this.talkHistory.get(newPlayer); // todo: previousPlayer OR newPlayer ?!
        for (User sender : hashmap1.keySet()) {
            resultMessage.append("------------------------------------------------------------\n");
            resultMessage.append("  From ").append(sender.getUsername()).append(":\n");

            HashMap<String, Boolean> hashmap2 = hashmap1.get(sender);
            for (String message : hashmap2.keySet()) {
                if (hashmap2.get(message)) {
                    resultMessage.append("    ").append(message).append("\n");
                }
            }

        }

        return resultMessage.toString();
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

//            for (int i = this.players.indexOf(player); i < this.players.size(); i++) {
//                User otherPlayer = this.players.get(i);
//                if (!player.hasInteractedToday(otherPlayer)) {
//                    this.changeFriendship(player, otherPlayer, -10);
//                    message.append(player).append(" did not interact with ").append(otherPlayer.getUsername())
//                            .append(". Their friendship decreased and is now ")
//                            .append(this.getUserFriendship(player, otherPlayer).toString()).append(".\n");
//                }
//            }

            for (NPC npc : this.npcs) {
                if (this.getNpcFriendshipPoints(player, npc) / 200 >= 3) {
                    if (Math.random() < 0.5) {
                        ItemType itemType = npc.getType().getRandomGift();
                        Item item = Item.getItemByItemType(itemType);
                        Result result = player.getBackpack().addToInventory(item, 1);
                        if (result.success) {
                            assert item != null;
                            message.append(npc.getName()).append(" has given you a ").append(item.getName())
                                    .append(" as a gift!");
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

        for (Shop shop : this.getVillage().getShops()) {
            for (Good good : shop.getShopInventory()) {
                good.setNumberSoldToUsersToday(0);
            }
        }

//        for (User player : this.players) {
//            int income = 0;
//            for (ShippingBin shippingBin : player.getFarm().getShippingBins()) {
//                for (Item item : shippingBin.getItemsToShip()) {
//                    income += item.getSellPrice();
//                }
//            }
//            player.changeBalance(income);
//            message.append(player.getUsername()).append("'s shipping bins have been emptied and they earned ")
//                    .append(income).append("g.\n");
//        }

        for (Tile tile : gameMap.getAllTiles()) {
            if (tile.getType() == TileType.TREE) {
                Tree tree = (Tree) tile.getItemPlacedOnTile();
                tree.incrementDaySinceLastHarvest();

                tree.incrementDayInStage();
                if (tree.getDayInStage() == tree.getStage()) {
                    tree.incrementStage();
                }

            } else if (tile.getType() == TileType.GROWING_CROP) {
                Crop crop = (Crop) tile.getItemPlacedOnTile();
                crop.incrementDaySinceLastHarvest();

                crop.incrementDayInStage();
                if (crop.getDayInStage() == crop.getStagesTimes().get(crop.getStage())) {
                    crop.incrementStage();
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

    public Shop getShopByShopType(ShopType shopType) {
        for (Shop shop : this.getVillage().getShops()) {
            if (shop.getType().equals(shopType)) {
                return shop;
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
            //saveGameState();
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
            //saveGameState();
        }
    }

    private void saveGameState() {
        /*
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getName().equals("activeGame");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .setPrettyPrinting()
                .create();

        try (FileWriter writer = new FileWriter("games.json")) {
            writer.write(gson.toJson(App.getGames()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
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

    public Trade getTradeById(int id) {
        for (Trade trade : this.getTrades()) {
            if (trade.getId() == id) {
                return trade;
            }
        }
        return null;
    }

}
