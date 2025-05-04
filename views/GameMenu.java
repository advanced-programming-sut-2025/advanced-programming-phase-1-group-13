package views;

import controllers.GameController;
import models.enums.commands.GameCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {
    private final GameController controller = new GameController();
    Matcher matcher;

    @Override
    public void check(Scanner scanner) {
        String inputLine = scanner.nextLine();
        if ((matcher = GameCommands.ENERGY_SHOW.getMatcher(inputLine)) != null) {
            System.out.println(controller.showPlayerEnergy());
        } else if ((matcher = GameCommands.CHEAT_ENERGY_SET.getMatcher(inputLine)) != null) {
            System.out.println(controller.setPlayerEnergy(matcher.group("value")));
        } else if ((matcher = GameCommands.CHEAT_ENERGY_UNLIMITED.getMatcher(inputLine)) != null) {
            System.out.println(controller.setUnlimitedEnergy());
        } else if ((matcher = GameCommands.TOOLS_SHOW_CURRENT.getMatcher(inputLine)) != null) {
            System.out.println(controller.showCurrentTool());
        } else if ((matcher = GameCommands.COOKING_SHOW_RECIPES.getMatcher(inputLine)) != null) {
            System.out.println(controller.showLearntCookingRecipes());
        } else if ((matcher = GameCommands.CRAFTING_SHOW_RECIPES.getMatcher(inputLine)) != null) {
            System.out.println(controller.showLearntCraftRecipes());
        } else if ((matcher = GameCommands.INVENTORY_SHOW.getMatcher(inputLine)) != null) {
            System.out.println(controller.inventoryShow());
        } else if ((matcher = GameCommands.THROW_ITEM_TO_TRASH.getMatcher(inputLine)) != null) {
            System.out.println(controller.throwItemToTrash(
                    matcher.group("itemName"),
                    matcher.group("number")
            ));
        } else if ((matcher = GameCommands.TOOLS_EQUIP.getMatcher(inputLine)) != null) {
            System.out.println(controller.equipTool(matcher.group("tool_name")));
        } else if ((matcher = GameCommands.TOOLS_USE.getMatcher(inputLine)) != null) {
            System.out.println(controller.useTool(matcher.group("direction")));
        } else if ((matcher = GameCommands.PLACE_ITEM.getMatcher(inputLine)) != null) {
            System.out.println(controller.placeItem(
                    matcher.group("item_name"),
                    matcher.group("direction")
            ));
        } else if ((matcher = GameCommands.CRAFTING_CRAFT.getMatcher(inputLine)) != null) {
            System.out.println(controller.craft(matcher.group("item_name")));
        } else if ((matcher = GameCommands.CRAFT_INFO.getMatcher(inputLine)) != null) {
            System.out.println(controller.showCraftInfo(matcher.group("craft_name")));
        } else if ((matcher = GameCommands.CHEAT_ADD_ITEM.getMatcher(inputLine)) != null) {
            System.out.println(controller.cheatAddItem(matcher.group("item_name")));
            // TODO: handle the optional "count" flag
        } else if ((matcher = GameCommands.COOKING_PREPARE.getMatcher(inputLine)) != null) {
            System.out.println(controller.prepareCook(matcher.group("recipe_name")));
        } else if ((matcher = GameCommands.EAT.getMatcher(inputLine)) != null) {
            System.out.println(controller.eat(matcher.group("food_name")));
        } else if ((matcher = GameCommands.WALK.getMatcher(inputLine)) != null) {
            System.out.println(controller.respondForWalkRequest(
                    matcher.group("x"),
                    matcher.group("y")
            ));
        } else if ((matcher = GameCommands.WALK_CONFIRM.getMatcher(inputLine)) != null) {
            System.out.println(controller.eat(matcher.group("y_or_n")));
        } else if ((matcher = GameCommands.PRINT_MAP.getMatcher(inputLine)) != null) {
            System.out.println(controller.printMap(
                    matcher.group("x"),
                    matcher.group("y"),
                    matcher.group("size")
            ));
        } else if ((matcher = GameCommands.PRINT_COLORED_MAP.getMatcher(inputLine)) != null) {
            System.out.println(controller.printMap(
                    matcher.group("x"),
                    matcher.group("y"),
                    matcher.group("size")
            ));
        } else if ((matcher = GameCommands.HELP_READING_MAP.getMatcher(inputLine)) != null) {
            System.out.println(controller.showHelpReadingMap());
        } else if ((matcher = GameCommands.CHEAT_ADV_TIME.getMatcher(inputLine)) != null) {
            System.out.println(controller.cheatAdvanceTime(matcher.group("hourIncrease")));
        } else if ((matcher = GameCommands.CHEAT_ADV_DATE.getMatcher(inputLine)) != null) {
            System.out.println(controller.cheatAdvanceDate(matcher.group("dayIncrease")));
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
        } else if ((matcher = GameCommands.BUILD.getMatcher(inputLine)) != null) {
            System.out.println(controller.build(
                    matcher.group("building_name"),
                    matcher.group("x"),
                    matcher.group("y")
            ));
        }
        // continue from " buyAnimal "
        else if ((matcher = GameCommands.BUY_ANIMAL.getMatcher(inputLine)) != null) {
            System.out.println(controller.buyAnimal(
                    matcher.group("animal"),
                    matcher.group("animal_name")
            ));
        } else if ((matcher = GameCommands.PET.getMatcher(inputLine)) != null) {
            System.out.println(controller.pet(matcher.group("name")));
        } else if ((matcher = GameCommands.CHEAT_SET_FRIENDSHIP.getMatcher(inputLine)) != null) {
            System.out.println(controller.cheatSetFriendship(
                    matcher.group("animal_name"),
                    matcher.group("amount")
            ));
        } else if ((matcher = GameCommands.ANIMALS.getMatcher(inputLine)) != null) {
            System.out.println(controller.showMyAnimalsInfo());
        } else if ((matcher = GameCommands.SHEPHERD_ANIMALS.getMatcher(inputLine)) != null) {
            System.out.println(controller.shepherdAnimal(
                    matcher.group("animal_name"),
                    matcher.group("x"),
                    matcher.group("y")
            ));
        } else if ((matcher = GameCommands.FEED_HAY.getMatcher(inputLine)) != null) {
            System.out.println(controller.feedHayToAnimal(matcher.group("animal_name")));
        } else if ((matcher = GameCommands.PRODUCES.getMatcher(inputLine)) != null) {
            System.out.println(controller.showProducedProducts());
        } else if ((matcher = GameCommands.COLLECT_PRODUCE.getMatcher(inputLine)) != null) {
            System.out.println(controller.collectProducts(matcher.group("name")));
        } else if ((matcher = GameCommands.SELL_ANIMAL.getMatcher(inputLine)) != null) {
            System.out.println(controller.sellAnimal(matcher.group("name")));
        } else if ((matcher = GameCommands.FISHING.getMatcher(inputLine)) != null) {
            System.out.println(controller.fishing(matcher.group("fishing_pole")));
        } else if ((matcher = GameCommands.ARTISAN_USE.getMatcher(inputLine)) != null) {
            System.out.println(controller.artisanUse(
                    matcher.group("artisan_name"),
                    matcher.group("items_names")
            ));
        } else if ((matcher = GameCommands.ARTISAN_GET.getMatcher(inputLine)) != null) {
            System.out.println(controller.artisanGet(matcher.group("artisan_name")));
        } else if ((matcher = GameCommands.SHOW_ALL_PRODUCTS.getMatcher(inputLine)) != null) {
            System.out.println(controller.showAllProducts());
        } else if ((matcher = GameCommands.SHOW_ALL_AVAILABLE_PRODUCTS.getMatcher(inputLine)) != null) {
            System.out.println(controller.showAvailableProducts());
        } else if ((matcher = GameCommands.PURCHASE.getMatcher(inputLine)) != null) {
            System.out.println(controller.purchase(
                    matcher.group("product_name"),
                    matcher.group("count")
            ));
        } else if ((matcher = GameCommands.CHEAT_ADD_DOLLARS.getMatcher(inputLine)) != null) {
            System.out.println(controller.cheatAddDollars(matcher.group("count")));

        } else if ((matcher = GameCommands.SELL.getMatcher(inputLine)) != null) {
            System.out.println(controller.sell(
                    matcher.group("product_name"),
                    matcher.group("count")
            ));
        } else {
            System.out.println("Invalid Command. Please try again!");
        }
    }
}
