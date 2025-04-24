package views;

import controllers.ProfileController;
import models.enums.commands.ProfileCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu {
    private final ProfileController controller = new ProfileController();
    Matcher matcher;

    @Override
    public void check(Scanner scanner) {
        String inputLine = scanner.nextLine();
        if ((matcher = ProfileCommands.CHANGE_USERNAME.getMatcher(inputLine)) != null) {
            System.out.println(controller.changeUsername(matcher.group("username")));
        } else if ((matcher = ProfileCommands.CHANGE_NICKNAME.getMatcher(inputLine)) != null) {
            System.out.println(controller.changeNickname(matcher.group("nickname")));
        } else if ((matcher = ProfileCommands.CHANGE_EMAIL.getMatcher(inputLine)) != null) {
            System.out.println(controller.changeEmail(matcher.group("email")));
        } else if ((matcher = ProfileCommands.CHANGE_PASSWORD.getMatcher(inputLine)) != null) {
            System.out.println(controller.changePassword(matcher.group("oldPass"), matcher.group("newPass")));
        } else if ((matcher = ProfileCommands.SHOW_CURRENT_MENU.getMatcher(inputLine)) != null) {
            System.out.println(controller.showCurrentMenu());
        } else if ((matcher = ProfileCommands.USER_INFO.getMatcher(inputLine)) != null) {
            System.out.println(controller.showUserInfo());
        } else {
            System.out.println("Invalid Command");
        }
    }
}
