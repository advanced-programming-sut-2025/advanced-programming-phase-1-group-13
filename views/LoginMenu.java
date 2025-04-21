package views;
import controllers.LoginController;
import models.enums.SecurityQuestion;
import models.enums.commands.LoginCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu {
    private final LoginController controller = new LoginController();
    Matcher matcher;
    @Override
    public void check(Scanner scanner) {
        String inputLine = scanner.nextLine();
        Matcher matcher;

        if ((matcher = LoginCommands.REGISTER_REGEX.getMatcher(inputLine)) != null) {
            System.out.println(controller.registerUser(
                    matcher.group("username"),
                    matcher.group("password"),
                    matcher.group("password_confirm"),
                    matcher.group("nickname"),
                    matcher.group("email"),
                    matcher.group("gender")
            ));
        } else if ((matcher = LoginCommands.PICK_QUESTION_REGEX.getMatcher(inputLine)) != null) {
            SecurityQuestion question = SecurityQuestion.values()[Integer.parseInt(matcher.group("question_number")) - 1];
            System.out.println(controller.pickSecurityQuestion(
                    controller.getUserByUsername(matcher.group("username")),
                    question,
                    matcher.group("answer")
            ));
        } else if ((matcher = LoginCommands.FORGET_PASSWORD_REGEX.getMatcher(inputLine)) != null) {
            System.out.println(controller.forgotPassword(
                    matcher.group("username"),
                    matcher.group("email"),
                    scanner
            ));
        } else if ((matcher = LoginCommands.ANSWER_REGEX.getMatcher(inputLine)) != null) {
            System.out.println(controller.validateSecurityQuestion(
                    controller.getUserByUsername(matcher.group("username")),
                    matcher.group("answer")
            ));
        } else if ((matcher = LoginCommands.LOGIN_REGEX.getMatcher(inputLine)) != null) {
            System.out.println(controller.login(
                    matcher.group("username"),
                    matcher.group("password")
            ));
        } else if ((matcher = LoginCommands.SHOW_CURRENT_MENU.getMatcher(inputLine)) != null) {
            System.out.println("You are currently in the Login Menu.");
        } else {
            System.out.println("Invalid command. Please try again.");
        }
    }

}

