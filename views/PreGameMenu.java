package views;

import controllers.PreGameMenuController;
import models.enums.commands.PreGameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class PreGameMenu implements AppMenu {
    private final PreGameMenuController controller = new PreGameMenuController();
    Matcher matcher;

    @Override
    public void check(Scanner scanner) {
        String inputLine = scanner.nextLine();
        if ((matcher = PreGameMenuCommands.GAME_NEW.getMatcher(inputLine)) != null) {
            System.out.println(controller.gameNew(
                    matcher.group("username_1"),
                    matcher.group("username_2"),
                    matcher.group("username_3")
            ));
        } else if ((matcher = PreGameMenuCommands.GAME_MAP.getMatcher(inputLine)) != null) {
            System.out.println(controller.chooseGameMap(matcher.group("map_number")));
        } else if ((matcher = PreGameMenuCommands.LOAD_GAME.getMatcher(inputLine)) != null) {
            System.out.println(controller.loadGame());
        } else if ((matcher = PreGameMenuCommands.EXIT_GAME.getMatcher(inputLine)) != null) {
            System.out.println(controller.exitGame());
        } else {
            System.out.println("Invalid command. Please try again.");
        }

    }
}
