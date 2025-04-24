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
            System.out.println(controller.showCurrentTool();
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
        }


        else {
            System.out.println("Invalid Command. Please try again!");
        }
    }
}
