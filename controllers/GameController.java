package controllers;

import models.*;
import models.enums.types.*;
import models.enums.types.FarmBuildingType;
import models.tools.Tool;
import models.enums.environment.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameController {
    User player = App.getLoggedIn();
    Game game = App.getCurrentGame();

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

    public Result cheatAddItem(Item item) {
        // TODO: add item to inventory
        return new Result(true, "Item added to inventory.");
    }

    // or name it cook() ?
    public Result prepareCook(FoodType food) {
        if (!canCook(food)) {
            return new Result(false, "You cannot cook this right now.");
            // todo: or specify the cause of the error...
        }
        // TODO: cook and add to inventory
        return new Result(true, "Yummy! Your meal is ready.");
    }

    public Result eat(FoodType food) {
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
        return false;
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
        return new Result(true, "");
    }

    public Result cheatAdvanceDate(int howManyDays) {
        // TODO;
        return new Result(true, "");
    }

    public Result cheatThor(Position position) {
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

    public Result cheatWeatherSet(Weather newWeather) {
        // TODO
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

    public Result plant(Direction direction, Seed seed) {
        // TODO
        return new Result(true, "");
    }

    public Result showPlant(Position position) {
        Tile tile = getTileByPosition(position);
        // TODO
        return new Result(true, "");
    }

    public Result fertilize(FertilizerType fertilizer, Direction direction) {
        // TODO
        return new Result(true, "");
    }

    // === FARM BUILDINGS & ANIMALS === //

    public Result build(FarmBuildingType farmBuildingType, String xString, String yString) {
        int x, y;
        if (!xString.matches("\\d+") || !yString.matches("\\d+")) {
            return new Result(false, "Enter two valid numbers for x and y.");
        } else {
            x = Integer.parseInt(xString);
            y = Integer.parseInt(xString);
        }

        Position position = new Position(x, y);

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

    public Result buyAnimal(AnimalType animalType, String name) {
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
        return new Result(true, "You bought a " + animalType.getName() + " called \"" + name +
                "\" and housed it in a " + animalLivingSpace.getFarmBuildingType().getName() + ".");
    }

    public Result pet(String animalName) {
        Animal animal = getAnimalByName(animalName);

        if (animal == null) {
            return new Result(false, "Animal not found.");
        }

        animal.changeFriendship(15);
        animal.setLastPettingTime(App.getCurrentGame().getGameState().getTime());

        return new Result(true, "You pet your " + animal.getAnimalType().getName() + ", \"" +
                animalName + "\". Its' friendship level is now " + animal.getFriendshipLevel() + ".");
    }

    public void updateAnimalFriendships() { // TODO: call this method at the end of the day
        for (Animal animal : getAllFarmAnimals()) {
            if (!animal.hasBeenFedToday()) {
                animal.changeFriendship(-20);
            }

            if (!animal.hasBeenPetToday()) {
                animal.changeFriendship(-10);
            }

            // TODO: check if animal is not inside its' living space at the end of the night
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

        return new Result(true, "Friendship of your " + animal.getAnimalType().getName() + ", \"" +
                animalName + "\" has been set to " + amount + ".");
    }

    public Result showMyAnimalsInfo() {
        String message = "Your animals: \n";

        for (Animal animal : getAllFarmAnimals()) {

            message += "-------------------------------\n" +
                    animal.getName() + " (" + animal.getAnimalType().getName() + "):\n" +
                    "Friendship level: " + animal.getFriendshipLevel() + "\n";

            if (animal.hasBeenFedToday()) {
                message += "Has been fed today.\n";
            } else {
                message += "Has not been fed today.\n";
            }

            if (animal.hasBeenPetToday()) {
                message += "Has been pet today.\n";
            } else {
                message += "Has not been pet today.\n";
            }
        }

        return new Result(true, message);
    }

    public Result shepherdAnimal(String animalName, String xString, String yString) {
        int x, y;
        if (!xString.matches("\\d+") || !yString.matches("\\d+")) {
            return new Result(false, "Enter two valid numbers for x and y.");
        } else {
            x = Integer.parseInt(xString);
            y = Integer.parseInt(xString);
        }

        // TODO
        return new Result(true, "");
    }

    public Result feedHayToAnimal(String animalName) {
        Animal animal = getAnimalByName(animalName);
        // TODO
        return new Result(true, "");
    }

    public Result showProducedProducts() {
        // TODO
        return new Result(true, "");
    }

    public Result collectProducts(String animalName) {
        Animal animal = getAnimalByName(animalName);
        // TODO
        return new Result(true, "");
    }

    public Result sellAnimal(String animalName) {
        Animal animal = getAnimalByName(animalName);
        // TODO
        return new Result(true, "");
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
        Tool fishingRod = getFishingRodByName(fishingRodName);
        // TODO: only fish if near lake and fishingPole is not null
        return new Result(true, "");
    }

    public int numberOfCaughtFish() {
        // TODO
        return 0;
    }

    public int qualityOfCaughtFish() {
        // TODO
        return 0;
    }

    private Tool getFishingRodByName(String name) {
        // TODO: find fishing pole
        return null;
    }

    // === ARTISAN === //

    public Result artisanUse(String artisanName, ArrayList<String> itemsNames) { // gets ingredients
        ArrayList<Item> ingredientItems = new ArrayList<>();
        for (String itemName : itemsNames) {
            ingredientItems.add(getItemByItemName(itemName));
        }
        Artisan artisan = getArtisanByArtisanName(artisanName);
        // TODO
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

    public Result showAllProducts(ShopType shopType) {
        StringBuilder productList = new StringBuilder("All Products in " + shopType.name() + ":\n");

        for (GoodsType product : GoodsType.values()) {
            if (product.getShopType() == shopType) {
                String availability = (product.getDailyLimit() == 0) ? "Unavailable" : "Available";
                productList.append(String.format("- %s: %d gold (%s)\n", product.name(), product.getPrice(), availability));
            }
        }

        return new Result(true, productList.toString());
    }

    public Result showAvailableProducts(ShopType shopType) {
        StringBuilder availableProducts = new StringBuilder("Available Products in " + shopType.name() + ":\n");

        for (GoodsType product : GoodsType.values()) {
            if (product.getShopType() == shopType) {
                availableProducts.append(String.format("- %s: %d gold\n", product.name(), product.getPrice()));
            }
        }

        return new Result(true, availableProducts.toString());
    }


    public Result purchase(String productName, Integer count) {
        // count is optional and might be null. In that case:
        if (count == null) {
            count = 1;
        }
        Item product = getItemByItemName(productName);
        // TODO: check if we have enough money
        // TODO: check if the product is actually a valid product (not made up / invalid)
        // TODO: check if the product is available
        // TODO: check if the product has already been sold up to its daily limit (counts between different players)
        // TODO: check if the given "count" is greater than the item's daily limit
        return new Result(true, "");
    }

    public Result cheatAddDollars(int amount, User currentUser) {
        currentUser.setBalance(amount);
        return new Result(true, "User has " + amount + "dollars now.");
    }

    public Result sell(String productName, Integer count) {
        // count is optional and might be null. In that case we sell the entire available in inventory
        if (count == null) {
            // TODO: count = total num of that product in our inventory
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

    public Result giveGift(String username, String itemName, int amount) {
        // TODO: check the error cases (from Doc page.48)
        return new Result(true, "");
    }

    public Result giftList() {
        // TODO
        return new Result(true, "");
    }

    public Result giftRate(int giftNumber, int rate) {
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

    public Result askMarriage(String username, Object ring) {
        // TODO: ring object type!!?
        // TODO: will u marry me? :)
        return new Result(true, "");
    }

    public Result respondToMarriageRequest(boolean accepted, String username) {
        // TODO
        return new Result(true, "");
    }

    // === TRADE === //

    public Result tradeWithMoney(String targetUsername, String type, String itemName, int amount, int price) { // type?
        // TODO
        return new Result(true, "");
    }

    public Result tradeWithItem(String targetUsername, String type, String itemName, int amount, String targetItemName, int targetAmount) { // type?
        // TODO: create a Trade class; int ID, User user1, User user2, Hashmap<Item, Integer>
        return new Result(true, "");
    }

    public Result showTradeList(String targetUsername, String type, String itemName, int amount, int price) { // type?
        // TODO
        return new Result(true, "");
    }

    public Result tradeResponse(int id) { // type?
        // TODO
        return new Result(true, "");
    }

    public Result showTradeHistory() { // type?
        // TODO:
        return new Result(true, "");
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

    public Result finishQuest(int index) {
        // TODO
        return new Result(true, "");
    }

    private NPC getNPCByName(String NPCName) {
        // TODO
        return null;
    }
}