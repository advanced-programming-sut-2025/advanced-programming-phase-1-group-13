package com.ap_project.common.models;

import com.ap_project.client.controllers.*;
import com.ap_project.common.models.enums.SecurityQuestion;
import com.ap_project.common.models.enums.Skill;
import com.ap_project.common.models.enums.SkillLevel;
import com.ap_project.common.models.enums.environment.Direction;
import com.ap_project.common.models.enums.environment.Time;
import com.ap_project.common.models.enums.types.*;
import com.ap_project.common.models.inventory.Backpack;
import com.ap_project.common.models.tools.*;
import com.ap_project.common.models.trade.Trade;
import com.ap_project.common.models.trade.TradeWithItem;
import com.ap_project.common.models.trade.TradeWithMoney;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static com.ap_project.client.controllers.login.LoginController.hashSha256;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private final Gender gender;
    private int avatarNumber;
    private int woodCount;
    private int stoneCount;
    private transient Game activeGame;
    private int energy;
    private int maxEnergy;
    private boolean isEnergyUnlimited;
    private FoodBuff currentFoodBuff;
    private Skill buffRelatedSkill;
    private Integer hoursLeftTillBuffVanishes;
    private Position position;
    private Direction direction;
    private Tool currentTool;
    private HashMap<Skill, SkillLevel> skillLevels;
    private HashMap<Skill, Integer> skillPoints;
    private ArrayList<CraftRecipe> learntCraftRecipes;
    private ArrayList<CookingRecipe> learntCookingRecipes;
    private final HashMap<SecurityQuestion, String> qAndA;
    private Farm farm;
    private Backpack backpack;
    private ArrayList<Gift> gifts;
    private double balance;
    private double spentMoney;
    private int mostEarnedMoney;
    private int numberOfGames;
    private TrashCan trashCan;
    private HashMap<User, Boolean> hasTalkedToToday;
    private HashMap<User, Boolean> exchangedGiftToday;
    private HashMap<User, Boolean> hasHuggedToday;
    private HashMap<User, Boolean> exchangedFlowerToday;
    private ArrayList<User> marriageRequests;
    private User spouse;
    private boolean isDepressed;
    private Time rejectionTime;

    public User(String username, String password, String nickname, String email, Gender gender) {
        this.username = username;
        this.password = hashSha256(password);
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.avatarNumber = (new Random()).nextInt(34) + 1;
        this.qAndA = new HashMap<>();
        this.numberOfGames = 0;
        this.mostEarnedMoney = 0;
        this.activeGame = null;
        this.resetPlayer();
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
        this.position = new Position(
            farm.getCabin().getPosition().getX() + 4,
            farm.getCabin().getPosition().getY() - 1
        );
    }

    public void resetPlayer() {
        this.woodCount = 0;
        this.stoneCount = 0;
        this.energy = 200;
        this.maxEnergy = 200;
        this.backpack = new Backpack(BackpackType.INITIAL);
        this.direction = Direction.DOWN;
        this.isEnergyUnlimited = false;
        this.balance = 0;
        this.spentMoney = 0;
        this.learntCraftRecipes = new ArrayList<>();
        this.learntCookingRecipes = new ArrayList<>();
        // TODO: Remove later
        learnNewCraftRecipe(new CraftRecipe(CraftType.BEE_HOUSE));
        learnNewCraftRecipe(new CraftRecipe(CraftType.CHEESE_PRESS));
        learnNewCraftRecipe(new CraftRecipe(CraftType.MEGA_BOMB));
        learnNewCookingRecipe(new CookingRecipe(FoodType.SALAD));
        learnNewCookingRecipe(new CookingRecipe(FoodType.SPAGHETTI));
        learnNewCookingRecipe(new CookingRecipe(FoodType.HASHBROWNS));
        this.backpack = new Backpack(BackpackType.DELUXE); // TODO: change to INITIAL later
        this.backpack.addToInventory(new Axe(ToolMaterial.BASIC), 1);
        this.backpack.addToInventory(new Hoe(ToolMaterial.BASIC), 1);
        this.backpack.addToInventory(new WateringCan(ToolMaterial.BASIC), 1);
        this.backpack.addToInventory(new Pickaxe(ToolMaterial.BASIC), 1);
        this.backpack.addToInventory(new Scythe(ToolMaterial.BASIC), 1);
        this.skillLevels = new HashMap<>();
        this.skillLevels.put(Skill.FARMING, SkillLevel.LEVEL_ONE);
        this.skillLevels.put(Skill.FISHING, SkillLevel.LEVEL_ONE);
        this.skillLevels.put(Skill.MINING, SkillLevel.LEVEL_ONE);
        this.skillLevels.put(Skill.FORAGING, SkillLevel.LEVEL_ONE);
        this.skillPoints = new HashMap<>();
        this.skillPoints.put(Skill.FARMING, 0);
        this.skillPoints.put(Skill.FISHING, 0);
        this.skillPoints.put(Skill.MINING, 0);
        this.skillPoints.put(Skill.FORAGING, 0);
        this.trashCan = new TrashCan(ToolMaterial.BASIC);
        this.marriageRequests = new ArrayList<>();
        this.spouse = null;
        this.isDepressed = false;
        this.gifts = new ArrayList<>();
        this.hasTalkedToToday = new HashMap<>();
        this.exchangedGiftToday = new HashMap<>();
        this.hasHuggedToday = new HashMap<>();
        this.exchangedFlowerToday = new HashMap<>();
        this.currentFoodBuff = null;
        this.buffRelatedSkill = null;
        this.hoursLeftTillBuffVanishes = null;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void updateSkillPoints(Skill skill, int points) {
        int previousPoints = skillPoints.get(skill);
        SkillLevel previousLevel = skillLevels.get(skill);
        int newPoints;
        SkillLevel newLevel = skillLevels.get(skill);
        if (previousPoints + points > previousLevel.calculateMaxPoints()) {
            if (previousLevel.equals(SkillLevel.LEVEL_FOUR)) {
                newPoints = SkillLevel.LEVEL_FOUR.calculateMaxPoints();
            } else {
                newPoints = previousPoints + points - previousLevel.calculateMaxPoints();
            }

            if (previousLevel.equals(SkillLevel.LEVEL_FOUR)) {
                newLevel = SkillLevel.LEVEL_FOUR;
            } else {
                newLevel = SkillLevel.getSkillLevelByNumber(previousLevel.getNumber() + 1);
            }
        } else {
            newPoints = previousPoints + points;
        }
        skillPoints.put(skill, newPoints);
        skillLevels.put(skill, newLevel);
    }

    public Skill getBuffRelatedSkill() {
        return this.buffRelatedSkill;
    }

    public void updateBuffRelatedSkill() {
        switch (this.currentFoodBuff) {
            case FARMING_5_HOURS:
                this.buffRelatedSkill = Skill.FARMING;
                break;
            case FISHING_5_HOURS:
            case FISHING_10_HOURS:
                this.buffRelatedSkill = Skill.FISHING;
                break;
            case MINING_5_HOURS:
                this.buffRelatedSkill = Skill.MINING;
                break;
            case FORAGING_5_HOURS:
            case FORAGING_11_HOURS:
                this.buffRelatedSkill = Skill.FORAGING;
                break;
            case MAX_ENERGY_PLUS_50:
            case MAX_ENERGY_PLUS_100:
            default:
                this.buffRelatedSkill = null;
                break;
        }
    }

    public FoodBuff getCurrentFoodBuff() {
        return this.currentFoodBuff;
    }

    public void activateFoodBuff(FoodBuff foodBuff) {
        this.currentFoodBuff = foodBuff;
        this.hoursLeftTillBuffVanishes = foodBuff.getBuffDurationInHours();

        switch (foodBuff) {
            case MAX_ENERGY_PLUS_50:
                this.energy = 250;
                this.maxEnergy = 250;
                break;
            case MAX_ENERGY_PLUS_100:
                this.energy = 300;
                this.maxEnergy = 300;
                break;
        }

        this.updateBuffRelatedSkill();
    }

    public Integer getHoursLeftTillBuffVanishes() {
        return this.hoursLeftTillBuffVanishes;
    }

    public void decreaseHoursLeftTillBuffVanishes(Integer decreaseBy) {
        if (this.hoursLeftTillBuffVanishes != null) {
            this.hoursLeftTillBuffVanishes -= decreaseBy;
            if (this.hoursLeftTillBuffVanishes <= 0) {
                this.hoursLeftTillBuffVanishes = 0;
            }
        }
    }

    public void setMostEarnedMoney(int mostEarnedMoney) {
        this.mostEarnedMoney = mostEarnedMoney;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setRejectionTime(Time rejectionTime) {
        this.rejectionTime = rejectionTime;
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

    public Direction getDirection() {
        return direction;
    }

    public Backpack getBackpack() {
        return this.backpack;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        // saveUsersToJson();
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

    public int getAvatarNumber() {
        return avatarNumber;
    }

    public void setAvatarNumber(int avatarNumber) {
        this.avatarNumber = avatarNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = hashSha256(password);
    }

    public int getEnergy() {
        return this.energy;
    }

    public double getBalance() {
        return balance;
    }

    public double getSpentMoney() {
        return spentMoney;
    }

    public Game getActiveGame() {
        return activeGame;
    }

    public void setActiveGame(Game activeGame) {
        if (activeGame == null) {
            numberOfGames++;
            if (balance > mostEarnedMoney) {
                mostEarnedMoney = (int) balance;
            }
        }
        this.activeGame = activeGame;
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

    public ArrayList<CookingRecipe> getLearntCookingRecipes() {
        return learntCookingRecipes;
    }

    public void changeBalance(double amount) {
        this.balance += amount;
        spentMoney += amount;
        // saveUsersToJson();
    }

    public void setEnergy(int energyAmount) {
        if (this.isEnergyUnlimited) {
            this.energy = Integer.MAX_VALUE;
        } else {
            this.energy = energyAmount;
        }
        // saveUsersToJson();
    }

    public void decreaseEnergyBy(int amount) {
        if (!this.isEnergyUnlimited) this.energy -= amount;
        if (this.energy < 0) this.energy = 0;
    }

    public void increaseEnergyBy(int amount) {
        if (!this.isEnergyUnlimited) this.energy += amount;
        else this.energy = Integer.MAX_VALUE;
    }

    public boolean isEnergyUnlimited() {
        return this.isEnergyUnlimited;
    }

    public void setEnergyUnlimited(boolean unlimitedEnergy) {
        this.isEnergyUnlimited = unlimitedEnergy;
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
        if (this.getExchangedFlowerToday() != null) return this.getExchangedFlowerToday().get(user);
        return false;
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
        this.energy = (int) (0.75 * this.maxEnergy);
        System.out.println("You fainted! Your energy falls to 75% of max-energy, when you wake up in the next turn.");
        System.out.println(GameController.nextTurn());
    }

    public void changePosition(Position newPosition) {
        this.position = newPosition;
    }

    public void changePosition(int amount, Direction direction) {
        this.position = new Position(this.position.getX() + direction.getDeltaX() * amount, this.position.getY() + direction.getDeltaY() * amount);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getStringLearntCookingRecipes() {
        StringBuilder sb = new StringBuilder();
        if (this.learntCookingRecipes.isEmpty()) {
            return null;
        }
        sb.append("\nLearnt cooking recipes:\n\n");
        for (CookingRecipe recipe : learntCookingRecipes) {
            sb.append(recipe.toString()).append("\n");
        }
        return sb.toString();
    }

    public String getStringLearntCraftRecipes() {
        StringBuilder sb = new StringBuilder();
        if (this.learntCraftRecipes.isEmpty()) {
            return null;
        }
        sb.append("\nLearnt craft recipes:\n\n");
        for (CraftRecipe recipe : learntCraftRecipes) {
            sb.append(recipe.toString()).append("\n");
        }
        return sb.toString();
    }

    public void learnNewCraftRecipe(CraftRecipe craftRecipe) {
        this.learntCraftRecipes.add(craftRecipe);
    }

    public void learnNewCookingRecipe(CookingRecipe cookingRecipe) {
        this.learntCookingRecipes.add(cookingRecipe);
    }

    public boolean hasLearntCookingRecipe(FoodType foodType) {
        for (CookingRecipe cookingRecipe : learntCookingRecipes) {
            if (cookingRecipe.getFoodType() == foodType) {
                return true;
            }
        }
        return false;
    }

    public boolean hasLearntCraftRecipe(CraftType craftType) {
        for (CraftRecipe craftRecipe : learntCraftRecipes) {
            if (craftRecipe.getCraftType() == craftType) {
                return true;
            }
        }
        return false;
    }

    public Map<SecurityQuestion, String> getQAndA() {
        return qAndA;
    }

    public void addQAndA(SecurityQuestion securityQuestion, String answer) {
        this.qAndA.put(securityQuestion, answer);
        // saveUsersToJson();
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
            if (item instanceof FishingRod) {
                FishingRod fishingRod = (FishingRod) item;
                if (fishingRod.getRodType().getName().equals(name)) {
                    return fishingRod;
                }
            }
        }
        return null;
    }

    public boolean isCloseToTileType(TileType tileType) {
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        Position position;
        for (int dx = -1; dx < 1; dx++) {
            for (int dy = -1; dy < 1; dy++) {
                position = new Position(x + dx, y + dy);
                if (!(dx == 0 && dy == 0) && Objects.requireNonNull(this.activeGame.getPlayerByUsername(App.getLoggedIn().getUsername()).getFarm().getTileByPosition(position)).getType().equals(tileType)) {
                    return true;
                }
            }
        }
        return false;
    }

    public ShippingBin getCloseShippingBin() {
        for (ShippingBin shippingBin : this.farm.getShippingBins()) {
            if (Position.areClose(shippingBin.getPosition(), this.getPosition())) {
                return shippingBin;
            }
        }
        return null;
    }

    public Result tradeWithMoney(User targetUser, boolean isOffer, Item item, int amount, int price) {
        Trade trade;
        if (isOffer) {
            trade = new TradeWithMoney(this.activeGame, this, this, targetUser, item, amount, price);
        } else {
            trade = new TradeWithMoney(this.activeGame, this, targetUser, this, item, amount, price);
        }
        App.getCurrentGame().addTrade(trade);
        return new Result(true, "Trade offer created: " + (isOffer ? "Offering" : "Requesting") + " " +
            item.getName() + "(x" + amount + ") for " + price + "g with " + targetUser.getUsername() + ". " +
            "Wait for their respond.");
    }

    public Result tradeWithItem(User targetUser, boolean isOffer, Item item, int amount,
                                Item targetItem, int targetAmount) {
        Trade trade;
        if (isOffer) {
            trade = new TradeWithItem(this.activeGame, this, this, targetUser, item, amount,
                targetItem, targetAmount);
        } else {
            trade = new TradeWithItem(this.activeGame, this, targetUser, this, item, amount,
                targetItem, targetAmount);
        }
        App.getCurrentGame().addTrade(trade);
        return new Result(true, "Trade offer created: " + (isOffer ? "Offering" : "Requesting") + " " +
            item.getName() + "(x" + amount + ") for " + targetItem.getName() + "(x" + targetAmount + ") with " +
            targetUser.getUsername() + ". Wait for their respond.");
    }

    @Override
    public String toString() {
        return "Username: " + this.username + "\n" +
            "Nickname: " + this.nickname + "\n" +
            "Most earned money in a game: " + this.mostEarnedMoney + "\n" +
            "Number of games: " + this.numberOfGames;
    }

    public int getWoodCount() {
        return this.woodCount;
    }

    public int getStoneCount() {
        return this.stoneCount;
    }

    private void saveUsersToJson() {
        try (FileWriter writer = new FileWriter("users.json")) {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(App.getUsers()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
