package views;

import controllers.PreGameMenuController;
import models.App;
import models.enums.Menu;
import models.enums.commands.PreGameMenuCommands;
import models.enums.commands.ProfileCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class PreGameMenu implements AppMenu {
    private final PreGameMenuController controller = new PreGameMenuController();
    Matcher matcher;

    @Override
    public void check(Scanner scanner) {
        String inputLine = scanner.nextLine();
        if ((matcher = PreGameMenuCommands.GAME_NEW.getMatcher(inputLine)) != null) {
            System.out.println(controller.gameNew(matcher.group("usernames")));
        } else if ((matcher = PreGameMenuCommands.GAME_MAP.getMatcher(inputLine)) != null) {
            System.out.println(controller.chooseGameMap(matcher.group("mapNumber")));
        } else if ((matcher = PreGameMenuCommands.LOAD_GAME.getMatcher(inputLine)) != null) {
            System.out.println(controller.loadGame());
        } else if ((matcher = ProfileCommands.SHOW_CURRENT_MENU.getMatcher(inputLine)) != null) {
            System.out.println(controller.showCurrentMenu());
        } else if ((matcher = PreGameMenuCommands.EXIT.getMatcher(inputLine)) != null) {
            App.setCurrentMenu(Menu.MAIN_MENU);
            System.out.println("Menu switched to Main Menu.");
        } else {
            System.out.println("Invalid command. Please try again.");
        }

    }
}
