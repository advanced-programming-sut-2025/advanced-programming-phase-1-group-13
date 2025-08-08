package com.ap_project.common.models;

import com.ap_project.common.models.enums.FriendshipLevel;
import com.ap_project.common.models.enums.environment.Time;
import com.ap_project.common.models.enums.types.ItemType;
import com.ap_project.common.models.enums.types.NPCType;
import com.ap_project.common.models.enums.types.ShopType;
import com.ap_project.common.models.farming.Crop;
import com.ap_project.common.models.farming.Tree;
import com.ap_project.common.models.trade.Trade;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Game {
    private final ArrayList<User> players;
    private final NPCVillage village;
    private boolean isInNPCVillage;
    private final GameState gameState;
    private final ArrayList<NPC> npcs;
    private final ArrayList<Quest> quests;
    private final HashMap<User, HashMap<User, Friendship>> userFriendships;
    private final HashMap<User, HashMap<NPC, Integer>> npcFriendships;
    private final ArrayList<Trade> trades;
    private HashMap<User, HashMap<User, HashMap<String, Boolean>>> talkHistory;

    public Game(ArrayList<User> players) {
        this.players = players;
        this.gameState = new GameState();
        this.isInNPCVillage = false;
        this.village = new NPCVillage();
        App.setCurrentGame(this);

        this.npcs = new ArrayList<>();

        for (NPCType npcType : NPCType.values()) {
            this.npcs.add(new NPC(npcType));
        }

        this.quests = new ArrayList<>();
        int questId = 1;

        Map<NPCType, List<Quest>> npcQuestMap = new LinkedHashMap<>();

        for (NPCType npcType : NPCType.values()) {
            NPC npc = getNPCByName(npcType.getName());
            if (npc == null) continue;

            List<Quest> npcQuests = new ArrayList<>();

            for (Map.Entry<Map<ItemType, Integer>, Map<ItemType, Integer>> questEntry : npcType.getQuests()) {
                Map<ItemType, Integer> requestMap = questEntry.getKey();
                Map<ItemType, Integer> rewardMap = questEntry.getValue();

                Iterator<Map.Entry<ItemType, Integer>> requestIt = requestMap.entrySet().iterator();
                Iterator<Map.Entry<ItemType, Integer>> rewardIt = rewardMap.entrySet().iterator();

                while (requestIt.hasNext() && rewardIt.hasNext()) {
                    Map.Entry<ItemType, Integer> request = requestIt.next();
                    Map.Entry<ItemType, Integer> reward = rewardIt.next();

                    Quest quest = new Quest(
                        -1,
                        npc,
                        request.getKey(),
                        request.getValue(),
                        reward.getKey(),
                        reward.getValue(),
                        this
                    );
                    npcQuests.add(quest);
                }
            }


            npcQuestMap.put(npcType, npcQuests);
        }

        boolean moreToAdd = true;
        int index = 0;

        while (moreToAdd) {
            moreToAdd = false;
            for (List<Quest> npcQuests : npcQuestMap.values()) {
                if (index < npcQuests.size()) {
                    Quest q = npcQuests.get(index);
                    Quest newQuest = new Quest(
                        questId++,
                        q.getNpc(),
                        q.getRequest(),
                        q.getRequestQuantity(),
                        q.getReward(),
                        q.getRewardQuantity(),
                        this
                    );
                    quests.add(newQuest);
                    moreToAdd = true;
                }
            }
            index++;
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

    public ArrayList<User> getPlayers() {
        return players;
    }

    public GameState getGameState() {
        return gameState;
    }

    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

    public ArrayList<Quest> getQuests() {
        return quests;
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

    public String nextTurn(User previousPlayer) {
        int previousPlayerIndex = players.indexOf(previousPlayer);
        User newPlayer = players.get((previousPlayerIndex + 1) % players.size());
        App.setLoggedIn(newPlayer);

        if (previousPlayerIndex == players.size() - 1) {
            Time.advanceOneHour(this);
        }

        StringBuilder resultMessage = new StringBuilder("New messages:\n");
        HashMap<User, HashMap<String, Boolean>> hashmap1 = this.talkHistory.get(newPlayer);
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

        for (User player : players) {
            message.append(player.getFarm().updateAnimals());
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

                npc.setTalkedToToday(player, false);
                npc.setReceivedGiftToday(player, false);
            }
        }

        for (Shop shop : NPCVillage.getShops()) {
            for (Good good : shop.getShopInventory()) {
                good.setNumberSoldToUsersToday(0);
            }
        }

        for (User player : this.players) {
            int income = 0;
            for (ShippingBin shippingBin : player.getFarm().getShippingBins()) {
                for (Item item : shippingBin.getItemsToShip()) {
                    income += item.getSellPrice();
                }
            }
            player.changeBalance(income);
            message.append(player.getUsername()).append("'s shipping bins have been emptied, and they earned ")
                .append(income).append("g.\n");


            ArrayList<Position> crows = new ArrayList<>();
            for (int i = 0; i < player.getFarm().getCropCount(); i++) {
                if ((i % 16) == 0) {
                    if ((new Random()).nextInt(4) == 0) {
                        int lastIndex = (new Random()).nextInt(player.getFarm().getPlantedCrops().size() - 1);
                        Crop randomCrop = player.getFarm().getPlantedCrops().remove(lastIndex); // Removes AND gets in one step
                        Position crowPosition = new Position(randomCrop.getPosition());
                        crows.add(crowPosition);
                    }
                }
            }
            player.getFarm().setCrows(crows);
        }

        for (Crop crop : App.getLoggedIn().getFarm().getPlantedCrops()) {
            crop.incrementDaySinceLastHarvest();
            crop.incrementDayInStage();
        }

        for (Tree tree : App.getLoggedIn().getFarm().getPlantedTrees()) {
            tree.incrementDaySinceLastHarvest();
            tree.incrementDayInStage();
            System.out.println("STAGE: " + tree.getDayInStage() + "/7 in stage" + tree.getStage() + " - day since last harvest:" + tree.getDaySinceLastHarvest());
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

    public Quest getQuestById(int id) {
        for (Quest quest : this.quests) {
            if (quest.getId() == id) {
                return quest;
            }
        }
        return null;
    }

    public static Shop getShopByName(String name) {
        for (Shop shop : NPCVillage.getShops()) {
            if (shop.getName().equalsIgnoreCase(name)) {
                return shop;
            }
        }
        return null;
    }

    public Shop getShopByShopType(ShopType shopType) {
        for (Shop shop : NPCVillage.getShops()) {
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
            System.out.println(e.getMessage());
        }
    }

    public void sendMessage(User sender, User receiver, String message) {
        if (talkHistory.containsKey(sender) && talkHistory.get(sender).containsKey(receiver)) {
            talkHistory.get(sender).get(receiver).put(message, false);
        }
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
