package models;

import models.enums.SecurityQuestion;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.environment.Direction;
import models.enums.types.BackpackType;
import models.enums.types.FoodType;
import models.enums.types.Gender;
import models.enums.types.ItemType;
import models.inventory.Backpack;
import models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Gender gender;
    private ArrayList<Game> games;
    private Game activeGame;
    private int energy;
    private boolean isEnergyUnlimited;
    private Position position;
    private Tool currentTool;
    private HashMap<Skill, SkillLevel> skillLevels;
    private ArrayList<CraftRecipe> learntCraftRecipes;
    private ArrayList<FoodType> learntCookingRecipes;
    private HashMap<SecurityQuestion, String> qAndA;
    private Farm farm;
    private ArrayList<Transaction> transactions;
    private Backpack backpack;
    private double balance;
    private int mostEarnedMoney;

    public User(String username, String password, String nickname, String email, Gender gender) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.games = new ArrayList<>();
        this.activeGame = null;
        this.energy = 200; // TODO: change value if needed
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public ArrayList<Game> getGames() {
        return games;
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

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void changeBalance(double amount) {
        this.balance += amount;
    }

    public void setEnergy(int energyAmount) {
        if (this.isEnergyUnlimited) {
            this.energy = Integer.MAX_VALUE;
        } else {
            this.energy = energyAmount;
        }
    }

    public boolean isEnergyUnlimited() {
        return this.isEnergyUnlimited;
    }

    public void setEnergyUnlimited(boolean unlimitedEnergy) {
        this.isEnergyUnlimited = unlimitedEnergy;
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
    }

    @Override
    public String toString() {
        return "Username: " + this.username + "\n" +
                "Nickname: " + this.nickname + "\n" +
                "Most earned money in a game: " + this.mostEarnedMoney + "\n" +
                "Number of games: " + this.games.size();
    }

    public boolean hasInInventory(ItemType itemType) {
        // TODO
        return false;
    }
}
