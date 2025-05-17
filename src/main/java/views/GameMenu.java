package views;

import controllers.GameController;
import models.Result;
import models.enums.commands.GameCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {
    private final GameController controller = new GameController();
    Matcher matcher;

    @Override
    public void check(Scanner scanner) {
        String inputLine = scanner.nextLine();
        if ((matcher = GameCommands.NEXT_TURN.getMatcher(inputLine)) != null) {
            System.out.println(GameController.nextTurn());
        } else if ((matcher = GameCommands.TIME.getMatcher(inputLine)) != null) {
            System.out.println(controller.showTime());
        } else if ((matcher = GameCommands.DATE.getMatcher(inputLine)) != null) {
            System.out.println(controller.showDate());
        } else if ((matcher = GameCommands.DATETIME.getMatcher(inputLine)) != null) {
            System.out.println(controller.showDateAndTime());
        } else if ((matcher = GameCommands.DAY_OF_THE_WEEK.getMatcher(inputLine)) != null) {
            System.out.println(controller.showDayOfTheWeek());
        } else if ((matcher = GameCommands.CHEAT_ADV_TIME.getMatcher(inputLine)) != null) {
            System.out.println(controller.cheatAdvanceTime(matcher.group("hourIncrease")));
        } else if ((matcher = GameCommands.CHEAT_ADV_DATE.getMatcher(inputLine)) != null) {
            System.out.println(controller.cheatAdvanceDate(matcher.group("dayIncrease")));
        } else if ((matcher = GameCommands.SEASON.getMatcher(inputLine)) != null) {
            System.out.println(controller.showSeason());
        } else if ((matcher = GameCommands.CHEAT_THOR.getMatcher(inputLine)) != null) {
            System.out.println(controller.cheatThor(
                    matcher.group("x"),
                    matcher.group("y")));
        } else if ((matcher = GameCommands.WEATHER.getMatcher(inputLine)) != null) {
            System.out.println(controller.showWeather());
        } else if ((matcher = GameCommands.WEATHER_FORECAST.getMatcher(inputLine)) != null) {
            System.out.println(controller.showWeatherForecast());
        } else if ((matcher = GameCommands.CHEAT_WEATHER_SET.getMatcher(inputLine)) != null) {
            System.out.println(controller.cheatWeatherSet(matcher.group("type")));
        } else if ((matcher = GameCommands.GREENHOUSE_BUILD.getMatcher(inputLine)) != null) {
            System.out.println(controller.buildGreenhouse());
        } else if ((matcher = GameCommands.CHEAT_BUILD_GREENHOUSE.getMatcher(inputLine)) != null) {
            System.out.println(controller.cheatBuildNewGreenhouse());
        } else if ((matcher = GameCommands.WALK.getMatcher(inputLine)) != null) {
            Result result = controller.respondForWalkRequest(
                    matcher.group("x"),
                    matcher.group("y")
            );
            System.out.println(result);
            if (result.success()) {
                String newInputLine = scanner.nextLine();
                if ((matcher = GameCommands.WALK_CONFIRM.getMatcher(newInputLine)) != null) {
                    System.out.println(controller.applyTheWalk(
                            matcher.group("yOrN"),
                            result.message()
                    ));
                } else {
                    System.out.println("Invalid command.");
                }
            }
        } else if ((matcher = GameCommands.WALK_CONFIRM.getMatcher(inputLine)) != null) {
            System.out.println("First enter this command: \"walk -l x,y\" for a valid destination.");
        } else if ((matcher = GameCommands.PRINT_MAP.getMatcher(inputLine)) != null) {
            System.out.println(controller.printMap(
                    matcher.group("x"),
                    matcher.group("y"),
                    matcher.group("size")
            ));
        } else if ((matcher = GameCommands.PRINT_MAP_VILLAGE.getMatcher(inputLine)) != null) {
            System.out.println(controller.printMapVillage(
                    matcher.group("x"),
                    matcher.group("y"),
                    matcher.group("size")
            ));
        } else if ((matcher = GameCommands.HELP_READING_MAP.getMatcher(inputLine)) != null) {
            System.out.println(controller.showHelpReadingMap());
        } else if ((matcher = GameCommands.ENERGY_SHOW.getMatcher(inputLine)) != null) {
            System.out.println(controller.showPlayerEnergy());
        } else if ((matcher = GameCommands.CHEAT_ENERGY_SET.getMatcher(inputLine)) != null) {
            System.out.println(controller.setPlayerEnergy(matcher.group("value")));
        } else if ((matcher = GameCommands.CHEAT_ENERGY_UNLIMITED.getMatcher(inputLine)) != null) {
            System.out.println(controller.setUnlimitedEnergy());
        } else if ((matcher = GameCommands.CHEAT_ENERGY_LIMITED.getMatcher(inputLine)) != null) {
            System.out.println(controller.deactivateUnlimitedEnergy());
        } else if ((matcher = GameCommands.INVENTORY_SHOW.getMatcher(inputLine)) != null) {
            System.out.println(controller.inventoryShow());
        } else if ((matcher = GameCommands.TOOLS_EQUIP.getMatcher(inputLine)) != null) {
            System.out.println(controller.equipTool(matcher.group("toolName")));
        } else if ((matcher = GameCommands.TOOLS_SHOW_CURRENT.getMatcher(inputLine)) != null) {
            System.out.println(controller.showCurrentTool());
        } else if ((matcher = GameCommands.TOOLS_SHOW_AVAILABLE.getMatcher(inputLine)) != null) {
            System.out.println(controller.showAvailableTools());
        } else if ((matcher = GameCommands.TOOLS_UPGRADE.getMatcher(inputLine)) != null) {
            System.out.println(controller.upgradeTools(matcher.group("toolsName")));
        } else if ((matcher = GameCommands.TOOLS_USE.getMatcher(inputLine)) != null) {
            System.out.println(controller.useTool(matcher.group("direction")));
        } else if ((matcher = GameCommands.CRAFT_INFO.getMatcher(inputLine)) != null) {
            System.out.println(controller.showCraftInfo(matcher.group("craftName")));
        } else if ((matcher = GameCommands.PLANT.getMatcher(inputLine)) != null) {
            System.out.println(controller.plant(
                    matcher.group("seed"),
                    matcher.group("direction")
            ));
        } else if ((matcher = GameCommands.SHOW_PLANT.getMatcher(inputLine)) != null) {
            System.out.println(controller.showPlant(
                    matcher.group("x"),
                    matcher.group("y")
            ));
        } else if ((matcher = GameCommands.FERTILIZE.getMatcher(inputLine)) != null) {
            System.out.println(controller.fertilize(
                    matcher.group("fertilizer"),
                    matcher.group("direction")
            ));
        } else if ((matcher = GameCommands.HOWMUCH_WATER.getMatcher(inputLine)) != null) {
            System.out.println(controller.howMuchWater());
        } else if ((matcher = GameCommands.CRAFTING_SHOW_RECIPES.getMatcher(inputLine)) != null) {
            System.out.println(controller.showLearntCraftRecipes());
        } else if ((matcher = GameCommands.CRAFTING_CRAFT.getMatcher(inputLine)) != null) {
            System.out.println(controller.craft(matcher.group("itemName")));
        } else if ((matcher = GameCommands.PLACE_ITEM.getMatcher(inputLine)) != null) {
            System.out.println(controller.placeItem(
                    matcher.group("itemName"),
                    matcher.group("direction")
            ));
        } else if ((matcher = GameCommands.THROW_ITEM_TO_TRASH.getMatcher(inputLine)) != null) {
            System.out.println(controller.throwItemToTrash(
                    matcher.group("itemName"),
                    matcher.group("number")
            ));
        } else if ((matcher = GameCommands.PLACE_ITEM.getMatcher(inputLine)) != null) {
            System.out.println(controller.placeItem(
                    matcher.group("itemName"),
                    matcher.group("direction")
            ));
        } else if ((matcher = GameCommands.CHEAT_ADD_ITEM.getMatcher(inputLine)) != null) {
            System.out.println(controller.cheatAddItem(
                    matcher.group("itemName"),
                    matcher.group("count")
            ));
        } else if ((matcher = GameCommands.COOKING_REFRIGERATOR.getMatcher(inputLine)) != null) {
            System.out.println(controller.cookingRefrigerator(
                    matcher.group("putOrPick"),
                    matcher.group("item")
            ));
        } else if ((matcher = GameCommands.COOKING_SHOW_RECIPES.getMatcher(inputLine)) != null) {
            System.out.println(controller.showLearntCookingRecipes());
        } else if ((matcher = GameCommands.COOKING_PREPARE.getMatcher(inputLine)) != null) {
            System.out.println(controller.prepareCook(matcher.group("recipeName")));
        } else if ((matcher = GameCommands.EAT.getMatcher(inputLine)) != null) {
            System.out.println(controller.eat(matcher.group("foodName")));
        } else if ((matcher = GameCommands.BUILD.getMatcher(inputLine)) != null) {
            System.out.println(controller.build(
                    matcher.group("buildingName"),
                    matcher.group("x"),
                    matcher.group("y")
            ));
        } else if ((matcher = GameCommands.BUY_ANIMAL.getMatcher(inputLine)) != null) {
            System.out.println(controller.buyAnimal(
                    matcher.group("animal"),
                    matcher.group("name")
            ));
        } else if ((matcher = GameCommands.PET.getMatcher(inputLine)) != null) {
            System.out.println(controller.pet(matcher.group("name")));
        } else if ((matcher = GameCommands.CHEAT_SET_FRIENDSHIP.getMatcher(inputLine)) != null) {
            System.out.println(controller.cheatSetFriendship(
                    matcher.group("name"),
                    matcher.group("amount")
            ));
        } else if ((matcher = GameCommands.ANIMALS.getMatcher(inputLine)) != null) {
            System.out.println(controller.showMyAnimalsInfo());
        } else if ((matcher = GameCommands.SHEPHERD_ANIMALS.getMatcher(inputLine)) != null) {
            System.out.println(controller.shepherdAnimal(
                    matcher.group("animalName"),
                    matcher.group("x"),
                    matcher.group("y")
            ));
        } else if ((matcher = GameCommands.FEED_HAY.getMatcher(inputLine)) != null) {
            System.out.println(controller.feedHayToAnimal(matcher.group("animalName")));
        } else if ((matcher = GameCommands.PRODUCES.getMatcher(inputLine)) != null) {
            System.out.println(controller.showProducedProducts());
        } else if ((matcher = GameCommands.COLLECT_PRODUCE.getMatcher(inputLine)) != null) {
            System.out.println(controller.collectProducts(matcher.group("name")));
        } else if ((matcher = GameCommands.SELL_ANIMAL.getMatcher(inputLine)) != null) {
            System.out.println(controller.sellAnimal(matcher.group("name")));
        } else if ((matcher = GameCommands.FISHING.getMatcher(inputLine)) != null) {
            System.out.println(controller.fishing(matcher.group("fishingPole")));
        } else if ((matcher = GameCommands.ARTISAN_USE.getMatcher(inputLine)) != null) {
            System.out.println(controller.artisanUse(
                    matcher.group("artisanName"),
                    matcher.group("itemsNames")
            ));
        } else if ((matcher = GameCommands.ARTISAN_GET.getMatcher(inputLine)) != null) {
            System.out.println(controller.artisanGet(matcher.group("artisanName")));
        } else if ((matcher = GameCommands.SHOW_ALL_PRODUCTS.getMatcher(inputLine)) != null) {
            System.out.println(controller.showAllProducts());
        } else if ((matcher = GameCommands.SHOW_ALL_AVAILABLE_PRODUCTS.getMatcher(inputLine)) != null) {
            System.out.println(controller.showAvailableProducts());
        } else if ((matcher = GameCommands.PURCHASE.getMatcher(inputLine)) != null) {
            System.out.println(controller.purchase(
                    matcher.group("productName"),
                    matcher.group("count")
            ));
        } else if ((matcher = GameCommands.CHEAT_ADD_DOLLARS.getMatcher(inputLine)) != null) {
            System.out.println(controller.cheatAddDollars(matcher.group("count")));

        } else if ((matcher = GameCommands.SELL.getMatcher(inputLine)) != null) {
            System.out.println(controller.sell(
                    matcher.group("productName"),
                    matcher.group("count")
            ));
        } else if ((matcher = GameCommands.FRIENDSHIPS.getMatcher(inputLine)) != null) {
            System.out.println(controller.showFriendshipLevels());
        } else if ((matcher = GameCommands.TALK.getMatcher(inputLine)) != null) {
            System.out.println(controller.talk(
                    matcher.group("username"),
                    matcher.group("message")
            ));
        } else if ((matcher = GameCommands.TALK_HISTORY.getMatcher(inputLine)) != null) {
            System.out.println(controller.showTalkHistoryWithUser(matcher.group("username")));
        } else if ((matcher = GameCommands.GIFT.getMatcher(inputLine)) != null) {
            System.out.println(controller.giveGift(
                    matcher.group("username"),
                    matcher.group("item"),
                    matcher.group("amount")
            ));
        } else if ((matcher = GameCommands.GIFT_LIST.getMatcher(inputLine)) != null) {
            System.out.println(controller.giftList());
        } else if ((matcher = GameCommands.GIFT_RATE.getMatcher(inputLine)) != null) {
            System.out.println(controller.giftRate(
                    matcher.group("giftNumber"),
                    matcher.group("rate")
            ));
        } else if ((matcher = GameCommands.GIFT_HISTORY.getMatcher(inputLine)) != null) {
            System.out.println(controller.showGiftHistory(matcher.group("username")));
        } else if ((matcher = GameCommands.HUG.getMatcher(inputLine)) != null) {
            System.out.println(controller.hug(matcher.group("username")));
        } else if ((matcher = GameCommands.FLOWER.getMatcher(inputLine)) != null) {
            System.out.println(controller.giveFlowerToUser(
                    matcher.group("username"),
                    matcher.group("flowerName")
            ));
        } else if ((matcher = GameCommands.ASK_MARRIAGE.getMatcher(inputLine)) != null) {
            System.out.println(controller.askMarriage(
                    matcher.group("username"),
                    matcher.group("ring")));
        } else if ((matcher = GameCommands.RESPONSE_MARRIAGE.getMatcher(inputLine)) != null) {
            System.out.println(controller.respondToMarriageRequest(
                    matcher.group("response"),
                    matcher.group("username")
            ));
        } else if ((matcher = GameCommands.START_TRADE.getMatcher(inputLine)) != null) {
            System.out.println(controller.startTrade());
        } else if ((matcher = GameCommands.MEETNPC.getMatcher(inputLine)) != null) {
            System.out.println(controller.meetNPC(matcher.group("npcName")));
        } else if ((matcher = GameCommands.GIFTNPC.getMatcher(inputLine)) != null) {
            System.out.println(controller.giftNPC(matcher.group("npcName"), matcher.group("item")));
        } else if ((matcher = GameCommands.FRIENDSHIPNPC_LIST.getMatcher(inputLine)) != null) {
            System.out.println(controller.showFriendshipNPCList());
        } else if ((matcher = GameCommands.QUESTS_LIST.getMatcher(inputLine)) != null) {
            System.out.println(controller.showQuestsList());
        } else if ((matcher = GameCommands.QUESTS_FINISH.getMatcher(inputLine)) != null) {
            System.out.println(controller.finishQuest(
                    matcher.group("npcName"),
                    matcher.group("index")
            ));
        } else if ((matcher = GameCommands.EXIT_GAME.getMatcher(inputLine)) != null) {
            System.out.println(controller.exitGame());
        } else if ((matcher = GameCommands.SHOW_CURRENT_MENU.getMatcher(inputLine)) != null) {
            System.out.println("Game Menu");
        } else if ((matcher = GameCommands.GO_TO_VILLAGE.getMatcher(inputLine)) != null) {
            System.out.println(controller.goToVillage());
        } else if ((matcher = GameCommands.EXIT_VILLAGE.getMatcher(inputLine)) != null) {
            System.out.println(controller.exitVillage());
        } else if ((matcher = GameCommands.WALK_VILLAGE.getMatcher(inputLine)) != null) {
            System.out.println(controller.walkInVillage(matcher.group("x"), matcher.group("y")));
        } else {
            System.out.println("Invalid Command. Please try again!");
        }
    }
}
