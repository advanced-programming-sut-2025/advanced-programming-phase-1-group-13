package models;

import models.enums.SecurityQuestion;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.environment.Direction;
import models.enums.types.BackpackType;
import models.enums.types.FoodType;
import models.inventory.Backpack;
import models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private int energy;
    private boolean isEnergyUnlimited;
    private Position position;
    private Tool currentTool;
    private HashMap<Skill, SkillLevel> SkillLevels;
    private ArrayList<Farm> farms;
    private ArrayList<CraftRecipe> learntCraftRecipes;
    private ArrayList<FoodType> learntCookingRecipes;
    private Map<SecurityQuestion, String> qAndA;
    private Farm farm;
    private ArrayList<Transaction> transactions;
    private Backpack backpack;
    private double balance;

    public Farm getFarm() {
        return farm;
    }

    public Backpack getBackpack() {
        return backpack;
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
