package com.ap_project.client.controllers;

import com.ap_project.Main;
import com.ap_project.client.views.game.FarmhouseView;
import com.ap_project.common.models.*;
import com.ap_project.common.models.enums.environment.*;
import com.ap_project.common.models.enums.*;
import com.ap_project.common.models.enums.types.*;
import com.ap_project.common.models.farming.*;
import com.ap_project.common.models.inventory.*;
import com.ap_project.common.models.tools.*;
import com.ap_project.common.models.trade.*;
import com.ap_project.client.views.game.GameView;

import java.util.*;

import static com.ap_project.common.models.Greenhouse.canBuildGreenhouse;
import static com.ap_project.common.models.Position.areClose;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class GameController {
    private GameView view;

    public void setView(GameView view) {
        this.view = view;
    }

    // === PLAYER'S STATUS === //

    public static Result nextTurn() {
        Game game = App.getCurrentGame();
        String message = game.nextTurn(App.getLoggedIn());
        return new Result(true, "It is now " + App.getLoggedIn().getNickname() + "'s turn.\n" + message);
    }

    public Result showPlayerEnergy() {
        User player = App.getLoggedIn();
        int playerEnergy = player.getEnergy();
        if (player.isEnergyUnlimited()) {
            return new Result(true, "Your energy is: " + playerEnergy + "\n(unlimited energy is activated)");
        }
        return new Result(true, "Your energy is: " + playerEnergy);
    }


    public Result showPlayerPosition() {
        User player = App.getLoggedIn();
        Position position = player.getPosition();
        return new Result(true, "Your current position is: " + position);
    }

    public Result setPlayerEnergy(String energyAmountStr) {
        int energyAmount = Integer.parseInt(energyAmountStr);

        User player = App.getLoggedIn();
        player.setEnergy(energyAmount);
        return new Result(true, "Energy set to " + energyAmount);
    }

    public Result setUnlimitedEnergy() {
        User player = App.getLoggedIn();
        player.setEnergyUnlimited(true);
        return new Result(true, "Unlimited Energy activated!");
    }

    public Result deactivateUnlimitedEnergy() {
        User player = App.getLoggedIn();
        player.setEnergyUnlimited(false);
        return new Result(true, "Unlimited Energy deactivated!");
    }

    public Result showCurrentTool() {
        User player = App.getLoggedIn();
        Tool playerCurrentTool = player.getCurrentTool();
        if (playerCurrentTool == null) {
            return new Result(false, "No tool equipped.");
        }
        return new Result(true, "Your tool is: " + playerCurrentTool.toString());
    }

    public Result showAvailableTools() {
        User player = App.getLoggedIn();
        StringBuilder sb = new StringBuilder();
        for (Item item : player.getBackpack().getItems().keySet()) {
            if (item instanceof Tool) {
                Tool tool = (Tool) item;
                sb
                    .append(tool.getName()).append(": ").append(tool.getToolMaterial())
                    .append("! Related Skill: ").append(tool.getRelatedSkill()).append("\n")
                ;
            }
        }
        return new Result(true, sb.toString());
    }

    public Result upgradeTools(String toolName) {
        User player = App.getLoggedIn();
        Tool tool = (Tool) Item.getItemByItemName(toolName);
        if (tool == null) {
            return new Result(false, "No tools with that name! Use: \n" + ToolType.getFullList());
        }
        if ((tool instanceof FishingRod)) {
            return new Result(false, "You should \"buy\" fishing rods at the fisher's shop. Don't use the upgrade command.");
        }

        if (
            App.getCurrentShop() == null ||
                App.getCurrentShop().getType() != ShopType.BLACKSMITH) {
            return new Result(false, "You can only ask the blacksmith to upgrade your " + toolName + ". Pay him a visit!");
        }
        equipTool(toolName);
        if (!canUpgradeTool(player.getCurrentTool(), player)) {
            return new Result(false, "You cannot upgrade this tool with your current items of possession.");
        }
        player.getCurrentTool().upgradeTool();
        player.changeBalance(-tool.getToolMaterial().getUpgradePrice());
        player.getBackpack().removeFromInventory(
            Item.getItemByItemType(tool.getToolMaterial().getIngredientForUpgrade()),
            tool.getToolMaterial().getNumOfIngredientNeededForUpgrade()
        );
        return new Result(true, toolName + " has been upgraded to " + tool.getToolMaterial() + ".");
    }

    public boolean canUpgradeTool(Tool tool, User player) {
        if (tool == null) {
            return false;
        }
        if (tool instanceof FishingRod) {
            return false;
        } else {
            Item ingredientItem = Item.getItemByItemType(tool.getToolMaterial().getIngredientForUpgrade());
            return player.getBackpack().getItems().get(ingredientItem) != null &&
                player.getBackpack().getItems().get(ingredientItem) >= tool.getToolMaterial().getNumOfIngredientNeededForUpgrade() &&
                player.getBalance() >= tool.getToolMaterial().getUpgradePrice();
        }
    }

    public Result showLearntCookingRecipes() {
        User player = App.getLoggedIn();
        String learntRecipes = player.getStringLearntCookingRecipes();
        return new Result(true, learntRecipes);
    }

    public Result showLearntCraftRecipes() {
        User player = App.getLoggedIn();
        String learntRecipes = player.getStringLearntCraftRecipes();
        if (learntRecipes == null) {
            return new Result(false, "No learnt recipes found.");
        }
        return new Result(true, learntRecipes);
    }

    // === TIME === //

    public Result showTime() {
        return new Result(true,
            "Time: " + App.getCurrentGame().getGameState().getTime().getHour() + ":00");
    }

    public Result showDate() {
        Time time = App.getCurrentGame().getGameState().getTime();
        return new Result(true, "Date: " + time.getDayInSeason() + " of " + time.getSeason().getName());
    }

    public Result showDateAndTime() {
        return new Result(true, showTime().message + "\n" + showDate().message);
    }

    public Result showDayOfTheWeek() {
        return new Result(true,
            "Day of the week: " + App.getCurrentGame().getGameState().getTime().getWeekday().getName());
    }

    public Result cheatAdvanceTime(String hourIncreaseStr) {
        int hour = Integer.parseInt(hourIncreaseStr);
        Time.cheatAdvanceTime(hour, App.getCurrentGame());
        return new Result(true, "Time increased by " + hour + " hours.");
    }

    public Result cheatAdvanceDate(String dayIncreaseStr) {
        int hourIncrease = Integer.parseInt(dayIncreaseStr);
        Time.cheatAdvanceDate(hourIncrease, App.getCurrentGame());
        return new Result(true, "Time increased by " + dayIncreaseStr + " days.");
    }

    public Result showSeason() {
        return new Result(true,
            "Season: " + App.getCurrentGame().getGameState().getTime().getSeason().getName());
    }

    // === INVENTORY === //

    public Result inventoryShow() {
        User player = App.getLoggedIn();
        return new Result(true, player.getBackpack().showItemsInInventory());
    }

    public Result throwItemToTrash(String itemName, String numberStr) {
        User player = App.getLoggedIn();
        Item item = Item.getItemByItemName(itemName);
        Integer number;
        if (numberStr == null) {
            number = null;
        } else {
            number = Integer.parseInt(numberStr);
        }
        return player.getBackpack().removeFromInventoryToTrash(item, number, player);
    }

    // === TOOLS, FOODS, ITEMS, AND CRAFTS === //

    public Result equipTool(String toolTypeName) {
        User player = App.getLoggedIn();
        ToolType toolType = ToolType.getToolTypeByName(toolTypeName);
        if (toolType == null) {
            String notFoundMessage = "Tool not found.\n" + "Enter a valid tool name: \n" + ToolType.getFullList();
            return new Result(false, notFoundMessage);
        }
        Item itemFromInventory = player.getBackpack().getItemFromInventoryByName(toolTypeName);
        if (itemFromInventory == null) {
            return new Result(false, "Tool not found in inventory.");
        }
        player.setCurrentTool((Tool) itemFromInventory);
        return new Result(true, toolTypeName + " equipped.");
    }

    public Result useTool(String directionString) {
        User player = App.getLoggedIn();
        Direction direction = Direction.getDirectionByDisplayName(directionString);
        if (direction == null) {
            return new Result(false, "Invalid Direction.");
        }
        Tile tile = neighborTile(direction);
        Tool tool = player.getCurrentTool();
        String failingMessage = "You can't use " + tool.toString() + " here on your " + direction.getDisplayName() + ".";
        if (tile == null) {
            return new Result(false, failingMessage);
        }
        if (tool.getToolType() == ToolType.MILK_PAIL) {
            return new Result(false, "Use the command below to use milk pail:\n" +
                "collect produce -n <animal's name>");
        }
        if (tool.getToolType() == ToolType.SHEARS) {
            return new Result(false, "Use the command below to use shear:\n" +
                "collect produce -n <animal's name>");
        }
        if (canToolBeUsedHere(tile, tool.getToolType())) {
            tool.useTool(tile, player);
            return new Result(true, "Done!");
        }
        return new Result(false, failingMessage);
    }

    public Result placeItem(String itemName, String directionString) {
        User player = App.getLoggedIn();
        Item item = player.getBackpack().getItemFromInventoryByName(itemName);
        if (item == null) {
            return new Result(false, "Item not found in inventory.");
        }
        Direction direction = Direction.getDirectionByDisplayName(directionString);
        Tile tile = neighborTile(direction);
        if (tile == null) {
            return new Result(false, "No available tile in that direction.");
        }

        String atPosOnTile = "at " + tile.getPosition().toString() + " on " + tile.getType().getDisplayName();

        if (canItemBePlacedHere(tile.getType())) {
            neighborTile(direction).pLaceItemOnTile(item);
            neighborTile(direction).setType(TileType.UNDER_AN_ITEM);
            return new Result(true, item + " placed " + atPosOnTile);
        }
        return new Result(false, "Cannot place item " + atPosOnTile);
    }

    public Result craft(String itemName) {
        User player = App.getLoggedIn();
        ItemType itemType = Item.getItemTypeByItemName(itemName);
        Item item = Item.getItemByItemName(itemName);
        CraftType craftType = CraftType.getCraftByName(itemName);
        if (craftType == null) {
            return new Result(false, "Item not found.");
        }
        if (!canCraftResult((CraftType) itemType).success) {
            return canCraftResult((CraftType) itemType);
        }

        HashMap<IngredientType, Integer> ingredients = craftType.getIngredients();
        for (IngredientType ingredientType : ingredients.keySet()) {
            int quantity = ingredients.get(ingredientType);
            player.getBackpack().removeFromInventory(item, quantity);
        }
        player.getBackpack().addToInventory(new Craft(craftType), 1);
        return new Result(true, itemName + " crafted and added to inventory.");
    }

    public Result showCraftInfo(String name) {
        CropType cropType = CropType.getCropTypeByName(name);
        if (cropType == null) {
            TreeType treeType = TreeType.getTreeTypeByName(name);
            if (treeType == null) {
                ForagingCropType foragingCropType = ForagingCropType.getForagingCropTypeByName(name);
                if (foragingCropType == null) {
                    return new Result(false, "No tree, crop, nor foraging crop found!");
                } else {
                    StringBuilder message = new StringBuilder();
                    message.append("Name of Foraging Crop: ").append(foragingCropType.getName()).append("\n");
                    if (!foragingCropType.getSeasons().isEmpty()) {
                        message.append("Season: ").append(foragingCropType.getSeasons()).append("\n");
                    }
                    message.append("Base sell price: ").append(foragingCropType.getBaseSellPrice()).append("\n");
                    message.append("Energy: ").append(foragingCropType.getEnergy()).append("\n");
                    return new Result(true, message.toString());
                }
            } else {
                StringBuilder message =
                    new StringBuilder("Name: " + treeType.getName() + "\n" +
                        "Source: " + treeType.getSource() + "\n" +
                        "Stages: ");

                message.append("7-7-7-7");

                message.append("\n" + "Total Harvest Time: ").append(treeType.getTotalHarvestTime()).append("\n").
                    append("Base Sell Price: ").append(treeType.getFruitBaseSellPrice()).append("\n").
                    append("Is Edible: ").append(treeType.isFruitEdible()).append("\n").
                    append("Base Fruit Energy: ");

                if (treeType.isFruitEdible()) {
                    message.append(treeType.getFruitEnergy());
                } else {
                    message.append("Not Edible");
                }

                message.append("\nSource: ").append(treeType.getSource().getName()).append("\n");

                return new Result(true, message.toString());
            }
        }

        StringBuilder message =
            new StringBuilder("Name: " + cropType.getName() + "\n" +
                "Source: " + cropType.getSource() + "\n" +
                "Stages: ");

        for (Integer stage : cropType.getStages()) {
            message.append(stage).append("-");
        }
        message.replace(message.length() - 1, message.length(), "");

        message.append("\n" + "Total Harvest Time: ").append(cropType.getTotalHarvestTime()).append("\n").append("One Time: ").append(cropType.isOneTime()).append("\n")
            .append("Regrowth Time: ");

        if (cropType.getRegrowthTime() != null) {
            message.append(cropType.getRegrowthTime());
        }

        message.append("\n").append("Base Sell Price: ").append(cropType.getSellPrice()).append("\n").
            append("Is Edible: ").append(cropType.isEdible()).append("\n").
            append("Base Energy: ");

        if (cropType.isEdible()) {
            message.append(cropType.getEnergy());
        } else {
            message.append("Not Edible");
        }
        message.append("\nSeason: ").append(cropType.getSource()).append("\n").
            append("Can Become Giant: ").append(cropType.canBecomeGiant()).append("\n")
        ;

        return new Result(true, message.toString());
    }

    public Result cheatAddItem(String itemName, String numberStr) {
        User player = App.getLoggedIn();
        Item item = Item.getItemByItemName(itemName);
        if (item == null) {
            return new Result(false, "Item not found.");
        }

        int number = 1;
        if (numberStr != null) {
            number = Integer.parseInt(numberStr);
        }
        player.getBackpack().addToInventory(item, number);
        return new Result(true, "Item added to inventory.");
    }

    public Result cookingRefrigerator(String putOrPick, String itemStr) {
        User player = App.getLoggedIn();
        Backpack backpack = player.getBackpack();
        Refrigerator refrigerator = player.getFarm().getCabin().getRefrigerator();
        Item item = Item.getItemByItemName(itemStr);
        Boolean isPut;
        switch (putOrPick.toLowerCase()) {
            case "put":
                isPut = true;
                break;
            case "pick":
                isPut = false;
                break;
            default:
                isPut = null;
        }
        if (isPut == null) {
            return new Result(false, "Rewrite the command; make sure you use \"put\" or \"pick\".");
        }
        if (isPut) {
            int number = backpack.getItems().get(item);
            refrigerator.addToInventory(item, number);
            backpack.removeFromInventory(item, number);
            return new Result(true, number + " " + item.getName() + " has been transferred from backpack to refrigerator.");
        } else {
            int number = refrigerator.getItems().get(item);
            backpack.addToInventory(item, number);
            refrigerator.removeFromInventory(item, number);
            return new Result(true, number + " " + item.getName() + " has been transferred from refrigerator to backpack.");
        }
    }

    public Result prepareCook(String foodName) {
        User player = App.getLoggedIn();
//        if (!(Main.getMain().getScreen() instanceof FarmhouseView)) {
//            return new Result(false, "You can cook inside your cabin only.");
//        }

        CookingRecipe cookingRecipe = CookingRecipe.getCookingRecipe(foodName);
        if (cookingRecipe == null) {
            return new Result(false, "Food not found.");
        }

        if (!canCookResult(cookingRecipe).success) {
            return new Result(false, canCookResult(cookingRecipe).message);
        }

        Backpack backpack = player.getBackpack();
        Refrigerator homeRefrigerator = player.getFarm().getCabin().getRefrigerator();
        Result result = removeIngredientsFromInventories(
            homeRefrigerator,
            backpack,
            cookingRecipe.getFoodType().getIngredients());

        if (!result.success){
            return result;
        }

        homeRefrigerator.addToInventory(new Food(cookingRecipe), 1);
        return new Result(true, "Yummy! Your fresh " + foodName + " added to the refrigerator.");
    }

    private Result canCookResult(CookingRecipe givenRecipe) {
        User player = App.getLoggedIn();
        boolean hasLearntRecipe = false;
        for (CookingRecipe recipe : player.getLearntCookingRecipes()) {
            if (recipe.getFoodType() == givenRecipe.getFoodType()) {
                hasLearntRecipe = true;
                break;
            }
        }
        if (!hasLearntRecipe) {
            return new Result(false, "You haven't learnt the recipe for this food.");
        }

        Backpack backpack = player.getBackpack();
        Refrigerator homeRefrigerator = player.getFarm().getCabin().getRefrigerator();
        Refrigerator combinedInventory = new Refrigerator();

        backpack.getItems().forEach(combinedInventory::addToInventory);
        homeRefrigerator.getItems().forEach(combinedInventory::addToInventory);

        if (
//                !backpack.isCapacityUnlimited() &&
//                backpack.getCapacity() <= backpack.getItems().size() &&
            !homeRefrigerator.isCapacityUnlimited() &&
                homeRefrigerator.getCapacity() <= backpack.getItems().size()
        ) {
            return new Result(false, "Your refrigerator is full.");
        }


        for (Map.Entry<IngredientType, Integer> entry : givenRecipe.getFoodType().getIngredients().entrySet()) {
            IngredientType ingredientType = entry.getKey();
            int requiredAmount = entry.getValue();

            if (!hasEnoughOfThatItem(Item.getItemByItemType(ingredientType), requiredAmount, combinedInventory)) {
                return new Result(false, "You don't have enough " + ingredientType.getName());
            }
        }
        return new Result(true, "");
    }

    private Result removeIngredientsFromInventories(
        Refrigerator refrigerator,
        Backpack backpack,
        HashMap<IngredientType, Integer> neededIngredients
    ) {
        HashMap<Item, Boolean> enoughItemInRefrigerator = new HashMap<>();

        for (Map.Entry<IngredientType, Integer> entry : neededIngredients.entrySet()) {
            Item ingredientItem = Item.getItemByItemType(entry.getKey());
            int requiredAmount = entry.getValue();

            int fridgeAmount = refrigerator.getItems().getOrDefault(ingredientItem, 0);
            int backpackAmount = backpack.getItems().getOrDefault(ingredientItem, 0);

            if (fridgeAmount + backpackAmount < requiredAmount) {
                return new Result(false, "You don't have enough " + ingredientItem.getName() + ".");
            }

            enoughItemInRefrigerator.put(ingredientItem, fridgeAmount >= requiredAmount);
        }

        for (IngredientType itemType : neededIngredients.keySet()) {
            Item ingredientItem = Item.getItemByItemType(itemType);
            int requiredAmount = neededIngredients.get(itemType);
            int fridgeAmount = refrigerator.getItems().getOrDefault(ingredientItem, 0);

            if (enoughItemInRefrigerator.get(ingredientItem)) {
                refrigerator.removeFromInventory(ingredientItem, requiredAmount);
            } else {
                int neededAmountFromBackpack = requiredAmount - fridgeAmount;
                refrigerator.removeFromInventory(ingredientItem, fridgeAmount);
                backpack.removeFromInventory(ingredientItem, neededAmountFromBackpack);
            }
        }

        return new Result(true, "");
    }

    private boolean hasEnoughOfThatItem(Item specificItem, int requiredAmount, Inventory inventory) {
        if (!inventory.getItems().containsKey(specificItem)) {
            return false;
        }
        return inventory.getItems().get(specificItem) >= requiredAmount;
    }

    public Result eat(String foodName) {
        FoodType foodType = FoodType.getFoodTypeByName(foodName);
        Food food = new Food(foodType);
        User player = App.getLoggedIn();
        Backpack backpack = player.getBackpack();
        Refrigerator homeRefrigerator = player.getFarm().getCabin().getRefrigerator();
        if (!backpack.getItems().containsKey(food) && !homeRefrigerator.getItems().containsKey(food)) {
            return new Result(
                false,
                "You don't have a " + food.getName() + ", neither in your backpack, nor in your cabin's refrigerator."
            );
        }
        if (!backpack.getItems().containsKey(food) &&
            homeRefrigerator.getItems().containsKey(food) &&
            App.getCurrentGame().getPlayerByUsername(player.getUsername()).getFarm().getTileByPosition(player.getPosition()).getType() != TileType.CABIN) {
            return new Result(
                false,
                "You don't have a " + food.getName() + " in your backpack, but you have one back in your cabin.\n" +
                    "Go there and enjoy your " + food.getName() + "!"
            );
        }
        assert foodType != null;
        player.increaseEnergyBy(foodType.getEnergy());
        FoodBuff buff = foodType.getBuff();
        player.activateFoodBuff(buff);
        String message = "Bon appétit!\nYour " + food.getName() + " had these effects:\n" +
            "\tyour energy increased by " + foodType.getEnergy() + "\n" + "\tbuff: " + buff.getBuffDisplayName();
        return new Result(true, message);
    }

    public static Result canCraft(CraftType craftType) {
        return new GameController().canCraftResult(craftType);
    }

    private Result canCraftResult(CraftType craftType) {
        CraftRecipe recipe = new CraftRecipe(craftType);
        User player = App.getLoggedIn();
        if (player.getBackpack().getCapacity() <= player.getBackpack().getItems().size()) {
            return new Result(false, "Your backpack is full.");
        }

        if (!player.hasLearntCraftRecipe(recipe.getCraftType())) {
            return new Result(false, "You should learn the recipe first.");
        }

        HashMap<IngredientType, Integer> neededIngredients = craftType.getIngredients();
        for (Map.Entry<IngredientType, Integer> entry : neededIngredients.entrySet()) {
            IngredientType ingredientType = entry.getKey();
            int requiredAmount = entry.getValue();

            if (!hasEnoughOfThatItem(Item.getItemByItemType(ingredientType), requiredAmount, player.getBackpack())) {
                return new Result(true, "You don't have enough " + ingredientType.getName()); // todo
            }
        }
        return new Result(true, "");
    }

    private boolean canToolBeUsedHere(Tile tile, ToolType toolType) { // todo: check GREENHOUSE options.
        TileType tileType = tile.getType();
        if (toolType == ToolType.HOE) {
            return tileType == TileType.PLOWED_SOIL ||
                tileType == TileType.NOT_PLOWED_SOIL;
        } else if (toolType == ToolType.PICKAXE) {
            return tileType == TileType.PLOWED_SOIL ||
                tileType == TileType.NOT_PLOWED_SOIL ||
                tileType == TileType.PLANTED_SEED ||
                tileType == TileType.GROWING_CROP ||
                tileType == TileType.QUARRY_GROUND ||
                tileType == TileType.STONE ||
                tileType == TileType.UNDER_AN_ITEM;
        } else if (toolType == ToolType.AXE) {
            return tileType == TileType.TREE ||
                tileType == TileType.WOOD_LOG ||
                tileType == TileType.PLOWED_SOIL ||
                tileType == TileType.NOT_PLOWED_SOIL;
        } else if (toolType == ToolType.WATERING_CAN) {
            return tileType != TileType.CABIN && tileType != TileType.SHOP;
        } else if (toolType == ToolType.FISHING_ROD) {
            return tileType == TileType.WATER;
        } else if (toolType == ToolType.SCYTHE) {
            return tileType == TileType.GRASS ||
                tileType == TileType.TREE ||
                tileType == TileType.GROWING_CROP ||
                tileType == TileType.GREENHOUSE;
        } else if (toolType == ToolType.MILK_PAIL) {
            return tileType == TileType.ANIMAL;
        } else if (toolType == ToolType.SHEARS) {
            return tileType == TileType.ANIMAL;
        } else return toolType == ToolType.TRASH_CAN; // directs to appropriate message.
    }

    private boolean canItemBePlacedHere(TileType tileType) {
        return tileType == TileType.PLOWED_SOIL ||
            tileType == TileType.NOT_PLOWED_SOIL ||
            tileType == TileType.CABIN ||
            tileType == TileType.GRASS ||
            tileType == TileType.QUARRY_GROUND;
    }


    public Tile neighborTile(Direction direction) {
        User player = App.getLoggedIn();
        Position newPosition = Direction.getNewPosition(player.getPosition(), direction);
        return App.getCurrentGame().getPlayerByUsername(player.getUsername()).getFarm().getTileByPosition(newPosition);
    }

    // === WALK === //

    public Result respondForWalkRequest(String targetXStr, String targetYStr) {
        User player = App.getLoggedIn();
        try {
            int targetX = Integer.parseInt(targetXStr);
            int targetY = Integer.parseInt(targetYStr);

            Position playerPos = player.getPosition();
            Position destination = new Position(targetX, targetY);

            // are coordinates within map bounds?
            if (isPositionInvalid(destination)) {
                return new Result(false, "Coordinates (" + targetX + "," + targetY + ") are out of bounds.");
            }

            if (Greenhouse.isPositionInGreenhouse(destination) &&
                !App.getCurrentGame().getPlayerByUsername(player.getUsername()).getFarm().getGreenhouse().canEnter()) {
                return new Result(false, "You haven't built the greenhouse yet, so you can't enter it.");
            }
            PathFinder pf = new PathFinder(player);
            Path path = pf.findValidPath(playerPos, destination);

            if (path == null) {
                return new Result(false, "No valid path found to (" + targetX + "," + targetY + ")");
            }

            String message = String.format(
                "Found path to (%d,%d):\nDistance: %d tiles\nTurns: %d\nEnergy needed: %d",
                targetX, targetY,
                path.getDistanceInTiles(),
                path.getNumOfTurns(),
                path.getEnergyNeeded()
            );
            System.out.println(message);
            return new Result(true, targetXStr + " " + targetYStr);

        } catch (NumberFormatException e) {
            return new Result(false, "Invalid coordinates - must be numbers");
        } catch (Exception e) {
            return new Result(false, "Error: " + e.getMessage());
        }
    }

    public Result applyTheWalk(String yOrN, String xAndYValues) {
        Boolean confirmed;
        if (yOrN == null) confirmed = true;
        else {
            switch (yOrN.toLowerCase()) {
                case "y":
                case "yes":
                    confirmed = true;
                    break;
                case "n":
                case "no":
                    confirmed = false;
                    break;
                default:
                    confirmed = null;
            }
        }

        if (confirmed == null) {
            return new Result(false, "Invalid confirmation. Use \"y\" or \"n\".");
        }
        if (!confirmed) {
            return new Result(false, "You denied the walk.");
        }

        String[] parts = xAndYValues.split(" ");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);

        User player = App.getLoggedIn();
        Position orig = player.getPosition();
        Position dest = new Position(x, y);

        PathFinder pf = new PathFinder(player);
        Path path = pf.findValidPath(orig, dest);
        if (path == null) {
            return new Result(false, "No valid path found to (" + x + "," + y + ")");
        }

        return pf.walk(path, yOrN);
    }


    private boolean isPositionInvalid(Position pos) {
        List<Tile> allTiles = App.getCurrentGame().getPlayerByUsername(App.getLoggedIn().getUsername()).getFarm().getAllTiles();
        if (allTiles.isEmpty()) return true;

        int maxX = allTiles.stream().mapToInt(t -> t.getPosition().getX()).max().orElse(0);
        int maxY = allTiles.stream().mapToInt(t -> t.getPosition().getY()).max().orElse(0);

        return pos.getX() < 0 || pos.getX() > maxX ||
            pos.getY() < 0 || pos.getY() > maxY;
    }

    // === PRINT MAP === //

    public Result printMapVillage(String xString, String yString, String sizeString) {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        int size = Integer.parseInt(sizeString);

        if (!App.getCurrentGame().getVillage().isPositionValid(new Position(x, y))) {
            return new Result(false, "Coordinates (" + x + "," + y + ") are out of bounds.");
        }

        StringBuilder mapRepresentation = new StringBuilder();
        Position playerPos = App.getLoggedIn().getPosition();

        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                Position pos = new Position(i, j);
                Tile tile = App.getCurrentGame().getVillage().getTileByPosition(pos);

                if (pos.getX() == 10 && pos.getY() == 15) {
                    mapRepresentation.append("\uD83E\uDDD1\u200D\uD83D\uDCBB"); // Sebastian
                } else if (pos.getX() == 15 && pos.getY() == 10) {
                    mapRepresentation.append("\uD83E\uDDD1\u200D\uD83C\uDFA4"); // Abigail
                } else if (pos.getX() == 25 && pos.getY() == 5) {
                    mapRepresentation.append("\uD83D\uDC68\u200D⚕\uFE0F"); // Harvey
                } else if (pos.getX() == 5 && pos.getY() == 20) {
                    mapRepresentation.append("\uD83D\uDC69\u200D\uD83C\uDF3E"); // Leah
                } else if (pos.getX() == 20 && pos.getY() == 20) {
                    mapRepresentation.append("\uD83D\uDC69\u200D\uD83C\uDFED"); // Robin
                } else if (pos.getX() == playerPos.getX() && pos.getY() == playerPos.getY()) {
                    mapRepresentation.append("👤");
                } else {
                    mapRepresentation.append(getTileSymbol(tile)).append(" ");
                }
            }
            mapRepresentation.append("\n");
        }

        return new Result(true, mapRepresentation.toString());
    }

    public Result printMap(String xString, String yString, String sizeString) {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        int size = Integer.parseInt(sizeString);

        if (isPositionInvalid(new Position(x, y))) {
            return new Result(false, "Coordinates (" + x + "," + y + ") are out of bounds.");
        }

        StringBuilder mapRepresentation = new StringBuilder();
        Position playerPos = App.getLoggedIn().getPosition();

        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                Position pos = new Position(i, j);
                Tile tile = App.getCurrentGame().getPlayerByUsername(App.getLoggedIn().getUsername()).getFarm().getTileByPosition(pos);

                if (pos.getX() == playerPos.getX() && pos.getY() == playerPos.getY()) {
                    mapRepresentation.append("👤");
                } else {
                    mapRepresentation.append(getTileSymbol(tile)).append(" ");
                }
            }
            mapRepresentation.append("\n");
        }

        return new Result(true, mapRepresentation.toString());
    }


    private String getTileSymbol(Tile tile) {
        if (tile == null) return "⬜";

        switch (tile.getType()) {
            case TREE:
                return "🌳";
            case WATER:
                return "🌊";
            case CABIN:
                return "🏠";
            case STONE:
                return "🪨";
            case GREENHOUSE:
                return "🪟";
            case QUARRY_GROUND:
                return "⛰️";
            case WOOD_LOG:
                return "🪵";
            case GROWING_CROP:
                return "🌱";
            case ANIMAL:
                return "🐄";
            case PLOWED_SOIL:
                return "🟤";
            case NOT_PLOWED_SOIL:
                return "🟫";
            case PLANTED_SEED:
                return "🌾";
            case WATERED_NOT_PLOWED_SOIL:
                return "💧";
            case WATERED_PLOWED_SOIL:
                return "💦";
            case GRASS:
                return "⸙";
            case UNDER_AN_ITEM:
                return "📦";
            case SHOP:
                String shopIcon;
                switch (whichShop(tile)) {
                    case JOJAMART:
                        shopIcon = "🏪"; // farming tools and seeds
                        break;
                    case CARPENTER_SHOP:
                        shopIcon = "\uD83E\uDE9A"; // wood and stone
                        break;
                    case FISH_SHOP:
                        shopIcon = "\uD83D\uDC1F"; // fishing
                        break;
                    case MARNIE_RANCH:
                        shopIcon = "🐄"; // animal
                        break;
                    case BLACKSMITH:
                        shopIcon = "⚒️"; // mineral and extracting tools
                        break;
                    case PIERRE_GENERAL_STORE:
                        shopIcon = "\uD83C\uDF3E"; // farming
                        break;
                    case THE_STARDROP_SALOON:
                        shopIcon = "🍻"; // foods and drinks
                        break;
                    default:
                        shopIcon = null;
                }
                return shopIcon;
            default:
                return "❓";
        }
    }

    public static ShopType whichShop(Tile tile) {
        for (Shop shop : App.getCurrentGame().getVillage().getShops()) {
            if (shop.containsPosition(tile.getPosition())) {
                return shop.getType();
            }
        }

        return null;
    }


    public Result showHelpReadingMap() {
        String helpText = "=== Map Symbols Legend ===\n" +
            "🌳 - Tree\n" +
            "🌊 - Water\n" +
            "🏠 - Cabin\n" +
            "🪨 - Stone\n" +
            "🪟 - Greenhouse\n" +
            "⛰️ - Quarry Ground\n" +
            "🪵 - Wood Log\n" +
            "🌱 - Growing Crop\n" +
            "🐄 - Animal\n" +
            "🟤 - Plowed Soil\n" +
            "🟫 - Not Plowed Soil\n" +
            "🌾 - Planted Seed\n" +
            "💧 - Watered Not Plowed Soil\n" +
            "💦 - Watered Plowed Soil\n" +
            "⸙ - Grass\n" +
            "📦 - Item\n" +
            "❓ - Unknown Tile\n\n" +

            "=== Shops ===\n" +
            "🏪 - JojaMart (General store)\n" +
            "🪚 - Carpenter Shop (Wood and construction)\n" +
            "\uD83D\uDC1F - Fish Shop (Fishing supplies)\n" +
            "🐄 - Marnie Ranch (Animal ranch)\n" +
            "⚒️ - Blacksmith (Crafting and minerals)\n" +
            "\uD83C\uDF3E - Pierre's General Store (Farming goods)\n" +
            "🍻 - Stardrop Saloon (Food and drinks)\n";
        return new Result(true, helpText);
    }


    // === GAME STATUS === //

    public Result cheatThor(String xString, String yString) {
        Position position = new Position(Integer.parseInt(xString), Integer.parseInt(yString));
        Tile tile = App.getCurrentGame().getPlayerByUsername(App.getLoggedIn().getUsername()).getFarm().getTileByPosition(position);

        view.setLightningPosition(position);

        if (tile != null) {
            tile.setType(TileType.NOT_PLOWED_SOIL); // TODO: is it good?
            App.getCurrentGame().getGameState().triggerLightningStrike();
            return new Result(true, "Thor has struck (" + xString + ", " + yString + ")!");
        }
        return new Result(false, "Invalid position for Thor's strike.");
    }

    public Result showWeather() {
        Weather weather = App.getCurrentGame().getGameState().getCurrentWeather();
        return new Result(true, "Current weather: " + weather.getName());
    }

    public Result showWeatherForecast() {
        Season currentSeason = App.getCurrentGame().getGameState().getTime().getSeason();
        List<Weather> availableWeathers = currentSeason.getAvailableWeathers();
        Weather forecast = availableWeathers.get(new Random().nextInt(availableWeathers.size()));

        return new Result(true, "The forecast for " + currentSeason.getName() + " is " + forecast.getName());
    }


    public Result cheatWeatherSet(String newWeatherString) {
        try {
            String normalizedWeather = newWeatherString.trim().toUpperCase();
            Weather newWeather = Weather.valueOf(normalizedWeather);
            App.getCurrentGame().getGameState().setCurrentWeather(newWeather);
            return new Result(true, "Weather set to: " + newWeather.name());
        } catch (IllegalArgumentException e) {
            StringBuilder validOptions = new StringBuilder("Invalid weather type. Valid options are: ");
            for (Weather weather : Weather.values()) {
                validOptions.append(weather.name()).append(", ");
            }
            validOptions.setLength(validOptions.length() - 2);

            return new Result(false, validOptions.toString());
        }
    }

    public Result buildGreenhouse() {
        if (!canBuildGreenhouse()) {
            return new Result(false, "You don't have enough resources or a greenhouse already exists!");
        }
        App.getCurrentGame().getPlayerByUsername(App.getLoggedIn().getUsername()).getFarm().activateGreenhouse();
        return new Result(true, "Greenhouse built successfully! You can now enter and use it.");
    }

    public Result cheatBuildNewGreenhouse() {
        App.getCurrentGame().getPlayerByUsername(App.getLoggedIn().getUsername()).getFarm().activateGreenhouse();
        return new Result(true, "Greenhouse built successfully! You can now enter and use it.");
    }

    // === PLANTS === //

    public Result plant(String seedName, String directionName) {
        SeedType seedType = SeedType.getSeedByName(seedName);
        TreeSourceType treeSourceType = TreeSourceType.getTreeSourceTypeByName(seedName);
        Direction direction = Direction.getDirectionByDisplayName(directionName);
        Tile tile = neighborTile(direction);
        if (tile.getType().equals(TileType.NOT_PLOWED_SOIL)) {
            return new Result(false, "You must plow the ground first! Use hoe.");
        }
        if (seedType != null) {
            tile.pLaceItemOnTile(new Crop(seedType));
            tile.setType(TileType.GROWING_CROP);
            return new Result(true, seedName + " (crop seed) planted in position: " + tile.getPosition().toString());
        }
        if (treeSourceType != null) {
            tile.pLaceItemOnTile(new Tree(TreeType.getTreeTypeBySourceType(treeSourceType)));
            tile.setType(TileType.TREE);
            return new Result(true, seedName + " (tree source) planted in position: " + tile.getPosition().toString());
        }
        return new Result(false, "seed not found");
    }

    public Result showPlant(String xString, String yString) {
        Position position = new Position(Integer.parseInt(xString), Integer.parseInt(yString));
        Tile tile = App.getCurrentGame().getPlayerByUsername(App.getLoggedIn().getUsername()).getFarm().getTileByPosition(position);
        if (tile.getType() != TileType.PLANTED_SEED &&
            tile.getType() != TileType.GROWING_CROP &&
            tile.getType() != TileType.GREENHOUSE &&
            tile.getType() != TileType.TREE) {
            return new Result(false, "No plants in this tile.");
        }

        Item item = tile.getItemPlacedOnTile();
        String message = showCraftInfo(item.getName()).message;

        if (item instanceof Tree) {
            Tree tree = (Tree) item;
            int daysLeft;
            if (tree.getDaySinceLastHarvest() != null) {
                daysLeft = tree.getTotalHarvestTime() - tree.getDaySinceLastHarvest();
            } else {
                daysLeft = tree.getTotalHarvestTime();
            }
            message += "Days left to harvest: " + (daysLeft <= 0 ? "Ready to harvest" : daysLeft) + "\n" +
                "Current stage: " + tree.getStage() + "\n" +
                "Has been watered today: ";

            if (tree.hasBeenWateredToday()) {
                message += "Yes";
            } else {
                message += "No";
            }

            message += "\n" +
                "Has been fertilized today: ";

            if (tree.hasBeenFertilizedToday()) {
                message += "Yes";
            } else {
                message += "No";
            }
        }

        if (item instanceof Crop) {
            Crop crop = (Crop) item;
            int daysLeft = crop.getDaySinceLastHarvest() == null ? crop.getTotalHarvestTime() :
                crop.getTotalHarvestTime() - crop.getDaySinceLastHarvest();

            message += "Days left to harvest: " + (daysLeft < 0 ? "Ready to harvest" : daysLeft) + "\n";

            message += "Current stage: " + crop.getStage() + "\n" +
                "Has been watered today: ";

            if (crop.hasBeenWateredToday()) {
                message += "Yes";
            } else {
                message += "No";
            }

            message += "\n" +
                "Has been fertilized today: ";

            if (crop.hasBeenFertilizedToday()) {
                message += "Yes";
            } else {
                message += "No";
            }
        }

        return new Result(true, message);
    }

    public Result fertilize(String fertilizerName, String directionName) {
        FertilizerType fertilizerType = FertilizerType.getFertilizerTypeByName(fertilizerName);
        if (fertilizerType == null) {
            return new Result(false, "Invalid fertilizer name");
        }
        Direction direction = Direction.getDirectionByDisplayName(directionName);
        Tile tile = neighborTile(direction);
        Item plant = tile.getItemPlacedOnTile();
        if (!(plant instanceof Tree) && !(plant instanceof Crop) && !(plant instanceof PlantSource)) {
            return new Result(false, "You can fertilize crops, trees, and seeds only.");
        }
        if (plant instanceof Tree) {
            Tree tree = (Tree) plant;
            tree.setHasBeenFertilizedToday(true);
            return new Result(true, "Tree fertilized with " + fertilizerType.getName());
        }
        if (plant instanceof Crop) {
            Crop crop = (Crop) plant;
            crop.setHasBeenFertilizedToday(true);
            return new Result(true, "Crop fertilized with " + fertilizerType.getName());
        }
        return new Result(true, "Fertilized with " + fertilizerType.getName());
    }

    public Result howMuchWater() {
        Backpack backpack = App.getLoggedIn().getBackpack();
        WateringCan w = (WateringCan) backpack.getItemFromInventoryByName(ToolType.WATERING_CAN.getName());
        String message = "Water left in Watering Can: " + w.getRemainingWater() + " / " + w.getWaterCapacity();
        return new Result(true, message);
    }

    // === FARM BUILDINGS & ANIMALS === //

    public Result build(String farmBuildingTypeStr, String xString, String yString) {
        Shop shop = App.getCurrentShop();
        if (shop == null) {
            return new Result(false, "You must first enter Marnie's Ranch.");
        }

        if (!shop.getType().equals(ShopType.MARNIE_RANCH)) {
            return new Result(false, "You must first enter Marnie's Ranch.");
        }

        Position position = Position.getPositionByStrings(xString, yString);
        if (position == null) {
            return new Result(false, "Enter two valid numbers for x and y.");
        }
        return build(farmBuildingTypeStr, position);
    }

    public Result build(String farmBuildingTypeStr, Position position) {
        FarmBuildingType farmBuildingType = FarmBuildingType.getFarmBuildingTypeByName(farmBuildingTypeStr);
        if (farmBuildingType == null) {
            return new Result(false, "Enter a valid building name.");
        }

        User player = App.getLoggedIn();
        Farm farm = player.getFarm();
        FarmBuilding farmBuilding = new FarmBuilding(farmBuildingType, position);

        boolean canPlace = farm.canPlaceBuilding(farmBuildingType, position);

        if (!canPlace) {
            return new Result(false, "Can't build a " + farmBuildingType.getName() +
                " in this position, because the ground is not empty.");
        }

        HashMap<Item, Integer> items = player.getBackpack().getItems();
        Material wood = new Material(MaterialType.WOOD);
        Material stone = new Material(MaterialType.STONE);
        int woodInInventory = 0;
        if (items.get(wood) != null) {
            items.get(wood);
        }
        int stoneInInventory = 0;
        if (items.get(stone) != null) {
            items.get(stone);
        }
        int woodNeeded = farmBuildingType.getWoodCount();
        int stoneNeeded = farmBuildingType.getStoneCount();
        boolean enoughSupplies = (woodNeeded <= woodInInventory) && (stoneNeeded <= stoneInInventory);

        double cost = farmBuildingType.getCost();
        boolean enoughMoney = player.getBalance() >= cost;

        if (!enoughSupplies && !enoughMoney) {
            return new Result(false, "You don't have enough supplies or money to build a "
                + farmBuildingType.getName());
        }

        String methodOfPaymentDescription;
        if (!enoughSupplies) {
            player.changeBalance(cost);
            methodOfPaymentDescription = "You payed " + cost + "g to build it.";
        } else {
            int newWoodCount = woodInInventory - woodNeeded;
            int newStoneCount = stoneInInventory - stoneNeeded;
            player.getBackpack().removeFromInventory(wood, newWoodCount);
            player.getBackpack().removeFromInventory(stone, newStoneCount);
            methodOfPaymentDescription = "You used " + woodNeeded + " woods and " + stoneNeeded + " stones to build it";
        }
        farm.getFarmBuildings().add(farmBuilding);

        return new Result(true, "A " + farmBuildingType.getName() + " has been built in "
            + position.toString() + ". " + methodOfPaymentDescription);
    }

    public Result buyAnimal(String animalTypeStr, String name) {
//        Shop shop = App.getCurrentShop();
//        if (shop == null) {
//            return new Result(false, "You Must first enter Marnie's Ranch.");
//        }
//
//        if (!shop.getType().equals(ShopType.MARNIE_RANCH)) {
//            return new Result(false, "You Must first enter Marnie's Ranch.");
//        }

        AnimalType animalType = AnimalType.getAnimalTypeByName(animalTypeStr);
        if (animalType == null) {
            return new Result(false, "Animal not found: " + animalTypeStr);
        }

        User player = App.getLoggedIn();
        Farm farm = player.getFarm();
        List<FarmBuildingType> livingSpaceTypes = animalType.getLivingSpaceTypes();
        AnimalLivingSpace animalLivingSpace = farm.getAvailableLivingSpace(livingSpaceTypes);

        if (animalLivingSpace == null) {
            return new Result(false, "You don't have any available living spaces for a "
                + animalType.getName() + ".");
        }

        if (farm.getAnimalByName(name) != null) {
            return new Result(false, "You already have an animal called " + name + ".");
        }

        if (App.getLoggedIn().getBalance() < animalType.getPrice()) {
            return new Result(false, "You do not have enough money to buy a " +
                animalType.getName() + ".");
        }

        App.getLoggedIn().changeBalance(animalType.getPrice());
        Animal animal = new Animal(name, animalType, animalLivingSpace);
        animalLivingSpace.addAnimal(animal);
        return new Result(true, "You bought a " + animalType.getName() + " called " + name +
            " and housed it in a " + animalLivingSpace.getFarmBuildingType().getName() + ".");
    }

    public Result pet(String animalName) {
        User player = App.getLoggedIn();
        Farm farm = player.getFarm();
        Animal animal = farm.getAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "Animal not found.");
        }

        animal.changeFriendship(15);
        animal.setLastPettingTime(App.getCurrentGame().getGameState().getTime());

        return new Result(true, "You pet your " + animal.getAnimalType().getName() + ", " +
            animalName + ". Its' friendship level is now " + animal.getFriendshipLevel() + ".");
    }

    public Result cheatSetFriendship(String animalName, String amountString) {
        int amount;
        if (!amountString.matches("\\d+")) {
            return new Result(false, "Enter a number between 0 and 1000.");
        } else {
            amount = Integer.parseInt(amountString);
        }

        User player = App.getLoggedIn();
        Farm farm = player.getFarm();
        Animal animal = farm.getAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "Animal not found.");
        }

        animal.setFriendshipLevel(amount);

        return new Result(true, "Friendship of your " + animal.getAnimalType().getName() + ", " +
            animalName + ", has been set to " + amount + ".");
    }

    public Result showMyAnimalsInfo() {
        StringBuilder message = new StringBuilder("Your animals: \n");

        User player = App.getLoggedIn();
        Farm farm = player.getFarm();
        for (Animal animal : farm.getAllFarmAnimals()) {

            message.append("-------------------------------\n").append(animal.getName()).append(" (").
                append(animal.getAnimalType().getName()).append("):\n").append("Friendship level: ").
                append(animal.getFriendshipLevel()).append("\n");

            if (animal.hasBeenFedToday()) {
                message.append("Has been fed today.\n");
            } else {
                message.append("Has not been fed today.\n");
            }

            if (animal.hasBeenPetToday()) {
                message.append("Has been pet today.\n");
            } else {
                message.append("Has not been pet today.\n");
            }
        }

        return new Result(true, message.toString());
    }

    public Result shepherdAnimal(String animalName, String xString, String yString) {
        return shepherdAnimal(animalName, Position.getPositionByStrings(xString, yString));
    }

    public Result shepherdAnimal(String animalName, Position newPosition) {
        if (newPosition == null) {
            return new Result(false, "Enter two valid numbers for x and y.");
        }

        System.out.println(newPosition);

        User player = App.getLoggedIn();
        Farm farm = player.getFarm();
        Animal animal = farm.getAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "Animal not found.");
        }

        if (abs(pow(animal.getPosition().getX() - newPosition.getX(), 2) + pow(animal.getPosition().getY() - newPosition.getY(), 2)) > 5) {
            return new Result(false, "Animal can't move more than 5 tiles at once.");
        }

        FarmBuilding farmBuildingInNewPosition = farm.getFarmBuildingByPosition(newPosition);
        if (animal.isOutside()) {
            if (animal.getPosition().equals(newPosition)) {
                return new Result(false, "Your " + animal.getAnimalType().getName() + ", " + animalName
                    + ", is already at " + newPosition.toString());
            }

            if (!App.getCurrentGame().getPlayerByUsername(App.getLoggedIn().getUsername()).getFarm().getTileByPosition(newPosition).getType().equals(TileType.NOT_PLOWED_SOIL)) {
                return new Result(false, "Your animal can't go to this position.");
            }

            if (farmBuildingInNewPosition != null) {
                if (!farmBuildingInNewPosition.equals(animal.getAnimalLivingSpace())) {
                    return new Result(false, "Your animal can't go to this position.");
                }

                animal.setPosition(newPosition);
                animal.setLastFeedingTime(App.getCurrentGame().getGameState().getTime());
                animal.setOutside(false);
                return new Result(true, "Your " + animal.getAnimalType().getName() + ", " + animalName
                    + ", has been moved to its' living space.");
            }

            animal.setPosition(newPosition);
            return new Result(true, "Your " + animal.getAnimalType().getName() + ", " + animalName
                + ", has been moved to " + newPosition.toString() + ".");
        }

        if (farmBuildingInNewPosition != null) {
            if (farmBuildingInNewPosition.equals(animal.getAnimalLivingSpace())) {
                return new Result(false, "Your animal is already in its' living space.");
            }

            return new Result(false, "Your animal can only go on grass.");
        }

        Weather currentWeather = App.getCurrentGame().getGameState().getCurrentWeather();
        if (!currentWeather.equals(Weather.SUNNY)) {
            return new Result(false, "You can't take your animals out in " + currentWeather.toString() +
                " weather.");
        }

        animal.setPosition(newPosition);
        animal.setLastFeedingTime(App.getCurrentGame().getGameState().getTime());
        animal.changeFriendship(8);
        return new Result(true, "Your " + animal.getAnimalType().getName() + ", " + animalName
            + ", has been moved to " + newPosition + " and is now outside. Its' friendship level is now "
            + animal.getFriendshipLevel() + ".");
    }

    public Result feedHayToAnimal(String animalName) {
        User player = App.getLoggedIn();
        Farm farm = player.getFarm();
        Animal animal = farm.getAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "Animal not found.");
        }

        animal.setLastFeedingTime(App.getCurrentGame().getGameState().getTime());
        return new Result(true, "You fed hay to your " + animal.getAnimalType().getName() + ", "
            + animalName + ".");
    }

    public Result showProducedProducts() {
        StringBuilder message = new StringBuilder("Uncollected animal products: \n");
        User player = App.getLoggedIn();
        Farm farm = player.getFarm();
        for (Animal animal : farm.getAllFarmAnimals()) {
            if (!animal.getProducedProducts().isEmpty()) {
                message.append("-------------------------------\n").append(animal.getName()).append(" (").
                    append(animal.getAnimalType().getName()).append("):\n");

                for (AnimalProduct product : animal.getProducedProducts()) {
                    message.append("- ").append(product.getType().getName()).append("\n");
                }
            }
        }

        return new Result(true, message.toString());
    }

    public Result collectProducts(String animalName) { // TODO: fix
        User player = App.getLoggedIn();
        Farm farm = player.getFarm();
        Animal animal = farm.getAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "Animal not found.");
        }

        if (animal.getProducedProducts().isEmpty()) {
            return new Result(false, animalName + " does not have any product to collect.");
        }

        AnimalType animalType = animal.getAnimalType();

        ArrayList<Item> items = new ArrayList<>(player.getBackpack().getItems().keySet());
        HashMap<AnimalProduct, Integer> collectedProducts = new HashMap<>();

        if (animalType.equals(AnimalType.COW)) {
            MilkPail milkPail = null;
            for (Item item : items) {
                if (item instanceof MilkPail) {
                    milkPail = (MilkPail) item;
                    break;
                }
            }
            if (milkPail == null) {
                return new Result(false, "You need a milk pail to collect the cow's products.");
            }
            milkPail.useTool(animal);
        } else if (animalType.equals(AnimalType.GOAT)) {
            MilkPail milkPail = null;
            for (Item item : items) {
                if (item instanceof MilkPail) {
                    milkPail = (MilkPail) item;
                    break;
                }
            }
            if (milkPail == null) {
                return new Result(false, "You need a milk pail to collect the goat's products.");
            } else {
                milkPail.useTool(animal);
            }
        } else if (animalType.equals(AnimalType.SHEEP)) {
            Shear shear = null;
            for (Item item : items) {
                if (item instanceof Shear) {
                    shear = (Shear) item;
                    break;
                }
            }
            if (shear == null) {
                return new Result(false, "You need a shear to collect the sheep's products.");
            } else {
                shear.useTool(animal);
            }
        } else if (animalType.equals(AnimalType.PIG) && !animal.isOutside()) {
            return new Result(false, "Take the pig outside to collect its' products.");
        } else {
            HashMap<Item, Integer> itemsHashMap = player.getBackpack().getItems();
            for (AnimalProduct item : animal.getProducedProducts()) {
                player.getBackpack().addToInventory(item, 1);
                collectedProducts.put(item, collectedProducts.getOrDefault(item, 0) + 1);
            }
            animal.setProducedProducts(new ArrayList<>());
        }

        player.updateSkillPoints(Skill.FARMING, 5);
        StringBuilder message = new StringBuilder("You collected ");
        for (AnimalProduct item : collectedProducts.keySet()) {
            message.append(item.getType().getName()).append(" (x").append(collectedProducts.get(item)).append("), ");
        }
        return new Result(true, message.toString().replaceFirst(", $", "\n"));
    }

    public Result sellAnimal(String animalName) {
        User player = App.getLoggedIn();
        Farm farm = player.getFarm();
        Animal animal = farm.getAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "Animal not found.");
        }

        double price = animal.calculatePrice();
        player.changeBalance(price);
        animal.getAnimalLivingSpace().removeAnimal(animal);
        return new Result(true, "You sold your " + animal.getAnimalType().getName() + ", " +
            animalName + ", for " + price + "g.");
    }

    // === FISHING === //

    public Result fishing(String fishingRodName) {
        User player = App.getLoggedIn();
        FishingRod fishingRod = player.getFishingRodByName(fishingRodName);
        if (fishingRod == null) {
            return new Result(false, "You do not have a " + fishingRodName + " fishing rod.");
        }

        if (player.isCloseToTileType(TileType.WATER)) {
            return new Result(false, "You must be near water to fish.");
        }

        double M;
        Weather currentWeather = App.getCurrentGame().getGameState().getCurrentWeather();
        if (currentWeather.equals(Weather.SUNNY)) {
            M = 1.5;
        } else if (currentWeather.equals(Weather.RAINY)) {
            M = 1.2;
        } else {
            M = 0.5;
        }

        Season currentSeason = App.getCurrentGame().getGameState().getTime().getSeason();

        int fishingSkillLevel = player.getSkillLevels().get(Skill.FISHING).getNumber();
        boolean canCatchLegendary = fishingSkillLevel == 4;

        int numberOfCaughtFish = (int) Math.ceil(Math.random() * M * (fishingSkillLevel + 2));
        HashMap<Item, Integer> itemsHashMap = player.getBackpack().getItems();
        HashMap<Fish, Integer> caughtFish = new HashMap<>();
        for (int i = 0; i < numberOfCaughtFish; i++) {
            FishType fishType = FishType.getRandomFishType(currentSeason, canCatchLegendary, fishingRod.getRodType());

            double poleNumber = fishingRod.getRodType().getQualityNumber();
            double qualityNumber = (Math.random() * (fishingSkillLevel + 2) * poleNumber) / (7 - M);
            Quality quality = Quality.getQualityByNumber(qualityNumber);

            Fish fish = new Fish(fishType, quality);
            itemsHashMap.put(fish, itemsHashMap.getOrDefault(fish, 0) + 1);
            caughtFish.put(fish, caughtFish.getOrDefault(fish, 0) + 1);
        }

        fishingRod.useTool(null, player);
        StringBuilder message = new StringBuilder("You caught ");
        for (Fish fish : caughtFish.keySet()) {
            message.append(fish.getType().getName()).append(" (x").append(caughtFish.get(fish)).append("), ");
        }
        return new Result(true, message.toString().replaceFirst(", $", "\n"));
    }

    // === ARTISAN === //

    public Result artisanUse(String artisanNameString, String itemNamesString) {
        ArtisanType artisanType = ArtisanType.getArtisanTypeByArtisanName(artisanNameString);

        if (artisanType == null) {
            return new Result(false, "Artisan not found.");
        }

        ArrayList<String> itemNames = new ArrayList<>(Arrays.asList(itemNamesString.split("\\s+")));
        ArrayList<ItemType> itemTypes = new ArrayList<>();
        for (String itemName : itemNames) {
            ItemType itemType = Item.getItemTypeByItemName(itemName);
            if (itemType == null) {
                return new Result(false, itemName + " is not a valid item.");
            }

            itemTypes.add(itemType);
        }

        User player = App.getLoggedIn();
        Artisan artisan = player.getFarm().getEmptyArtisanByArtisanType(artisanType);
        if (artisan == null) {
            return new Result(false, "Artisan not found.");
        }

        if (artisan.getItemPending() != null) {
            return new Result(false, "You either have no " + artisanNameString + "s or all of your " +
                artisanNameString + "s are already making another product.");
        }

        ProcessedItemType processedItemType = ProcessedItemType.getProcessedItemTypeByIngredients(itemTypes,
            artisanType);
        if (processedItemType == null) {
            return new Result(false,
                artisanNameString + " does not produce any items with these ingredients.");
        }

        for (ItemType itemType : itemTypes) {
            int quantity = processedItemType.getIngredients().get(itemType);
            Item item = Item.getItemByItemType(itemType);
            Result result = player.getBackpack().removeFromInventory(item, quantity);
            if (!result.success) {
                for (int i = 0; i < itemTypes.indexOf(itemType); i++) {
                    Item itemToRemove = Item.getItemByItemType(itemTypes.get(i));
                    player.getBackpack().addToInventory(itemToRemove,
                        processedItemType.getIngredients().get(itemTypes.get(i)));
                }
                return new Result(false, "You don't have enough ingredients.");
            }
        }

        int processingTime = processedItemType.getProcessingTime();
        artisan.setItemPending(Item.getItemByItemType(processedItemType));
        artisan.setTimeLeft(processingTime);

        return new Result(true, artisanNameString + " is making " + processedItemType.getName() + ". " +
            "Come back in " + processingTime + " hours to collect it.");
    }

    public Result artisanGet(String artisanName) {
        ArtisanType artisanType = ArtisanType.getArtisanTypeByArtisanName(artisanName);
        if (artisanType == null) {
            return new Result(false, "Artisan not found.");
        }

        User player = App.getLoggedIn();
        Artisan artisan = player.getFarm().getFullArtisanByArtisanType(artisanType);
        if (artisan == null) {
            return new Result(false, "You don't have a " + artisanName +
                " producing an item right now.");
        }

        Item item = artisan.getItemPending();
        if (artisan.getTimeLeft() > 0) {
            return new Result(false, item.getName() + " is not ready to collect yet. Come back in " +
                artisan.getTimeLeft() + " hours to collect it.");
        }

        Result result = player.getBackpack().addToInventory(item, 1);
        if (!result.success) {
            return new Result(false, "You don't have enough space in your backpack.");
        }
        artisan.setItemPending(null);
        artisan.setTimeLeft(-1);
        return new Result(true, "You collected a " + item.getName() + " from the " + artisanName + ".");
    }

    // === SHOPS === //

    public Result showAllProducts() {
        Shop shop = App.getCurrentShop();
        Result result = checkShopStatus(shop);
        if (!result.success) {
            return result;
        }

        StringBuilder productList = new StringBuilder("All Products in " + shop.getName() + ":\n");
        for (GoodsType product : GoodsType.values()) {
            if (product.getShopType().equals(shop.getType().getName())) {
                productList.append(String.format("  " + product.getName() + ": %.2fg\n", product.getPrice()));
            }
        }

        return new Result(true, productList.toString());
    }

    public Result showAvailableProducts() {
        Shop shop = App.getCurrentShop();
        Result result = checkShopStatus(shop);
        if (!result.success) {
            return result;
        }

        StringBuilder availableProducts = new StringBuilder("Available Products in " + shop.getType().getName() + ":\n");
        for (GoodsType product : GoodsType.values()) {
            if (product.getShopType().equals(shop.getType().getName())) {
                if (product.isAvailable()) {
                    availableProducts.append(String.format("- %s: %d gold\n", product.name(), product.getPrice()));
                }
            }
        }

        return new Result(true, availableProducts.toString());
    }

    public Result checkShopStatus(Shop shop) {
        if (shop == null) {
            return new Result(false, "Enter a shop first!");
        }

        if (!shop.isOpen()) {
            return new Result(false, shop.getName() + " is not open right now. Come back between " +
                shop.getType().getStartHour() + " and " + shop.getType().getEndHour() + " to browse the goods.");
        }

        return new Result(true, "");
    }

    public Result purchase(String productName, String countStr) {
        Shop shop = App.getCurrentShop();
        Result result = checkShopStatus(shop);
        if (!result.success) {
            return result;
        }

        int count;
        if (countStr == null) {
            count = 1;
        } else {
            count = Integer.parseInt(countStr);
        }

        Item product = Item.getItemByItemName(productName);
        if (product == null) {
            return new Result(false, "Product not found.");
        }

        if (!(product instanceof Good)) {
            return new Result(false, "Product not found.");
        }

        Good good = (Good) product;

        if (!good.getType().getShopType().equals(shop.getType().getName())) {
            return new Result(false, shop.getName() + " does not sell this product.");
        }

        if (!good.getType().isAvailable()) {
            return new Result(false, good.getName() + " is not available to purchase right now.");
        }

        int price = product.getPrice() * count;

        User player = App.getLoggedIn();
//        if (
//                good.getType() == GoodsType.IRIDIUM_ROD &&
//                        player.getSkillLevels().get(Skill.FISHING) != SkillLevel.LEVEL_FOUR
//        ) {
//            return new Result(false, "Your fishing skill must reach level 4 to buy an iridium rod.");
//        }
//        if (
//                good.getType() == GoodsType.FIBERGLASS_ROD &&
//                        player.getSkillLevels().get(Skill.FISHING) == SkillLevel.LEVEL_ONE
//        ) {
//            return new Result(false, "Your fishing skill must reach level 2 to buy a fiberglass rod.");
//        }
//        if (player.getBalance() < price) {
//            return new Result(false, "You don't have enough money.");
//        }

        int dailyLimit = good.getType().getDailyLimit();
        if (count > dailyLimit) {
            return new Result(false, "You can only buy " + good.getType().getDailyLimit() + " of " +
                productName + " in a day.");
        }

        Good shopGood = shop.getGoodByType(good.getType());
        int numberOfBought = shopGood.getNumberSoldToUsersToday();
        if (numberOfBought >= dailyLimit) {
            return new Result(false, "You have already bought " + numberOfBought + " of " + productName
                + " and can not buy any more.");
        }
        result = player.getBackpack().addToInventory(product, count);
        if (!result.success) {
            return new Result(false, "You don't have enough space in your backpack.");
        }

        player.changeBalance(-product.getPrice());
        shopGood.setNumberSoldToUsersToday(numberOfBought + count);
        return new Result(true, "You bought " + count + " of " + productName + ".");
    }

    public Result cheatAddDollars(String countStr) {
        int count = Integer.parseInt(countStr);
        User player = App.getLoggedIn();
        player.changeBalance(count);
        return new Result(true, "You have " + player.getBalance() + "g now.");
    }

    public Result sell(String productName, String countStr) {
        User player = App.getLoggedIn();
        Item item = player.getBackpack().getItemFromInventoryByName(productName);
        if (item == null) {
            return new Result(false, "Product not found.");
        }

        int numberInInventory = player.getBackpack().getItems().get(item);

        int count;
        if (countStr == null) {
            count = numberInInventory;
        } else {
            count = Integer.parseInt(countStr);
        }

        if (!item.isSellable()) {
            return new Result(false, "This product can not be sold.");
        }

        ShippingBin shippingBin = player.getCloseShippingBin();
        if (shippingBin == null) {
            return new Result(false, "You must be near a shipping bin to sell.");
        }

        Result result = player.getBackpack().addToInventory(item, count);
        if (!result.success) {
            return new Result(false, "You don't have enough " + productName + " to sell.");
        }

        for (int i = 0; i < count; i++) {
            shippingBin.addItemToShip(item);
        }

        int price = item.getPrice() * count;

        return new Result(true, "You put " + count + " of " + productName + " in the shipping bin. " +
            "You will get " + price + "g tomorrow.");
    }

    // === FRIENDSHIPS === //

    public Result showFriendshipLevels() {
        StringBuilder message = new StringBuilder("Your friendships with other players:\n");
        User player = App.getLoggedIn();
        Game game = App.getCurrentGame();
        for (User otherPlayer : game.getPlayers()) {
            if (!player.equals(otherPlayer)) {
                Friendship friendship = game.getUserFriendship(player, otherPlayer);
                message.append(otherPlayer.getUsername()).append(": \n").append("   Friendship level: ")
                    .append(friendship.getLevel().getNumber()).append("\n").append("   Xp: ")
                    .append(friendship.getCurrentXP()).append("\n");
            }
        }
        return new Result(true, message.toString());
    }

    public Result talk(String username, String message) {
        Game game = App.getCurrentGame();
        User targetPlayer = game.getPlayerByUsername(username);
        if (targetPlayer == null) {
            return new Result(false, "User not found.");
        }

        User player = App.getLoggedIn();
        if (areClose(player.getPosition(), targetPlayer.getPosition())) {
            game.sendMessage(player, targetPlayer, message);

            if (player.getSpouse() != null) {
                if (player.getSpouse().equals(targetPlayer) && player.hasInteractedToday(targetPlayer)) {
                    game.changeFriendship(player, targetPlayer, 50);
                }
            }

            if (!player.getHasTalkedToToday().get(targetPlayer)) {
                game.changeFriendship(player, targetPlayer, 20);
            }
            return new Result(true, "You sent a message to " + username +
                ". Your friendship level with them is now " +
                game.getUserFriendship(player, targetPlayer).toString());
        }
        return new Result(false, "You must stand next to " + username + " to talk to them.");
    }

    public Result showTalkHistoryWithUser(String username) {
        Game game = App.getCurrentGame();
        User targetPlayer = game.getPlayerByUsername(username);
        if (targetPlayer == null) {
            return new Result(false, "User not found.");
        }

        StringBuilder history = new StringBuilder("Talk history with " + username + ":\n");

        User player = App.getLoggedIn();
        HashMap<String, Boolean> sentMessages = game.getTalkHistory().get(player).get(targetPlayer);
        if (sentMessages != null) {
            for (String message : sentMessages.keySet()) {
                history.append("You: ").append(message).append("\n");
            }
        }

        HashMap<String, Boolean> receivedMessages = game.getTalkHistory().get(targetPlayer).get(player);
        if (receivedMessages != null) {
            for (String message : receivedMessages.keySet()) {
                history.append(username).append(": ").append(message).append("\n");
            }
        }

        return new Result(true, history.toString().trim());
    }

    public Result giveGift(String username, String itemName, String amountStr) {
        Game game = App.getCurrentGame();
        User targetPlayer = game.getPlayerByUsername(username);

        if (targetPlayer == null) {
            return new Result(false, "User not found.");
        }

        User player = App.getLoggedIn();
        if (!areClose(player.getPosition(), targetPlayer.getPosition())) {
            return new Result(false, "You must be close to " + username + " to give them a gift.");
        }

        Item item = player.getBackpack().getItemFromInventoryByName(itemName);
        if (item == null) {
            return new Result(false, "Item not found.");
        }

        int amount = Integer.parseInt(amountStr);
        Result result = player.getBackpack().removeFromInventory(item, amount);
        if (!result.success) {
            return result;
        }

        targetPlayer.getBackpack().addToInventory(item, amount);
        targetPlayer.addGift(item, amount, player);
        return new Result(true, "You gave " + itemName + " (x" + amount + ") to " + username + ". " +
            "Your friendship level will change after they rate it.");
    }

    public Result giftList() {
        User player = App.getLoggedIn();
        StringBuilder message = new StringBuilder("Your gifts:\n");
        for (Gift gift : player.getGifts()) {
            message.append("-----------------------\n").append(gift.getId()).append(". ")
                .append(gift.getItem().getName()).append(" (x").append(gift.getAmount()).append(") given by ")
                .append(gift.getGiver().getUsername()).append("\n");
            if (gift.getRating() == 0) {
                message.append("You have not rated this gift yet.");
            } else {
                message.append("Your rating: ").append(gift.getRating());
            }
        }
        return new Result(true, message.toString());
    }

    public Result giftRate(String giftNumberStr, String rateStr) {
        User player = App.getLoggedIn();
        int giftNumber = Integer.parseInt(giftNumberStr);
        Gift gift = player.getGiftById(giftNumber);
        if (gift == null) {
            return new Result(false, "Invalid gift number.");
        }

        int rate = Integer.parseInt(rateStr);
        if (rate < 1 || rate > 5) {
            return new Result(false, "Rating must be between 1 and 5.");
        }

        gift.setRating(rate);
        int xp = gift.calculateFriendshipXP();
        User giver = gift.getGiver();
        Game game = App.getCurrentGame();
        if (player.getSpouse().equals(giver)) {
            game.changeFriendship(player, giver, 50);
        }
        if (!player.exchangedGiftToday(giver)) {
            game.changeFriendship(player, giver, xp);
        }
        player.setExchangedGiftToday(giver, true);
        giver.setExchangedGiftToday(player, true);
        return new Result(true, "You rated the gift " + rate + "/5. Your friendship level with " +
            giver.getUsername() + " has changed by " + xp + " Xp and it is now " +
            game.getUserFriendship(player, giver).toString());
    }

    public Result showGiftHistory(String username) {
        Game game = App.getCurrentGame();
        User targetPlayer = game.getPlayerByUsername(username);
        if (targetPlayer == null) {
            return new Result(false, "User not found.");
        }

        User player = App.getLoggedIn();
        String message = "Gift history of you and " + username + "\n";

        message += "    Gifts from you to " + username + ":\n\n";
        message += getGiftHistoryString(targetPlayer, player);

        message += "------------------------------------------\n    Gifts from " + username + " to you:\n";
        message += getGiftHistoryString(player, targetPlayer);

        return new Result(true, message);
    }

    private String getGiftHistoryString(User receiver, User giver) {
        StringBuilder message = new StringBuilder();
        for (Gift gift : receiver.getGifts()) {
            if (gift.getGiver().equals(giver)) {
                message.append("    ----------------------\n" + "   Gift number: ").append(gift.getId()).append("\n")
                    .append("   ").append(gift.getItem().getName()).append(" (x").append(gift.getAmount())
                    .append(") \n    ");
                if (gift.getRating() == 0) {
                    message.append("Not rated yet");
                } else {
                    message.append("Rating: ").append(gift.getRating()).append("/5");
                }
            }
        }
        return message.toString();
    }

    public Result hug(String username) {
        Game game = App.getCurrentGame();
        User targetPlayer = game.getPlayerByUsername(username);
        if (targetPlayer == null) {
            return new Result(false, "User not found.");
        }

        User player = App.getLoggedIn();
        if (game.getUserFriendship(player, targetPlayer).getLevel().getNumber() >= 2) {
            if (player.getSpouse().equals(targetPlayer) && player.hasInteractedToday(targetPlayer)) {
                game.changeFriendship(player, targetPlayer, 50);
            }
            if (!player.getHasTalkedToToday().get(targetPlayer)) {
                game.changeFriendship(player, targetPlayer, 60);
            }
            player.setHasHuggedToday(targetPlayer, true);
            targetPlayer.setHasHuggedToday(player, true);
            return new Result(true, "You hugged " + username + ". Your friendship level is now " +
                game.getUserFriendship(player, targetPlayer).toString());
        }
        return new Result(false, "Your friendship level with " + username +
            " must be at least 2 to hug them.");
    }

    public Result giveFlowerToUser(String username, String flowerName) {
        Game game = App.getCurrentGame();
        User targetPlayer = game.getPlayerByUsername(username);
        if (targetPlayer == null) {
            return new Result(false, "User not found.");
        }

        FlowerType flowerType = FlowerType.getFlowerTypeByName(flowerName);
        if (flowerType == null) {
            return new Result(false, "Flower not found.");
        }

        User player = App.getLoggedIn();
        if (!areClose(player.getPosition(), targetPlayer.getPosition())) {
            return new Result(false, "You must be standing next to " + username +
                " to give them a flower");
        }

        CropType cropType = CropType.getCropTypeByName(flowerName);
        assert cropType != null;
        Crop flower = new Crop(cropType);
        Result result = player.getBackpack().removeFromInventory(flower, 1);
        if (!result.success) {
            return result;
        }

        result = targetPlayer.getBackpack().addToInventory(flower, 1);
        if (!result.success) {
            return result;
        }

        if (player.getSpouse().equals(targetPlayer) && player.hasInteractedToday(targetPlayer)) {
            game.changeFriendship(player, targetPlayer, 50);
        }
        if (game.getUserFriendship(player, targetPlayer).getLevel().equals(FriendshipLevel.CLOSE_FRIEND)
            && !player.hasHuggedToday(targetPlayer)) {
            game.changeFriendshipLevel(player, targetPlayer, FriendshipLevel.BEST_FRIEND);
        }
        player.setHasHuggedToday(targetPlayer, true);
        targetPlayer.setHasHuggedToday(player, true);
        return new Result(true, "You gave " + username + " a " + flowerName +
            ". Your friendship level is now " + game.getUserFriendship(player, targetPlayer).toString() + ".");
    }

    public Result askMarriage(String username, String ringStr) {
        Game game = App.getCurrentGame();
        User targetPlayer = game.getPlayerByUsername(username);
        if (targetPlayer == null) {
            return new Result(false, "User not found.");
        }

        User player = App.getLoggedIn();
        if (areClose(player.getPosition(), targetPlayer.getPosition())) {
            if (!player.getGender().equals(targetPlayer.getGender())) {
                HashMap<Item, Integer> items = player.getBackpack().getItems();
                if (items.get(new Good(GoodsType.WEDDING_RING)) == 0) {
                    return new Result(false, "You don't have a ring to propose with.");
                }
                targetPlayer.addMarriageRequests(player);
                return new Result(true, "You proposed to " + username + ". Wait for their response.");
            }
            return new Result(false,
                "You are not allowed to marry a person of the same gender in this game.");
        }
        return new Result(false, "You must stand next to " + username + " to propose to them.");
    }

    public Result respondToMarriageRequest(String acceptanceStr, String username) {
        Game game = App.getCurrentGame();
        boolean hasAccepted = acceptanceStr.equalsIgnoreCase("accept");

        User targetUser = game.getPlayerByUsername(username);
        if (targetUser == null) {
            return new Result(false, "User not found.");
        }

        User player = App.getLoggedIn();
        if (!player.getMarriageRequests().contains(targetUser)) {
            return new Result(false, username + " has not proposed to you. If you want to marry them, "
                + "enter this command: \"ask marriage -u " + username + " -r Wedding ring\"");
        }

        if (!hasAccepted) {
            targetUser.setDepressed(true);
            game.changeFriendshipLevel(player, targetUser, FriendshipLevel.STRANGER);
            return new Result(false, "Marriage request denied. Your friendship level with " + username +
                " is now 0. " + username + " is now depressed.");
        }

        player.setSpouse(targetUser);
        targetUser.setSpouse(player);
        return new Result(true, "Congratulations! You are now married to " + username);
    }

    // === TRADE === //

    public Result startTrade() {
        App.setCurrentMenu(Menu.TRADE_MENU);

        User player = App.getLoggedIn();
        StringBuilder message = new StringBuilder("You are now in Trade Menu.\nNew Trade requests and offers:\n");
        for (Trade trade : App.getCurrentGame().getTrades()) {
            if (!trade.getCreator().equals(player) &&
                (trade.getRequester().equals(player) || trade.getOfferer().equals(player))) {
                message.append(trade.toString());
                trade.setSeen(true);
            }
        }
        return new Result(true, message.toString());
    }

    // === NPC === //

    public Result walkInVillage(String xStr, String yStr) {
        try {
            int x = Integer.parseInt(xStr);
            int y = Integer.parseInt(yStr);
            NPCVillage village = App.getCurrentGame().getVillage();
            Position pos = new Position(x, y);

            if (!village.isPositionValid(pos)) {
                return new Result(false, "Position (" + x + "," + y + ") is out of bounds.");
            }

            Tile tile = village.getTileByPosition(pos);

            if (tile == null) {
                return new Result(false, "No tile found at (" + x + "," + y + ").");
            }

            if (tile.getType() == TileType.SHOP) {
                Shop shop = village.getShopAtPosition(pos);

                if (shop != null) {
                    App.setCurrentShop(shop);
                    App.getLoggedIn().setPosition(pos);
                    return new Result(true, "You have entered " + shop.getType() + " at (" + x + "," + y + ").");
                } else {
                    return new Result(false, "This shop's type could not be determined.");
                }
            } else {
                App.setCurrentShop(null);
            }

            return new Result(false, "This tile is not a shop.");

        } catch (NumberFormatException e) {
            return new Result(false, "Invalid input! Coordinates must be numeric values.");
        }
    }


    public Result goToVillage() {
        Game currentGame = App.getCurrentGame();
        User player = App.getLoggedIn();

        if (currentGame.isInNPCVillage()) {
            return new Result(false, "You are already in the village!");
        }

        Position villageEntrance = new Position(1, 1);
        player.setPosition(villageEntrance);

        currentGame.setInNPCVillage(true);

        return new Result(true, "You have entered the village. Welcome!");
    }

    public Result exitVillage() {
        Game currentGame = App.getCurrentGame();
        User player = App.getLoggedIn();

        if (!currentGame.isInNPCVillage()) {
            return new Result(false, "You are not in the village!");
        }
        Position villageExit = new Position(1, 1); // Example position
        player.setPosition(villageExit);
        currentGame.setInNPCVillage(false);

        return new Result(true, "You have left the village and returned to your farm.");
    }

    public Result meetNPC(String NCPName) {
        Game game = App.getCurrentGame();
        NPC npc = game.getNPCByName(NCPName);
        if (npc == null) {
            return new Result(false, "NPC not found.");
        }

        User player = App.getLoggedIn();
        if (areClose(player.getPosition(), npc.getPosition())) {
            int timeOfDay = game.getGameState().getTime().getHour();
            Season season = game.getGameState().getTime().getSeason();
            Weather weather = game.getGameState().getCurrentWeather();
            int friendshipLevel = game.getNpcFriendshipPoints(player, npc);
            Dialog dialog =
                Dialog.getDialogBySituation(npc.getType(), timeOfDay, season, weather, friendshipLevel);
            if (dialog == null) {
                return new Result(false, NCPName + " doesn't have anything to say right now.");
            }

            if (!npc.hasTalkedToToday(player)) {
                game.changeFriendship(player, npc, 20);
            }
            return new Result(true, NCPName + ": \"" + dialog.getMessage() + "\"");
        }
        return new Result(false, "You must stand next to " + NCPName + " to talk to them.");
    }

    public Result giftNPC(String NCPName, String itemName) {
        Game game = App.getCurrentGame();
        NPC npc = game.getNPCByName(NCPName);
        if (npc == null) {
            return new Result(false, "Npc not found.");
        }

        if (ToolType.getToolTypeByName(itemName) != null) {
            return new Result(false, "You can't gift a tool to a NPC.");
        }

        User player = App.getLoggedIn();
        Item item = player.getBackpack().getItemFromInventoryByName(itemName);
        if (item == null) {
            return new Result(false, "Item not found.");
        }

        Result result = player.getBackpack().removeFromInventory(item, 1);
        if (!result.success) {
            return result;
        }

        if (!npc.hasReceivedGiftToday(player)) {
            if (npc.isFavourite(item.getName())) {
                game.changeFriendship(player, npc, 200);
            } else {
                game.changeFriendship(player, npc, 50);
            }
        }
        npc.setReceivedGiftToday(player, true);
        return new Result(true, "You gave a " + itemName + " to " + NCPName +
            ". Your friendship points with them is now " + game.getNpcFriendshipPoints(player, npc) + ".");
    }

    public Result showFriendshipNPCList() {
        User player = App.getLoggedIn();
        StringBuilder message = new StringBuilder("Your friendships with NPCs:\n");
        for (NPC npc : App.getCurrentGame().getNpcs()) {
            int friendshipPoints = App.getCurrentGame().getNpcFriendshipPoints(player, npc);
            if (friendshipPoints > 0) {
                message.append(npc.getName()).append(": \n" +
                    "   Friendship level: ").append(friendshipPoints / 200).append("\n" +
                    "   Friendship points: ").append(friendshipPoints).append("\n-------------------------------\n");

            }
        }
        return new Result(true, message.toString());
    }

    public Result showQuestsList() {
        Game game = App.getCurrentGame();
        StringBuilder message = new StringBuilder("NPC quests:\n");
        for (Quest quest : game.getQuests()) message.append(quest.toString()).append("\n");
        return new Result(true, message.toString());
    }

    public Result finishQuest(String indexStr) {
        int index = Integer.parseInt(indexStr);
        if (index > 15) {
            return new Result(false, "Quest not found");
        }

        Quest quest = App.getCurrentGame().getQuestById(index);

        if (!quest.isUnlocked(App.getLoggedIn())) {
            return new Result(false, "Quest is not unlocked for you");
        }

        ItemType requestItemType = quest.getRequest();
        Item requestItem = Item.getItemByItemType(requestItemType);
        int requestQuantity = quest.getRequestQuantity();

        if (quest.isFinished()) {
            return new Result(false, "This quest is already finished.");
        }

        User player = App.getLoggedIn();
        ItemType rewardItemType = quest.getReward();
        int rewardQuantity = quest.getRewardQuantity();
        String rewardName;
        if (rewardItemType == null) {
            App.getCurrentGame().changeFriendship(player, quest.getNpc(), rewardQuantity);
            rewardName = "Friendship Level";
        } else {
            Item rewardItem = Item.getItemByItemType(rewardItemType);
            rewardName = rewardItem.getName();
            Result rewardResult = player.getBackpack().addToInventory(rewardItem, rewardQuantity);
            if (!rewardResult.success) {
                return new Result(false,
                    "You do not have enough space in your inventory for this quest's reward.");
            }
        }

        if (requestItemType instanceof MoneyType) {
            player.changeBalance(rewardQuantity);
        } else {
            Result requestResult = player.getBackpack().removeFromInventory(requestItem, requestQuantity);
            if (!requestResult.success) {
                return new Result(false, "You don't have enough of " + requestItemType.getName() +
                    " to finish quest.");
            }
        }

        // if (index == 1) npc.startThirdQuestCountdown(player); TODO: start third quest countdown after unlocking quest 2

        quest.finish(player);
        assert false;
        return new Result(true, "You finished quest number " + index + ". " + quest.getNpc().getName() +
            " gave you " + rewardQuantity + " of " + rewardName + " as reward.");
    }

    public Result exitGame() {
        Game game = App.getCurrentGame();
        User player = App.getLoggedIn();
        if (!game.getPlayers().get(0).equals(player)) {
            return new Result(false, "Only the creator of the game can exit it.");
        }
        App.setCurrentMenu(Menu.PRE_GAME_MENU);
        return new Result(true, "Exiting Game ... Heading to Pre-Game Menu");
    }
}
