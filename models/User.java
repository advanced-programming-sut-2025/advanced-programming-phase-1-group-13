package models;

import models.enums.SecurityQuestion;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.environment.Direction;
import models.enums.types.BackpackType;
import models.enums.types.FoodType;
import models.enums.types.Gender;
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
    private int energy;
    private boolean isEnergyUnlimited;
    private Position position;
    private Tool currentTool;
    private HashMap<Skill, SkillLevel> SkillLevels;
    private ArrayList<CraftRecipe> learntCraftRecipes;
    private ArrayList<FoodType> learntCookingRecipes;
    private Map<SecurityQuestion, String> qAndA;
    private Farm farm;
    private ArrayList<Transaction> transactions;
    private Backpack backpack;
    private double balance;

    public User(String username, String password, String nickname, String email, Gender gender) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.energy = 200; // TODO: change value if needed
        this.isEnergyUnlimited = false;
        this.balance = 0;
        this.transactions = new ArrayList<>();
        this.learntCraftRecipes = new ArrayList<>();
        this.learntCookingRecipes = new ArrayList<>();
        this.qAndA = new HashMap<>(); // todo
        this.backpack = new Backpack(BackpackType.INITIAL);
        this.SkillLevels = new HashMap<>();
        this.SkillLevels.put(Skill.FARMING, SkillLevel.LEVEL_ZERO);
        this.SkillLevels.put(Skill.FISHING, SkillLevel.LEVEL_ZERO);
        this.SkillLevels.put(Skill.MINING, SkillLevel.LEVEL_ZERO);
        this.SkillLevels.put(Skill.FORAGING, SkillLevel.LEVEL_ZERO);
    }

    public Position getPosition() {
        return this.position;
    }

    public Farm getFarm() {
        return farm;
    }

    public Backpack getBackpack() {
        return backpack;
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

    public Tool getCurrentTool() {
        return this.currentTool;
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

    public void setQAndA(Map<SecurityQuestion, String> qAndA) {
        this.qAndA = qAndA;
    }
}
