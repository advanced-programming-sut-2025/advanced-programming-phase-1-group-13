package com.ap_project.views;

import com.ap_project.controllers.PreGameMenuController;
import com.ap_project.models.App;
import com.ap_project.models.enums.Menu;
import com.ap_project.models.enums.commands.PreGameMenuCommands;
import com.ap_project.models.enums.commands.ProfileCommands;

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
