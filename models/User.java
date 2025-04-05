package models;

import models.enums.SecurityQuestion;

import javax.tools.Tool;
import java.util.ArrayList;
import java.util.Map;

public class User {
    private int energy;
    private boolean isEnergyUnlimited;
    private Position position;
    private Tool currentTool;
    private HashMap<Skills, SkillLevels> SkillLevels;
    private ArrayList<Farm> farms;
    private ArrayList<CraftRecipe> learntCraftRecipes;
    private ArrayList<CookingRecipe> learntCookingRecipes;
    private Map<SecurityQuestion, String> qAndA;

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

    }

    public void changePosition(Position newPosition) {
        this.position = newPosition;
    }

    public void placeItem(Object item, Direction direction) {

    }

    public void helpReadingMap() {

    }

    public void printMap(Position position, int size) {

    }

    public void printColoredMap(Position position, int size) {

    }

    public String getStringLearntCookingRecipes() {
        // TODO: Use StringBuilder for it
    }

    public String getStringLearntCraftRecipes() {
        // TODO: Use StringBuilder for it
    }

    public void LearnNewCraftRecipe(CraftRecipe craftRecipe) {

    }

    public void LearnNewCookingRecipe(CookingRecipe cookingRecipe) {

    }

    public void craft(Craft craft) {

    }

    public void prepareCooking() {

    }

    public void eat(String foodName) {

    }

    public Map<SecurityQuestion, String> getQAndA() {
        return qAndA;
    }

    public void setQAndA(Map<SecurityQuestion, String> qAndA) {
        this.qAndA = qAndA;
    }
}
