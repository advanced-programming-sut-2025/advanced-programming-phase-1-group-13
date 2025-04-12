package models;

import models.enums.SecurityQuestion;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.environment.Direction;
import models.enums.types.Food;
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
    private ArrayList<Food> learntCookingRecipes;
    private Map<SecurityQuestion, String> qAndA;
    private Farm farm;
    private ArrayList<Transaction> transactions;

    public int getEnergy() {
        return this.energy;
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

    public void LearnNewCraftRecipe(Food craftRecipe) {
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
