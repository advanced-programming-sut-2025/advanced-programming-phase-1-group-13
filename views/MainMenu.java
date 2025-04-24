package views;

import controllers.MainMenuController;
import models.enums.commands.MainMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu {
    private final MainMenuController controller = new MainMenuController();
    Matcher matcher;

    @Override
    public void check(Scanner scanner) {
        String inputLine = scanner.nextLine();
        if ((matcher = MainMenuCommands.USER_LOGOUT.getMatcher(inputLine)) != null) {
            System.out.println(controller.user);
        }
    }
}
