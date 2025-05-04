package controllers;

import models.*;
import models.enums.Menu;
import models.enums.Quality;
import models.enums.Skill;
import models.enums.types.*;
import models.enums.types.FarmBuildingType;
import models.tools.FishingRod;
import models.tools.MilkPail;
import models.tools.Shear;
import models.tools.Tool;
import models.enums.environment.*;
import models.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameController {
    User player = App.getLoggedIn();
    Game game = App.getCurrentGame();
    Shop shop = App.getCurrentShop();
    ;

    // === PLAYER'S STATUS === //

    public Result showPlayerEnergy() {
        int playerEnergy = player.getEnergy();
        return new Result(true, "Your energy is: " + playerEnergy);
    }

    public Result setPlayerEnergy(String energyAmountStr) {
        int energyAmount = Integer.parseInt(energyAmountStr);

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

    // === TIME === //
    public Result cheatAdvanceTime(String hourIncreaseStr) {
        int hourIncrease = Integer.parseInt(hourIncreaseStr);
        Time.cheatAdvanceTime(hourIncrease, App.getCurrentGame().getGameState().getTime());
        return new Result(true, "");
    }

    public Result cheatAdvanceDate(String dayIncreaseStr) {
        int hourIncrease = Integer.parseInt(dayIncreaseStr);
        Time.cheatAdvanceDate(hourIncrease, App.getCurrentGame().getGameState().getTime());
        return new Result(true, "");
    }

    // === INVENTORY === //
    public Result inventoryShow() {
        // TODO
        return new Result(true, "");
    }

    public Result throwItemToTrash(String itemName, String numberStr) {
        // command: inventory trash ...
        // TODO: get Item from name
        // TODO: get number from numStr
        // TODO: throw away
        return new Result(true, "");
    }

    // === TOOLS, FOODS, ITEMS, AND CRAFTS === //

    public Result equipTool(String toolName) {
        // TODO: get Tool by its name (return appropriate failing message if null)
        // TODO: equip tool
        return new Result(true, "");
    }

    public Result useTool(String directionString) {
        Direction direction = Direction.getDirectionByDisplayName(directionString);
        Position position = neighborTile(direction);
        Tool tool = player.getCurrentTool();
        if (canToolBeUsedHere(position, tool)) {
            tool.useTool(direction);
            return new Result(true, ""); // todo: appropriate message
        }
        return new Result(false, "You can't use that tool in that direction"); // todo: appropriate message
    }

    public Result placeItem(String itemName, String directionString) {
        Item item = getItemByItemName(itemName);
        Direction direction = Direction.getDirectionByDisplayName(directionString);

        Position position = neighborTile(direction);
        if (canItemBePlacedHere(position, item)) {
            // TODO: place item
            // TODO: LEARN ABOUT assert position != null;
            return new Result(true, item + " placed at " + position.toString());
        }
        return new Result(false, "you can't place that item at " + position.toString());

    }

    public Result craft(String itemName) {
        Item item = getItemByItemName(itemName);
        if (!canCraft(item)) {
            return new Result(false, "Not possible to craft that item!");
        }
        // TODO: craft item and add it to inventory.
        return new Result(true, "Item crafted and added to inventory.");
    }

    public Result showCraftInfo(String craftName) {
        // TODO
        return new Result(true, "");
    }

    public Result cheatAddItem(String itemName) {
        Item item = getItemByItemName(itemName);
        // TODO: handle the optional "count" flag
        // TODO: add item to inventory
        return new Result(true, "Item added to inventory.");
    }

    // or name it cook() ?
    public Result prepareCook(String foodName) {
        FoodType food = FoodType.getFoodTypeByName(foodName);
        if (!canCook(food)) {
            return new Result(false, "You cannot cook this right now.");
            // todo: or specify the cause of the error...
        }
        // TODO: cook and add to inventory
        return new Result(true, "Yummy! Your meal is ready.");
    }

    public Result eat(String foodName) {
        FoodType food = FoodType.getFoodTypeByName(foodName);
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
        return false;
    }

    private boolean canCook(FoodType food) {
        // TODO: check if inventory is full; if so, return false.
        // TODO: check if we know the recipe, return false if not.
        // TODO: check if we have the ingredients, return false if not.
        return false;
    }

    private boolean canToolBeUsedHere(Position position, Tool tool) {
        // TODO: check the tile at "position" and check if tool can be used!
        return false;
    }

    private boolean canItemBePlacedHere(Position position, Item item) {
        // TODO: check the tile at "position" and check if item can be placed there!
        return false;
    }

    private Position neighborTile(Direction direction) {
        // TODO: return the position of the neighbour tile, if within the range of our map of farms.
        return null;
    }

    private Tile getTileByPosition(Position position) {
        // TODO: loop (the entire map) and return the tile whose position equals "position".
        return null;
    }


    // === WALK === //

    public Result walk(Path path, String walkConfirmation) {
        Boolean playerConfirmed = switch (walkConfirmation) {
            case "y" -> true;
            case "n" -> false;
            default -> null;
        };
        if (playerConfirmed == null) {
            return new Result(false, "the confirmation must be \"y\" or \"n\"");
        }
        if (!playerConfirmed) {
            return new Result(false, "You denied the walk.");
        }
        // TODO: Walk path! i.e. call player's inner changePosition(x,y) method.
        Position destination = path.getPathTiles().getLast();
        player.changePosition(destination);
        return new Result(true, "Walking...");
    }

    public Result respondForWalkRequest(String xString, String yString) {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        Position destination = new Position(x, y);
        Position origin = player.getPosition();
        Path path = findValidPath(origin, destination);
        if (path == null) {
            return new Result(false, "No valid path found!");
        }
        StringBuilder walkConfirmRequest = new StringBuilder();
        walkConfirmRequest
                .append("Do you confirm the walk?\n")
                .append("(respond with \"walk confirm\" followed by \"y\" or \"n\"");
        return new Result(true, walkConfirmRequest.toString());
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
        return false;
    }

    // === PRINT MAP === //

    public Result printMap(String xString, String yString, String sizeString) {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        int size = Integer.parseInt(sizeString);
        return new Result(true, ""); // TODO: print map.
    }

    public Result printColoredMap() {
        return new Result(true, ""); // TODO: print a colored map.
    }

    public Result showHelpReadingMap() {
        return new Result(true, ""); // TODO: show the "Help" / enter the Help menu / ...
    }


    // === GAME STATUS === //



//    public Result cheatAdvanceDate(String howManyDaysString) {
//        int howManyDays = Integer.parseInt(howManyDaysString);
//        // TODO;
//        return new Result(true, "");
//    }

    public Result cheatThor(String x, String y) {
        Position position = new Position(Integer.parseInt(x), Integer.parseInt(y));
        // TODO
        return new Result(true, "");
    }

    public Result showWeather() {
        // TODO
        return new Result(true, "");
    }

    public Result showWeatherForecast() {
        // TODO
        return new Result(true, "");
    }

    public Result cheatWeatherSet(String newWeatherString) {
        // TODO : get weather type from name
        return new Result(true, "");
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
        return false;
    }


    // === PLANTS === //

    public Result plant(String seedName, String directionName) {
        // TODO: get Seed from its name
        Direction direction = Direction.getDirectionByDisplayName(directionName);
        return new Result(true, "");
    }

    public Result showPlant(String xString, String yString) {
        Position position = new Position(Integer.parseInt(xString), Integer.parseInt(yString));
        Tile tile = getTileByPosition(position);
        // TODO
        return new Result(true, "");
    }

    public Result fertilize(String fertilizerName, String directionName) {
        // TODO : get FertilizerType from its name
        Direction direction = Direction.getDirectionByDisplayName(directionName);
        // TODO: fertilize
        return new Result(true, "");
    }


    // === FARM BUILDINGS & ANIMALS === //

    public Result build(String farmBuildingTypeStr, String xString, String yString) {
        FarmBuildingType farmBuildingType = FarmBuildingType.getFarmBuildingTypeByName(farmBuildingTypeStr);
        Position position = getPositionByStrings(xString, yString);
        if (position == null) {
            return new Result(false, "Enter two valid numbers for x and y.");
        }

        Farm farm = player.getFarm();
        FarmBuilding farmBuilding = new FarmBuilding(farmBuildingType, position);

        boolean canPlace = canPlaceBuilding(farm, farmBuildingType, position);

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
            items.put(wood, newWoodCount);
            items.put(stone, newStoneCount);
            methodOfPaymentDescription = "You used " + woodNeeded + " woods and " + stoneNeeded + " stones to build it";
        }
        farm.getFarmBuildings().add(farmBuilding);

        return new Result(true, "A " + farmBuildingType.getName() + " has been built in "
                + position.toString() + ". " + methodOfPaymentDescription);
    }

    public boolean canPlaceBuilding(Farm farm, FarmBuildingType farmBuildingType, Position position) {
        int xTopLeft = position.getX();
        int yTopLeft = position.getY();
        for (int i = 0; i < farmBuildingType.getWidth(); i++) {
            for (int j = 0; j < farmBuildingType.getLength(); j++) {
                Position currentPosition = new Position(xTopLeft + i, yTopLeft + j);
                if (!farm.getTileByPosition(currentPosition).getType().equals(TileType.NOT_PLOWED_GROUND)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Result buyAnimal(String animalTypeStr, String name) {
        AnimalType animalType = AnimalType.getAnimalTypeByName(animalTypeStr);
        List<FarmBuildingType> livingSpaceTypes = animalType.getLivingSpaceTypes();
        AnimalLivingSpace animalLivingSpace = getAvailableLivingSpace(livingSpaceTypes);

        if (animalLivingSpace == null) {
            return new Result(false, "You don't have any available living spaces for a "
                    + animalType.getName() + ".");
        }

        if (getAnimalByName(name) != null) {
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
        Animal animal = getAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "Animal not found.");
        }

        animal.changeFriendship(15);
        animal.setLastPettingTime(App.getCurrentGame().getGameState().getTime());

        return new Result(true, "You pet your " + animal.getAnimalType().getName() + ", " +
                animalName + ". Its' friendship level is now " + animal.getFriendshipLevel() + ".");
    }

    public void updateAnimals() { // TODO: call this method at the end of the day
        for (Animal animal : getAllFarmAnimals()) {
            if (!animal.hasBeenFedToday()) {
                animal.changeFriendship(-20);
            } else if (animal.getFriendshipLevel() >= 100) {
                animal.produceProduct();
            }

            if (!animal.hasBeenPetToday()) {
                animal.changeFriendship(-10);
            }

            if (animal.isOutside()) {
                animal.changeFriendship(-20);
            }
        }
    }

    public Result cheatSetFriendship(String animalName, String amountString) {
        int amount;
        if (!amountString.matches("\\d+")) {
            return new Result(false, "Enter a number between 0 and 1000.");
        } else {
            amount = Integer.parseInt(amountString);
        }

        Animal animal = getAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "Animal not found.");
        }

        animal.setFriendshipLevel(amount);

        return new Result(true, "Friendship of your " + animal.getAnimalType().getName() + ", " +
                animalName + ", has been set to " + amount + ".");
    }

    public Result showMyAnimalsInfo() {
        StringBuilder message = new StringBuilder("Your animals: \n");

        for (Animal animal : getAllFarmAnimals()) {

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
        Position newPosition = getPositionByStrings(xString, yString);
        if (newPosition == null) {
            return new Result(false, "Enter two valid numbers for x and y.");
        }

        Animal animal = getAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "Animal not found.");
        }

        Farm farm = player.getFarm();
        FarmBuilding farmBuildingInNewPosition = getFarmBuildingByPosition(newPosition);
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

        animal.setPosition(newPosition);
        animal.setLastFeedingTime(App.getCurrentGame().getGameState().getTime());
        animal.changeFriendship(8);
        return new Result(true, "Your " + animal.getAnimalType().getName() + ", " + animalName
                + ", has been moved to " + newPosition.toString() + " and is now outside. Its' friendship level is now "
                + animal.getFriendshipLevel() + ".");
    }

    public Position getPositionByStrings(String xString, String yString) {
        if (!xString.matches("\\d+") || !yString.matches("\\d+")) {
            return null;
        }

        int x, y;
        x = Integer.parseInt(xString);
        y = Integer.parseInt(xString);
        return new Position(x, y);
    }

    public FarmBuilding getFarmBuildingByPosition(Position position) {
        Farm farm = player.getFarm();
        for (FarmBuilding farmBuilding : farm.getFarmBuildings()) {
            int xTopLeft = farmBuilding.getPositionOfUpperLeftCorner().getX();
            int yTopLeft = farmBuilding.getPositionOfUpperLeftCorner().getY();
            int length = farmBuilding.getLength();
            int width = farmBuilding.getWidth();

            int x = position.getX();
            int y = position.getY();

            if (xTopLeft < x && xTopLeft + length > x && yTopLeft < y && yTopLeft + width > y) {
                return farmBuilding;
            }
        }
        return null;
    }

    public Result feedHayToAnimal(String animalName) {
        Animal animal = getAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "Animal not found.");
        }

        animal.setLastFeedingTime(App.getCurrentGame().getGameState().getTime());
        return new Result(true, "You fed hay to your " + animal.getAnimalType().getName() + ", "
                + animalName + ".");
    }

    public Result showProducedProducts() {
        StringBuilder message = new StringBuilder("Uncollected animal products: \n");

        for (Animal animal : getAllFarmAnimals()) {
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
        Animal animal = getAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "Animal not found.");
        }

        AnimalType animalType = animal.getAnimalType();
        ;
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

        StringBuilder message = new StringBuilder("You collected ");
        for (AnimalProduct item : collectedProducts.keySet()) {
            message.append(item.getType().getName()).append(" (x").append(collectedProducts.get(item)).append("), ");
        }
        return new Result(true, message.toString().replaceFirst(", $", "\n"));
    }

    public Result sellAnimal(String animalName) {
        Animal animal = getAnimalByName(animalName);
        if (animal == null) {
            return new Result(false, "Animal not found.");
        }

        double price = animal.calculatePrice();
        player.changeBalance(price);
        animal.getAnimalLivingSpace().removeAnimal(animal);
        return new Result(true, "You sold your " + animal.getAnimalType().getName() + ", " +
                animalName + ", for " + price + "g.");
    }

    private Animal getAnimalByName(String name) {
        for (Animal animal : getAllFarmAnimals()) {
            if (animal.getName().equals(name)) {
                return animal;
            }
        }
        return null;
    }

    private ArrayList<Animal> getAllFarmAnimals() {
        ArrayList<Animal> animals = new ArrayList<>();

        Farm farm = player.getFarm();
        for (FarmBuilding farmBuilding : farm.getFarmBuildings()) {
            if (farmBuilding.getFarmBuildingType().getIsCage() != null) {
                AnimalLivingSpace animalLivingSpace = (AnimalLivingSpace) farmBuilding;
                animals.addAll(animalLivingSpace.getAnimals());
            }
        }

        return animals;
    }

    public AnimalLivingSpace getAvailableLivingSpace(List<FarmBuildingType> livingSpaceTypes) {
        Farm farm = player.getFarm();

        for (FarmBuilding farmBuilding : farm.getFarmBuildings()) {
            if (livingSpaceTypes.contains(farmBuilding.getFarmBuildingType())) {
                AnimalLivingSpace animalLivingSpace = (AnimalLivingSpace) farmBuilding;
                if (!animalLivingSpace.isFull()) {
                    return animalLivingSpace;
                }
            }
        }

        return null;
    }

    // === FISHING === //

    public Result fishing(String fishingRodName) {
        FishingRod fishingRod = getFishingRodByName(fishingRodName);
        if (fishingRod == null) {
            return new Result(false, "You do not have a " + fishingRodName + " fishing rod.");
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
            FishType fishType = FishType.getRandomFishType(currentSeason, canCatchLegendary);

            double poleNumber = fishingRod.getRodType().getQualityNumber();
            double qualityNumber = (Math.random() * (fishingSkillLevel + 2) * poleNumber) / (7 - M);
            Quality quality = Quality.getQualityByNumber(qualityNumber);

            Fish fish = new Fish(fishType, quality);
            itemsHashMap.put(fish, itemsHashMap.getOrDefault(fish, 0) + 1);
            caughtFish.put(fish, caughtFish.getOrDefault(fish, 0) + 1);
        }

        StringBuilder message = new StringBuilder("You caught ");
        for (Fish fish : caughtFish.keySet()) {
            message.append(fish.getType().getName()).append(" (x").append(caughtFish.get(fish)).append("), ");
        }
        return new Result(true, message.toString().replaceFirst(", $", "\n"));
    }

    private FishingRod getFishingRodByName(String name) {
        ArrayList<Item> items = new ArrayList<>(player.getBackpack().getItems().keySet());
        for (Item item : items) {
            if (item instanceof FishingRod fishingRod) {
                if (fishingRod.getRodType().getName().equals(name)) {
                    return fishingRod;
                }
            }
        }
        return null;
    }

    // === ARTISAN === //

    public Result artisanUse(String artisanNamesString, String itemNamesString) {

        ArrayList<ItemType> itemTypes = new ArrayList<>();
        StringBuilder currentName = new StringBuilder();

        for (char c : itemNamesString.toCharArray()) {
            currentName.append(c);


        }

        return new Result(true, "");
    }


    public Result artisanGet(String artisanName) { // gives product
        // TODO: if product is not ready yet, return appropriate failing message

        // TODO: get the product from artisan
        return new Result(true, "");
    }

    private Artisan getArtisanByArtisanName(String artisanName) {
        for (ArtisanType type : ArtisanType.values()) {
            if (type.name().equalsIgnoreCase(artisanName)) {
                return new Artisan(type);
            }
        }
        return null;
    }

    private Item getItemByItemName(String itemName) {
        // TODO
        return null;
    }

    // === SHOPS === //

    public Result showAllProducts() {
        if (shop == null) {
            return new Result(false, "Enter a shop first!");
        }
        StringBuilder productList = new StringBuilder("All Products in " + shop.getName() + ":\n");

        for (GoodsType product : GoodsType.values()) {
            if (product.getShopType() == shop.getType()) {
                String availability = (product.getDailyLimit() == 0) ? "Unavailable" : "Available";
                productList.append(String.format("- %s: %d gold (%s)\n", product.name(), product.getPrice(), availability));
            }
        }

        return new Result(true, productList.toString());
    }

    public Result showAvailableProducts() {

        StringBuilder availableProducts = new StringBuilder("Available Products in " + shop.getType().getName() + ":\n");

        for (GoodsType product : GoodsType.values()) {
            if (product.getShopType() == shop.getType()) {
                availableProducts.append(String.format("- %s: %d gold\n", product.name(), product.getPrice()));
            }
        }

        return new Result(true, availableProducts.toString());
    }

    public Result purchase(String productName, String countStr) {
        // count is optional and might be null. In that case:
        int count;
        if (countStr == null) {
            count = 1;
        } else {
            count = Integer.parseInt(countStr);
        }

        Item product = getItemByItemName(productName);
        // TODO: check if we have enough money
        // TODO: check if the product is actually a valid product (not made up / invalid)
        // TODO: check if the product is available
        // TODO: check if the product has already been sold up to its daily limit (counts between different players)
        // TODO: check if the given "count" is greater than the item's daily limit
        return new Result(true, "");
    }

    public Result cheatAddDollars(String countStr) {
        int count = Integer.parseInt(countStr);
        player.changeBalance(count);
        return new Result(true, "You have " + player.getBalance() + "g now.");
    }

    public Result sell(String productName, String countStr) {
        // count is optional and might be null. In that case we sell the entire available in inventory
        int count;
        if (countStr == null) {
            count = 0; // TODO: total num
        } else {
            count = Integer.parseInt(countStr);
        }

        // TODO: Check if such a product cannot be sold.
        // TODO: Check if we do not have such a product.
        // TODO: Check if we aren't neighbors with a shipping bin. (we have to be near shipping bin to sell)
        // TODO: sell (also take into account its effect on friendship level)
        return new Result(true, "");
    }

    // === FRIENDSHIPS === //

    public Result showFriendshipLevels() {
        // TODO
        return new Result(true, "");
    }

    public Result talk(String username, String message) {
        // TODO (also take into account its effect on friendship level)
        return new Result(true, "");
    }

    public Result showTalkHistoryWithUser(String username) {
        // TODO (also take into account its effect on friendship level)
        return new Result(true, "");
    }

    public Result giveGift(String username, String itemName, String amountStr) {
        int amount = Integer.parseInt(amountStr);
        // TODO: check the error cases (from Doc page.48)
        return new Result(true, "");
    }

    public Result giftList() {
        // TODO
        return new Result(true, "");
    }

    public Result giftRate(String giftNumberStr, String rateStr) {
        int giftNumber = Integer.parseInt(giftNumberStr);
        int rate = Integer.parseInt(rateStr);
        // TODO
        return new Result(true, "");
    }

    public Result hug(String username) {
        // TODO
        return new Result(true, "");
    }

    public Result giveFlowerToUser(String username) {
        // TODO
        return new Result(true, "");
    }

    public Result askMarriage(String username, String ringStr) {
        // TODO: ring object type!!? wtf
        // TODO: will u marry me? :)
        return new Result(true, "");
    }

    public Result respondToMarriageRequest(String acceptanceStr, String username) {
        Boolean hasAccepted = null;
        if (acceptanceStr.equalsIgnoreCase("accept")) {
            hasAccepted = true;
        } else if (acceptanceStr.equalsIgnoreCase("reject")) {
            hasAccepted = false;
        }

        // TODO
        return new Result(true, "");
    }

    // === TRADE === //

    public Result startTrade() {
        App.setCurrentMenu(Menu.TARDE_MENU);
        return new Result(true, "You are now in Trade Menu.");
    }

    // === NPC === //

    public Result meetNPC(String NCPName) {
        // TODO
        return new Result(true, "");
    }

    public Result giftNPC(String NCPName, String itemName) {
        // TODO
        return new Result(true, "");
    }

    public Result showFriendshipNPCList() {
        // TODO
        return new Result(true, "");
    }

    public Result showQuestsList() {
        // TODO
        return new Result(true, "");
    }

    public Result finishQuest(String indexStr) {
        // TODO
        return new Result(true, "");
    }

    private NPC getNPCByName(String NPCName) {
        // TODO
        return null;
    }


}