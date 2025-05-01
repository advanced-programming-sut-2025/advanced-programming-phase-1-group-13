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
        } else {
            System.out.println("Invalid Command. Please try again!");
        }
    }
}
