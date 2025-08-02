package com.ap_project.client.views.phase_one;

import com.ap_project.client.controllers.MainMenuController;
import com.ap_project.common.models.enums.commands.MainMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu {
    private final MainMenuController controller = new MainMenuController();
    Matcher matcher;

    @Override
    public void check(Scanner scanner) {
        String inputLine = scanner.nextLine();
        if ((matcher = MainMenuCommands.USER_LOGOUT.getMatcher(inputLine)) != null) {
            System.out.println(controller.logout());
        } else if ((matcher = MainMenuCommands.MENU_EXIT.getMatcher(inputLine)) != null) {
            System.out.println(controller.exitMenu());
        } else if ((matcher = MainMenuCommands.MENU_ENTER.getMatcher(inputLine)) != null) {
            System.out.println(controller.enterMenu(matcher.group("newMenu")));
        } else if ((matcher = MainMenuCommands.SHOW_CURRENT_MENU.getMatcher(inputLine)) != null) {
            System.out.println(controller.showCurrentMenu());
        } else {
            System.out.println("Invalid command. Please try again.");
        }
    }
}
