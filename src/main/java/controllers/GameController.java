package controllers;

import models.*;
import models.enums.*;
import models.enums.types.*;
import models.farming.*;
import models.inventory.*;
import models.tools.*;
import models.enums.environment.*;
import models.trade.*;

import java.util.*;

import static models.Greenhouse.canBuildGreenhouse;
import static models.Position.areClose;

public class GameController {

    // === PLAYER'S STATUS === //

    public Result nextTurn() {
        Game game = App.getCurrentGame();
        game.nextTurn(App.getLoggedIn());
        return new Result(true, "Next turn!\nBye-bye " + App.getLoggedIn().getNickname() + ".");
    }

    public Result showPlayerEnergy() {
        User player = App.getLoggedIn();
        int playerEnergy = player.getEnergy();
        return new Result(true, "Your energy is: " + playerEnergy);
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

    public Result faint() {
        User player = App.getLoggedIn();
        player.faint();
        return new Result(true, ""); // todo: appropriate message (next turn? or wake up in cabin?)
    }

    public Result showCurrentTool() {
        User player = App.getLoggedIn();
        Tool playerCurrentTool = player.getCurrentTool();
        return new Result(true, "Your tool is: " + playerCurrentTool.toString());
    }

    public Result showAvailableTools() {
        User player = App.getLoggedIn();
        StringBuilder sb = new StringBuilder();
        for (Item item : player.getBackpack().getItems().keySet()) {
            if (item instanceof Tool tool) {
                sb
                        .append(tool.getName()).append(": ").append(tool.getToolMaterial())
                        .append("! Related Skill: ").append(tool.getRelatedSkill()).append("\n")
                ;
            }
        }
        return new Result(true, sb.toString());
    }

    public Result upgradeTools(String toolsName) {
        if (App.getCurrentShop().getType() != ShopType.BLACKSMITH) {
            return new Result(false, "You can only ask the blacksmith to upgrade your tools. Pay him a visit!");
        }
        Tool tool = (Tool) Item.getItemByItemName(toolsName);
        if (tool == null) {
            return new Result(false, "You do not have any tools with that name! Use: \n" + ToolType.getFullList());
        }
        // TODO: check the skills and budget...
        // TODO: remove the previous tool and add the upgraded one
        if (tool.getToolType() == ToolType.FISHING_ROD) {
            return new Result(true, "Your Fishing Rods upgraded to " + ((FishingRod) tool).getRodType() + ".");
        }
        return new Result(true, toolsName + " has been upgraded to " + tool.getToolMaterial() + ".");
    }

    public Result showLearntCookingRecipes() {
        User player = App.getLoggedIn();
        String learntRecipes = player.getStringLearntCookingRecipes();
        return new Result(true, learntRecipes);
    }

    public Result showLearntCraftRecipes() {
        User player = App.getLoggedIn();
        String learntRecipes = player.getStringLearntCraftRecipes();
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
        return new Result(true, showTime().message() + "\n" + showDate().message());
    }

    public Result showDayOfTheWeek() {
        return new Result(true,
                "Day of the week: " + App.getCurrentGame().getGameState().getTime().getWeekday().getName());
    }

    public Result cheatAdvanceTime(String hourIncreaseStr) {
        int hourIncrease = Integer.parseInt(hourIncreaseStr);
        Time.cheatAdvanceTime(hourIncrease, App.getCurrentGame());
        return new Result(true, "Time increased by " + hourIncrease + " hours.");
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

    public Result equipTool(String toolName) {
        User player = App.getLoggedIn();
        ToolType toolType = ToolType.getToolTypeByName(toolName);
        if (toolType == null) {
            String notFoundMessage = "Tool not found.\n" + "Enter a valid tool name: \n" + ToolType.getFullList();
            return new Result(false, notFoundMessage);
        }
        Item itemFromInventory = player.getBackpack().getItemFromInventoryByName(toolName);
        if (itemFromInventory == null) {
            return new Result(false, "Tool not found in inventory.");
        }
        player.setCurrentTool((Tool) itemFromInventory);
        return new Result(true, toolName + " equipped.");
    }

    public Result useTool(String directionString) {
        User player = App.getLoggedIn();
        Direction direction = Direction.getDirectionByDisplayName(directionString);
        Tile tile = neighborTile(direction);
        Tool tool = player.getCurrentTool();
        String failingMessage = "You can't use" + tool.toString() + " here on your " + directionString + ".";
        if (tile == null) {
            return new Result(false, failingMessage);
        }
        if (tool == null) {
            return new Result(false, "You should equip tool first.");
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
        Item item = Item.getItemByItemType(itemType);
        CraftType craftType = CraftType.getCraftByName(itemName);
        if (craftType == null) {
            return new Result(false, "Item not found.");
        }
        if (!canCraftResult((CraftType) itemType).success()) {
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

    public Result showCraftInfo(String craftName) {
        // TODO: tf is it? page 33 ... vegetables?
        return new Result(true, "");
    }

    public Result cheatAddItem(String itemName, String numberStr) {
        User player = App.getLoggedIn();
        Item item = Item.getItemByItemName(itemName);
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
        Boolean isPut = switch (putOrPick.toLowerCase()) {
            case "put" -> true;
            case "pick" -> false;
            default -> null;
        };
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
        if (getTileByPosition(player.getPosition()).getType() != TileType.CABIN) {
            return new Result(false, "You can cook inside your cabin only.");
        }

        CookingRecipe cookingRecipe = CookingRecipe.getCookingRecipe(foodName);
        if (cookingRecipe == null) {
            return new Result(false, "Food not found.");
        }

        if (!canCookResult(cookingRecipe).success()) {
            return new Result(false, canCookResult(cookingRecipe).message());
        }

        Backpack backpack = player.getBackpack();
        Refrigerator homeRefrigerator = player.getFarm().getCabin().getRefrigerator();
        Result result = removeIngredientsFromInventories(
                homeRefrigerator,
                backpack,
                cookingRecipe.getFoodType().getIngredients());
        homeRefrigerator.addToInventory(new Food(cookingRecipe), 1);
        return new Result(true, "Yummy! Your fresh " + foodName + " added to the refrigerator.");
    }

    private Result canCookResult(CookingRecipe givenRecipe) {
        User player = App.getLoggedIn();
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
            if (!refrigerator.getItems().containsKey(ingredientItem) && !backpack.getItems().containsKey(ingredientItem)) {
                return new Result(false, "You don't have " + ingredientItem.getName() + " at all.");
            }
            if (refrigerator.getItems().get(ingredientItem) + backpack.getItems().get(ingredientItem) < requiredAmount) {
                return new Result(false, "You don't have enough " + ingredientItem.getName() + ".");
            }
            enoughItemInRefrigerator.put(ingredientItem, refrigerator.getItems().get(ingredientItem) >= requiredAmount);
        }
        for (ItemType itemType : neededIngredients.keySet()) {
            Item ingredientItem = Item.getItemByItemType(itemType);
            int requiredAmount = neededIngredients.get(itemType);
            if (enoughItemInRefrigerator.get(ingredientItem)) {
                refrigerator.removeFromInventory(ingredientItem, requiredAmount);
            } else {
                int neededAmountFromBackpack = requiredAmount - refrigerator.getItems().get(ingredientItem);
                refrigerator.removeFromInventory(ingredientItem, null);
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
                getTileByPosition(player.getPosition()).getType() != TileType.CABIN) {
            return new Result(
                    false,
                    "You don't have a " + food.getName() + " in your backpack, but you have one back in your cabin.\n" +
                            "Go there and enjoy your " + food.getName() + "!"
            );
        }
        player.increaseEnergyBy(foodType.getEnergy());
        FoodBuff buff = foodType.getBuff();
        // TODO: apply buff
        String message = "Bon appétit!\nYour " + food.getName() + " had these effects:\n" +
                "\tyour energy increased by " + foodType.getEnergy() + "\n" + "\tbuff: " + buff.getBuffDisplayName();
        return new Result(true, message);
    }

    private Result canCraftResult(CraftType craftType) {
        CraftRecipe recipe = new CraftRecipe(craftType);
        User player = App.getLoggedIn();
        if (player.getBackpack().getCapacity() <= player.getBackpack().getItems().size()) {
            return new Result(false, "Your backpack is full.");
        } else if (player.getLearntCraftRecipes() == null ||
                player.getLearntCraftRecipes().isEmpty() ||
                !player.getLearntCraftRecipes().contains(recipe)) {
            return new Result(false, "You should learn the recipe first.");
        }
        HashMap<IngredientType, Integer> neededIngredients = craftType.getIngredients();
        for (Map.Entry<IngredientType, Integer> entry : neededIngredients.entrySet()) {
            IngredientType ingredientType = entry.getKey();
            int requiredAmount = entry.getValue();

            if (!hasEnoughOfThatItem(Item.getItemByItemType(ingredientType), requiredAmount, player.getBackpack())) {
                return new Result(false, "You don't have enough " + ingredientType.getName());
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
                    tileType == TileType.WOOD_LOG;
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
        return getTileByPosition(newPosition);
    }

    public static Tile getTileByPosition(Position position) {
        for (Tile tile : GameMap.getAllTiles()) {
            if (tile.getPosition().equals(position)) {
                return tile;
            }
        }
        return null;
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
            if (!isPositionValid(destination)) {
                return new Result(false, "Coordinates (" + targetX + "," + targetY + ") are out of bounds.");
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
        Boolean confirmed = switch (yOrN.toLowerCase()) {
            case "y", "yes" -> true;
            case "n", "no" -> false;
            default -> null;
        };
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
        player.changePosition(new Position(x, y));
        return new Result(true, "You are now at " + player.getPosition());
    }

    private boolean isPositionValid(Position pos) {
        List<Tile> allTiles = GameMap.getAllTiles();
        if (allTiles.isEmpty()) return false;

        int maxX = allTiles.stream().mapToInt(t -> t.getPosition().getX()).max().orElse(0);
        int maxY = allTiles.stream().mapToInt(t -> t.getPosition().getY()).max().orElse(0);

        return pos.getX() >= 0 && pos.getX() <= maxX &&
                pos.getY() >= 0 && pos.getY() <= maxY;
    }

    // === PRINT MAP === //

    public Result printMap(String xString, String yString, String sizeString) {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        int size = Integer.parseInt(sizeString);

        if (!isPositionValid(new Position(x, y))) {
            return new Result(false, "Coordinates (" + x + "," + y + ") are out of bounds.");
        }

        StringBuilder mapRepresentation = new StringBuilder();

        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                Tile tile = getTileByPosition(new Position(i, j));
                mapRepresentation.append(getTileSymbol(tile)).append(" ");
            }
            mapRepresentation.append("\n");
        }

        return new Result(true, mapRepresentation.toString());
    }

    private String getTileSymbol(Tile tile) {
        if (tile == null) return "⬜";

        switch (tile.getType()) {
            case TREE: return "🌳";
            case WATER: return "🌊";
            case CABIN: return "🏠";
            case STONE: return "🪨";
            case GREENHOUSE: return "🪟";
            case QUARRY_GROUND: return "⛰️";
            case WOOD_LOG: return "🪵";
            case GROWING_CROP: return "🌱";
            case ANIMAL: return "🐄";
            case PLOWED_SOIL: return "🟤";
            case NOT_PLOWED_SOIL: return "🟫";
            case PLANTED_SEED: return "🌾";
            case WATERED_NOT_PLOWED_SOIL: return "💧";
            case WATERED_PLOWED_SOIL: return "💦";
            case GRASS: return "⸙";
            case UNDER_AN_ITEM: return "📦";
            case SHOP: return "🏪";
            default: return "❓";
        }
    }

    public Result showHelpReadingMap() {
        String helpText = """
        === Map Symbols Legend ===
        🌳 - Tree
        🌊 - Water
        🏠 - Cabin
        🪨 - Stone
        🪟 - Greenhouse
        ⛰️ - Quarry Ground
        🪵 - Wood Log
        🌱 - Growing Crop
        🐄 - Animal
        🟤 - Plowed Soil
        🟫 - Not Plowed Soil
        🌾 - Planted Seed
        💧 - Watered Not Plowed Soil
        💦 - Watered Plowed Soil
        ⸙ - Grass
        📦 - Item
        🏪 - Shop
        ⬜ - Empty Space
        """;
        return new Result(true, helpText);
    }

    // === GAME STATUS === //

    public Result cheatThor(String xString, String yString) {
        Position position = new Position(Integer.parseInt(xString), Integer.parseInt(yString));
        Tile tile = getTileByPosition(position);

        if (tile != null) {
            tile.setType(TileType.NOT_PLOWED_SOIL); // TODO: is it good?
            App.getCurrentGame().getGameState().triggerLightningStrike();
            return new Result(true, "Thor has struck (" + xString + ", " + yString + ")!");
        }
        return new Result(false, "Invalid position for Thor's strike.");
    }


    public Result showWeather() {
        return new Result(true, "Current weather: " +
                App.getCurrentGame().getGameState().getCurrentWeather().name());
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

        App.getLoggedIn();
        Greenhouse greenhouse = generateGreenhouse(10, 10, 5, 5);

        return new Result(true, "Greenhouse built successfully! You can now enter and use it.");
    }


    // === PLANTS === //

    public Result plant(String seedName, String directionName) {
        SeedType seedType = SeedType.getSeedByName(seedName);
        Direction direction = Direction.getDirectionByDisplayName(directionName);
        Tile tile = neighborTile(direction);
        if (tile.getType().equals(TileType.NOT_PLOWED_SOIL)) {
            return new Result(false, "You must plow the ground first! Use hoe.");
        }
        tile.setType(TileType.PLANTED_SEED);
        tile.pLaceItemOnTile(new PlantSource(seedType));
        return new Result(true, seedName + " planted in position: " + tile.getPosition().toString());
    }

    public Result showPlant(String xString, String yString) {
        Position position = new Position(Integer.parseInt(xString), Integer.parseInt(yString));
        Tile tile = getTileByPosition(position);
        if (tile.getType() != TileType.PLANTED_SEED &&
                tile.getType() != TileType.GROWING_CROP &&
                tile.getType() != TileType.GREENHOUSE) {
            return new Result(false, "No plants in this tile.");
        }
        // Todo: plant's info, including:
        //  name, time left till being harvestable, current stage, wateredTodayOrNot, Quality, FertilizedOrNot
        return new Result(true, "");
    }

    public Result fertilize(String fertilizerName, String directionName) {
        // TODO : get FertilizerType from its name
        Direction direction = Direction.getDirectionByDisplayName(directionName);
        // TODO: fertilize (should we have another tileType ??
        //  FERTILIZED_GROWING_CROP,
        //  FERTILIZED_PLANTED_SEED,
        //  FERTILIZED_TREE,
        //  FERTILIZED_PLOWED_SOIL, ... ?
        return new Result(true, "");
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

        FarmBuildingType farmBuildingType = FarmBuildingType.getFarmBuildingTypeByName(farmBuildingTypeStr);
        if (farmBuildingType == null) {
            return new Result(false, "Enter a valid building name.");
        }

        Position position = Position.getPositionByStrings(xString, yString);
        if (position == null) {
            return new Result(false, "Enter two valid numbers for x and y.");
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
        int woodInInventory = items.get(wood);
        int stoneInInventory = items.get(stone);
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
        Shop shop = App.getCurrentShop();
        if (shop == null) {
            return new Result(false, "You Must first enter Marnie's Ranch.");
        }

        if (!shop.getType().equals(ShopType.MARNIE_RANCH)) {
            return new Result(false, "You Must first enter Marnie's Ranch.");
        }

        AnimalType animalType = AnimalType.getAnimalTypeByName(animalTypeStr);
        if (animalType == null) {
            return new Result(false, "Animal not found.");
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
        Position newPosition = Position.getPositionByStrings(xString, yString);
        if (newPosition == null) {
            return new Result(false, "Enter two valid numbers for x and y.");
        }

        User player = App.getLoggedIn();
        Farm farm = player.getFarm();
        Animal animal = farm.getAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "Animal not found.");
        }

        FarmBuilding farmBuildingInNewPosition = farm.getFarmBuildingByPosition(newPosition);
        if (animal.isOutside()) {
            if (animal.getPosition().equals(newPosition)) {
                return new Result(false, "Your " + animal.getAnimalType().getName() + ", " + animalName
                        + ", is already at " + newPosition.toString());
            }

            if (!farm.getTileByPosition(newPosition).getType().equals(TileType.GRASS)) {
                return new Result(false, "Your animal can only go on grass.");
            }

            if (farmBuildingInNewPosition != null) {
                if (!farmBuildingInNewPosition.equals(animal.getAnimalLivingSpace())) {
                    return new Result(false, "Your animal can only go on grass.");
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
                + ", has been moved to " + newPosition.toString() + " and is now outside. Its' friendship level is now "
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

    public Result collectProducts(String animalName) {
        User player = App.getLoggedIn();
        Farm farm = player.getFarm();
        Animal animal = farm.getAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "Animal not found.");
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
                player.getBackpack().getItems().put(item, itemsHashMap.getOrDefault(item, 0) + 1);
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
            if (!result.success()) {
                for (int i = 0; i < itemTypes.indexOf(itemType); i++) {
                    Item itemToRemove = Item.getItemByItemType(itemTypes.get(i));
                    player.getBackpack().addToInventory(itemToRemove,
                            processedItemType.getIngredients().get(itemTypes.get(i)));
                }
                return new Result(false, "You don't have enough " + itemType.getName());
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
        if (!result.success()) {
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
        if (!result.success()) {
            return result;
        }

        StringBuilder productList = new StringBuilder("All Products in " + shop.getName() + ":\n");
        for (GoodsType product : GoodsType.values()) {
            if (product.getShopType() == shop.getType()) {
                productList.append(String.format("  " + product.getName() + ": %.2fg\n", product.getPrice()));
            }
        }

        return new Result(true, productList.toString());
    }

    public Result showAvailableProducts() {
        Shop shop = App.getCurrentShop();
        Result result = checkShopStatus(shop);
        if (!result.success()) {
            return result;
        }

        StringBuilder availableProducts = new StringBuilder("Available Products in " + shop.getType().getName() + ":\n");
        for (GoodsType product : GoodsType.values()) {
            if (product.getShopType() == shop.getType()) {
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
        if (!result.success()) {
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

        if (!(product instanceof Good good)) {
            return new Result(false, "Product not found.");
        }

        if (!good.getType().getShopType().equals(shop.getType())) {
            return new Result(false, shop.getName() + " does not sell this product.");
        }

        if (!good.getType().isAvailable()) {
            return new Result(false, good.getName() + " is not available to purchase right now.");
        }

        int price = product.getPrice() * count;

        User player = App.getLoggedIn();
        if (player.getBalance() < price) {
            return new Result(false, "You don't have enough money.");
        }

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
        if (!result.success()) {
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
        if (!result.success()) {
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
        if (!result.success()) {
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
        Crop flower = new Crop(cropType);
        Result result = player.getBackpack().removeFromInventory(flower, 1);
        if (!result.success()) {
            return result;
        }

        result = targetPlayer.getBackpack().addToInventory(flower, 1);
        if (!result.success()) {
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
        App.setCurrentMenu(Menu.TARDE_MENU);

        User player = App.getLoggedIn();
        StringBuilder message = new StringBuilder("You are now in Trade Menu.\n\nNew Trade requests and offers:");
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
        if (!result.success()) {
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
                        "   Friendship points: ").append(friendshipPoints).append("""
                        
                        -------------------------------
                        """);
            }
        }
        return new Result(true, message.toString());
    }

    public Result showQuestsList() {
        Game game = App.getCurrentGame();
        User player = App.getLoggedIn();
        StringBuilder message = new StringBuilder("NPC quests:\n");
        for (NPC npc : game.getNpcs()) {
            int friendshipLevel = game.getNpcFriendshipPoints(player, npc) / 200;
            message.append("-----------------------\n")
                    .append(npc.getName()).append(" (").append(friendshipLevel).append("):\n");
            if (npc.getQuests().isEmpty()) {
                message.append("No quests.\n");
            } else {
                for (int i = 0; i < npc.getQuests().size(); i++) {
                    if (game.isQuestUnlocked(npc, player, i)) {
                        message.append(NPCType.questString(i, npc.getType(), friendshipLevel)).append("\n\n");
                    }
                }
            }
        }
        return new Result(true, message.toString());
    }

    public Result finishQuest(String npcName, String indexStr) {
        Game game = App.getCurrentGame();
        NPC npc = game.getNPCByName(npcName);
        if (npc == null) {
            return new Result(false, "Npc not found.");
        }

        int index = Integer.parseInt(indexStr) - 1;
        if (index > npc.getQuests().size()) {
            return new Result(false, "Quest not found");
        }

        ItemType itemType = npc.getType().getRequestItemType(index);
        Item item = Item.getItemByItemType(itemType);
        int requestQuantity = npc.getType().getRequestQuantity(index);

        if (npc.isQuestFinished(index)) {
            return new Result(false, "This quest is already finished.");
        }

        User player = App.getLoggedIn();
        Item rewardItem = Item.getItemByItemType(npc.getType().getRewardItemType(index));
        int rewardQuantity = npc.getType().getRewardQuantity(index);
        Result rewardResult = player.getBackpack().addToInventory(rewardItem, rewardQuantity);
        if (!rewardResult.success()) {
            return new Result(false,
                    "You do not have enough space in your inventory for this quest's reward.");
        }

        Result requestResult = player.getBackpack().removeFromInventory(item, requestQuantity);
        if (!requestResult.success()) {
            assert itemType != null;
            return new Result(false, "You don't have enough of " + itemType.getName() +
                    " to finish quest.");
        }

        if (index == 1) {
            npc.startThirdQuestCountdown(player);
        }

        npc.setQuestFinished(true, index);
        assert rewardItem != null;
        return new Result(true, "You finished quest number " + index + " of " + npcName +
                ". They gave you " + rewardQuantity + " of " + rewardItem.getName() + " as reward.");
    }

    public Result exitGame() {
        Game game = App.getCurrentGame();
        User player = App.getLoggedIn();
        if (!game.getPlayers().getFirst().equals(player)) {
            return new Result(false, "Only the creator of the game can exit it.");
        }
        App.setCurrentMenu(Menu.PRE_GAME_MENU);
        return new Result(true, "Exiting Game ... Heading to Pre-Game Menu");
    }
}