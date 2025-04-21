package views;

import controllers.ProfileController;
import models.enums.commands.ProfileCommands;

import java.util.Scanner;
import java.util.regex.Matcher;
import models.App;

public class ProfileMenu implements AppMenu {
    private final ProfileController controller = new ProfileController();

    @Override
    public void check(Scanner scanner) {
        String inputLine = scanner.nextLine();
        Matcher matcher;

        if ((matcher = ProfileCommands.CHANGE_USERNAME.getMatcher(inputLine)) != null) {
            System.out.println(controller.changeUsername(
                    App.getLoggedIn(),
                    matcher.group("username")
            ));
        } else if ((matcher = ProfileCommands.CHANGE_NICKNAME.getMatcher(inputLine)) != null) {
            System.out.println(controller.changeNickname(
                    App.getLoggedIn(),
                    matcher.group("nickname")
            ));
        } else if ((matcher = ProfileCommands.CHANGE_EMAIL.getMatcher(inputLine)) != null) {
            System.out.println(controller.changeEmail(
                    App.getLoggedIn(),
                    matcher.group("email")
            ));
        } else if ((matcher = ProfileCommands.CHANGE_PASSWORD.getMatcher(inputLine)) != null) {
            System.out.println(controller.changePassword(
                    App.getLoggedIn(),
                    matcher.group("oldPass"),
                    matcher.group("newPass")
            ));
        } else if ((matcher = ProfileCommands.USER_INFO.getMatcher(inputLine)) != null) {
            System.out.println(App.getLoggedIn());
        } else if ((matcher = ProfileCommands.SHOW_CURRENT_MENU.getMatcher(inputLine)) != null) {
            System.out.println("You are currently in the Profile Menu.");
        } else {
            System.out.println("Invalid command. Please try again.");
        }
    }
}
