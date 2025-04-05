package controllers.player;

import models.*;
import models.Food;
import models.Tool;
import models.enums.environment.*;

public class GameController {
    User player = App.getLoggedIn();


    // === PLAYER'S STATUS === //

    public Result showPlayerEnergy() {
        int playerEnergy = player.getEnergy();
        return new Result(true, "Your energy is: " + playerEnergy);
    }

    public Result setPlayerEnergy(int energyAmount) {
        player.setEnergy(energyAmount);
        return new Result(true, "Energy set to " + energyAmount);
    }

    public Result setUnlimitedEnergy() {
        player.setEnergyUnlimited(true);
        return new Result(true, "Unlimited Energy activated!");
    }

    public Result faint() {
        player.faint();
        return new Result(true, ""); // todo: appropriate message
    }

    public Result showCurrentTool() {
        Tool playerCurrentTool = player.getCurrentTool();
        return new Result(true, "Your tool is: " + playerCurrentTool.toString()); // todo: is message OK?
    }

    public Result showLearntCookingRecipes() {
        String learntRecipes = player.getStringLearntCookingRecipes();
        return new Result(true, learntRecipes);
    }

    public Result showLearntCraftRecipes() {
        String learntRecipes = player.getStringLearntCraftRecipes();
        return new Result(true, learntRecipes);
    }

    // === INVENTORY === //
    public Result inventoryShow() {
        // TODO
    }

    // === TOOLS, FOODS, ITEMS, AND CRAFTS === //

    public Result useTool(Position position, Tool tool) {
        if (canToolBeUsedHere(position, tool)) {
            // TODO: use tool
            return new Result(true, ""); // todo: appropriate message
        }
        return new Result(false, "You can't use that tool in that direction"); // todo: appropriate message
    }

    public Result placeItem(Item item, Direction direction) {
        Position position = neighborTile(direction);
        if (canItemBePlacedHere(position, item)) {
            // TODO: place item
            return new Result(true, item + " placed at " + position.toString());
        }
        return new Result(false, "you can't place that item at " + position.toString());

    }

    public Result craft(Item item) {
        if (!canCraft(item)) {
            return new Result(false, "Not possible to craft that item!");
        }
        // TODO: craft item and add it to inventory.
        return new Result(true, "Item crafted and added to inventory.");
    }

    public Result cheatAddItem(Item item) {
        // TODO: add item to inventory
        return new Result(true, "Item added to inventory.");
    }

    // or name it cook() ?
    public Result prepareCook(Food food) {
        if (!canCook(food)) {
            return new Result(false, "You cannot cook this right now.");
            // todo: or specify the cause of the error...
        }
        // TODO: cook and add to inventory
        return new Result(true, "Yummy! Your meal is ready.");
    }

    public Result eat(Food food) {
        // TODO: check if player HAS the food, and return appropriate Result if not.
        // TODO: increase energy
        // TODO: apply buff
        player.eat(food.getName());
        return new Result(true, ""); // todo: return appropriate Result (list the buff, etc. ?)
    }

    private boolean canCraft(Item item) {
        // TODO: check if inventory is full; if so, return false.
        // TODO: check if we know the recipe, return false if not.
        // TODO: check if we have the ingredients, return false if not.
    }

    private boolean canCook(Food food) {
        // TODO: check if inventory is full; if so, return false.
        // TODO: check if we know the recipe, return false if not.
        // TODO: check if we have the ingredients, return false if not.
    }

    private boolean canToolBeUsedHere(Position position, Tool tool) {
        // TODO: check the tile at "position" and check if tool can be used!
    }

    private boolean canItemBePlacedHere(Position position, Item item) {
        // TODO: check the tile at "position" and check if item can be placed there!
    }

    private Position neighborTile(Direction direction) {
        // TODO: return the position of the neighbour tile, if within the range of our map of farms.
    }

    // === WALK === //
    public Result walk(Path path, boolean playerConfirmed) {
        if (!playerConfirmed) {
            return new Result(false, "You denied the walk.");
        }
        // TODO: Walk path! i.e. call player's inner changePosition(x,y) method.
        Position destination = path.getPathTiles().getLast();
        player.changePosition(destination);
        return new Result(true, "Walking...");
    }

    public Result respondForWalkRequest(Position origin, Position destination) {
        Path path = findValidPath(origin, destination);
        if (path == null) {
            return new Result(false, "No valid path found!");
        }
        return new Result(true, "Do you confirm the walk?");
        // [we can also show the path and then ask for confirmation]

        /*
        In View: after calling this method, we expect the player to confirm/deny
        Then, we call the walk() method.
        */
    }

    private Path findValidPath(Position origin, Position destination) {
        // give FarmsMap as argument?
        if (!isDestinationAllowed(destination)) {
            return null;
        }
        // TODO: build a valid path and return it
        return new Path();
    }

    private boolean isDestinationAllowed(Position destination) {
        // TODO: check if destination is in OUR Farm.
    }

    // === PRINT MAP === //

    public Result printMap() {
        return new Result(true, ""); // TODO: print map.
    }

    public Result printColoredMap() {
        return new Result(true, ""); // TODO: print a colored map.
    }

    public Result showHelpReadingMap() {
        return new Result(true, ""); // TODO: show the "Help" / enter the Help menu / ...
    }


    // === GAME STATUS === //

    public Result cheatAdvanceTime(int howManyHours) {
        // TODO;
    }

    public Result cheatAdvanceDate(int howManyDays) {
        // TODO;
    }

    public Result cheatThor(Position position) {
        // TODO
    }

    public Result showWeather() {
        // TODO
    }

    public Result showWeatherForecast() {
        // TODO
    }

    public Result cheatWeatherSet(Weather newWeather) {
        // TODO
    }

    public Result buildGreenhouse() {
        if (!canBuildGreenhouse()) {
            return new Result(false, "You can't build greenhouse!");
        }
        // TODO: build a greenhouse
        return new Result(true, "Building greenhouse..."); // todo: show its info in detail?
    }

    private boolean canBuildGreenhouse() {
        // TODO: check if we have the required material
        // + is only ONE greenhouse allowed?
    }


}
