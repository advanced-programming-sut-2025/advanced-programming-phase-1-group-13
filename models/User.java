package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import models.enums.SecurityQuestion;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.environment.Direction;
import models.enums.environment.Time;
import models.enums.types.*;
import models.inventory.Backpack;
import models.tools.FishingRod;
import models.tools.Tool;
import models.tools.TrashCan;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Gender gender;
    private Game activeGame;
    private int energy;
    private boolean isEnergyUnlimited;
    private Position position;
    private Tool currentTool;
    private String hashedPassword;
    private HashMap<Skill, SkillLevel> skillLevels;
    private ArrayList<CraftRecipe> learntCraftRecipes;
    private ArrayList<FoodType> learntCookingRecipes;
    private HashMap<SecurityQuestion, String> qAndA;
    private Farm farm;
    private ArrayList<Transaction> transactions;
    private Backpack backpack;
    private ArrayList<Gift> gifts;
    private double balance;
    private int mostEarnedMoney;
    private TrashCan trashCan;
    private HashMap<User, Boolean> hasTalkedToToday;
    private HashMap<User, Boolean> exchangedGiftToday;
    private HashMap<User, Boolean> hasHuggedToday;
    private HashMap<User, Boolean> exchangedFlowerToday;
    private ArrayList<User> marriageRequests;
    private User spouse;
    private boolean isDepressed;
    private Time rejectionTime;

    public User(String username, String password, String hashPass, String nickname, String email, Gender gender) {
        this.username = username;
        this.password = password;
        this.hashedPassword = hashPass;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.activeGame = null;
        this.energy = 200; // TODO: change value if needed
        this.farm = new Farm(0); // TODO
        this.isEnergyUnlimited = false;
        this.balance = 0;
        this.transactions = new ArrayList<>();
        this.learntCraftRecipes = new ArrayList<>();
        this.learntCookingRecipes = new ArrayList<>();
        this.qAndA = new HashMap<>();
        this.backpack = new Backpack(BackpackType.INITIAL);
        this.skillLevels = new HashMap<>();
        this.skillLevels.put(Skill.FARMING, SkillLevel.LEVEL_ZERO);
        this.skillLevels.put(Skill.FISHING, SkillLevel.LEVEL_ZERO);
        this.skillLevels.put(Skill.MINING, SkillLevel.LEVEL_ZERO);
        this.skillLevels.put(Skill.FORAGING, SkillLevel.LEVEL_ZERO);
        this.trashCan = new TrashCan(ToolMaterial.BASIC);
        this.marriageRequests = new ArrayList<>();
        this.spouse = null;
        this.isDepressed = false;
        this.gifts = new ArrayList<>();
        this.hasTalkedToToday = new HashMap<>();
        this.exchangedGiftToday = new HashMap<>();
        this.hasHuggedToday = new HashMap<>();
        this.exchangedFlowerToday = new HashMap<>();
    }

    public TrashCan getTrashCan() {
        return this.trashCan;
    }

    public void setTrashCan(TrashCan trashCan) {
        this.trashCan = trashCan;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    public Tool getCurrentTool() {
        return this.currentTool;
    }

    public Position getPosition() {
        return this.position;
    }

    public Farm getFarm() {
        return farm;
    }

    public Backpack getBackpack() {
        return this.backpack;
    }

    //
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        saveUsersToJson();
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnergy() {
        return this.energy;
    }

    public double getBalance() {
        return balance;
    }

    public Game getActiveGame() {
        return activeGame;
    }

    public void setActiveGame(Game activeGame) {
        this.activeGame = activeGame;
    }

    public HashMap<SecurityQuestion, String> getqAndA() {
        return qAndA;
    }

    public int getMostEarnedMoney() {
        return mostEarnedMoney;
    }

    public HashMap<Skill, SkillLevel> getSkillLevels() {
        return skillLevels;
    }

    public ArrayList<CraftRecipe> getLearntCraftRecipes() {
        return learntCraftRecipes;
    }

    public ArrayList<FoodType> getLearntCookingRecipes() {
        return learntCookingRecipes;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void changeBalance(double amount) {
        this.balance += amount;
        saveUsersToJson();
    }

    public void setEnergy(int energyAmount) {
        if (this.isEnergyUnlimited) {
            this.energy = Integer.MAX_VALUE;
        } else {
            this.energy = energyAmount;
        }
        saveUsersToJson();
    }

    private void saveUsersToJson() {
        try (FileWriter writer = new FileWriter("users.json")) {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(App.getUsers()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void decreaseEnergyBy(int amount) {
        this.energy -= amount;
    }

    public boolean isEnergyUnlimited() {
        return this.isEnergyUnlimited;
    }

    public void setEnergyUnlimited(boolean unlimitedEnergy) {
        this.isEnergyUnlimited = unlimitedEnergy;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public HashMap<User, Boolean> getHasTalkedToToday() {
        return hasTalkedToToday;
    }

    public boolean hasTalkedToToday(User user) {
        return this.getHasTalkedToToday().get(user);
    }

    public void setTalkedToToday(User user, boolean hasReceived) {
        this.getHasTalkedToToday().put(user, hasReceived);
    }

    public HashMap<User, Boolean> getExchangedGiftToday() {
        return exchangedGiftToday;
    }

    public boolean exchangedGiftToday(User user) {
        return this.getExchangedGiftToday().get(user);
    }

    public void setExchangedGiftToday(User user, boolean hasReceived) {
        this.getExchangedGiftToday().put(user, hasReceived);
    }

    public HashMap<User, Boolean> getHasHuggedToday() {
        return hasHuggedToday;
    }

    public boolean hasHuggedToday(User user) {
        return this.getHasHuggedToday().get(user);
    }

    public void setHasHuggedToday(User user, boolean hasReceived) {
        this.getHasHuggedToday().put(user, hasReceived);
    }

    public HashMap<User, Boolean> getExchangedFlowerToday() {
        return exchangedFlowerToday;
    }

    public boolean hasExchangedFlowerToday(User user) {
        return this.getExchangedFlowerToday().get(user);
    }

    public void setExchangedFlowerToday(User user, boolean hasReceived) {
        this.getExchangedFlowerToday().put(user, hasReceived);
    }

    public boolean hasInteractedToday(User user) {
        return this.hasExchangedFlowerToday(user) ||
                this.hasHuggedToday(user) ||
                this.exchangedGiftToday(user) ||
                this.hasTalkedToToday(user);
    }

    public User getSpouse() {
        return spouse;
    }

    public void setSpouse(User spouse) {
        this.spouse = spouse;
    }

    public ArrayList<User> getMarriageRequests() {
        return marriageRequests;
    }

    public void addMarriageRequests(User user) {
        this.marriageRequests.add(user);
    }

    public boolean isDepressed() {
        return isDepressed;
    }

    public void setDepressed(boolean depressed) {
        isDepressed = depressed;
    }

    public ArrayList<Gift> getGifts() {
        return gifts;
    }

    public void addGift(Item item, int amount, User giver) {
        Gift gift = new Gift(this.gifts.size() + 1, item, amount, giver);
        this.gifts.add(gift);
    }

    public Time getRejectionTime() {
        return rejectionTime;
    }

    public void faint() {
        // TODO: well, faint!
    }

    public void useTool(Direction direction) {
        // TODO
    }

    public void changePosition(Position newPosition) {
        this.position = newPosition;
    }

    public String getStringLearntCookingRecipes() {
        // TODO: Use StringBuilder for it
        return null;
    }

    public String getStringLearntCraftRecipes() {
        // TODO: Use StringBuilder for it
        return null;
    }

    public void LearnNewCraftRecipe(FoodType craftRecipe) {
        // TODO
    }

    public void LearnNewCookingRecipe(CookingRecipe cookingRecipe) {
        // TODO
    }

    public void eat(String foodName) {
        // TODO
    }

    public Map<SecurityQuestion, String> getQAndA() {
        return qAndA;
    }

    public void addQAndA(SecurityQuestion securityQuestion, String answer) {
        this.qAndA.put(securityQuestion, answer);
        saveUsersToJson();
    }

    public Gift getGiftById(int id) {
        for (Gift gift : this.getGifts()) {
            if (gift.getId() == id) {
                return gift;
            }
        }
        return null;
    }

    public FishingRod getFishingRodByName(String name) {
        ArrayList<Item> items = new ArrayList<>(this.getBackpack().getItems().keySet());
        for (Item item : items) {
            if (item instanceof FishingRod fishingRod) {
                if (fishingRod.getRodType().getName().equals(name)) {
                    return fishingRod;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Username: " + this.username + "\n" +
                "Nickname: " + this.nickname + "\n" +
                "Most earned money in a game: " + this.mostEarnedMoney + "\n";
    }

    public boolean hasInInventory(ItemType itemType) {
        // TODO
        return false;
    }
}
