package com.ap_project.views.phase_one;

import com.ap_project.controllers.ProfileMenuController;
import com.ap_project.models.enums.commands.ProfileCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu {
    private final ProfileMenuController controller = new ProfileMenuController();
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
            //System.out.println(controller.changePassword(matcher.group("oldPass"), matcher.group("newPass")));
        } else if ((matcher = ProfileCommands.SHOW_CURRENT_MENU.getMatcher(inputLine)) != null) {
            System.out.println(controller.showCurrentMenu());
        } else if ((matcher = ProfileCommands.USER_INFO.getMatcher(inputLine)) != null) {
            System.out.println(controller.showUserInfo());
        } else if ((matcher = ProfileCommands.MENU_EXIT.getMatcher(inputLine)) != null) {
            System.out.println(controller.goToMainmenu());
        } else {
            System.out.println("Invalid command. Please try again.");
        }
    }
}
