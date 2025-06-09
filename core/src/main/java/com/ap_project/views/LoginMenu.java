package com.ap_project.views;

import com.ap_project.controllers.LoginController;
import com.ap_project.models.Result;
import com.ap_project.models.enums.commands.LoginCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu {
    private final LoginController controller = new LoginController();
    Matcher matcher;

    @Override
    public void check(Scanner scanner) {
        String inputLine = scanner.nextLine();
        Matcher matcher;
        if (LoginCommands.MENU_EXIT.getMatcher(inputLine) != null) {
            System.out.println(controller.exit());
        } else if ((matcher = LoginCommands.REGISTER_USER_PASS.getMatcher(inputLine)) != null) {
            String username = matcher.group("username").trim();
            Result result = controller.registerUser(
                    username,
                    matcher.group("password"),
                    matcher.group("repeatPassword"),
                    matcher.group("nickname"),
                    matcher.group("email"),
                    matcher.group("gender"),
                    false
            );
            System.out.println(result.message);
            if (result.success) {
                inputLine = scanner.nextLine();
                if ((matcher = LoginCommands.PICK_QUESTION_REGEX.getMatcher(inputLine)) != null) {
                    System.out.println(controller.pickSecurityQuestion(
                            username,
                            matcher.group("questionNumber"),
                            matcher.group("answer"),
                            matcher.group("repeatAnswer")
                    ));
                }
            }
        } else if ((matcher = LoginCommands.REGISTER_USER_RAND.getMatcher(inputLine)) != null) {
            String username = matcher.group("username").trim();
            Result result = controller.registerUser(
                    username,
                    matcher.group("nickname"),
                    matcher.group("email"),
                    matcher.group("gender"),
                    true  // generate random password
            );
            System.out.println(result.message);
            if (result.success) {
                inputLine = scanner.nextLine();
                if ((matcher = LoginCommands.PICK_QUESTION_REGEX.getMatcher(inputLine)) != null) {
                    System.out.println(controller.pickSecurityQuestion(
                            username,
                            matcher.group("questionNumber"),
                            matcher.group("answer"),
                            matcher.group("repeatAnswer")
                    ));
                }
            }
        }
        else if ((matcher = LoginCommands.FORGET_PASSWORD.getMatcher(inputLine)) != null) {
            String username = matcher.group("username");
            Result result = controller.forgotPassword(username);
            System.out.println(result);
            if (result.success) {
                inputLine = scanner.nextLine();
                if ((matcher = LoginCommands.ANSWER_SECURITY_QUESTION.getMatcher(inputLine)) != null) {
                    System.out.println(controller.validateSecurityQuestion(
                            username,
                            result.message,
                            matcher.group("answer")
                    ));
                } else {
                    System.out.println("Invalid command. Try \"answer -a <your answer>\".");
                }
            }
        } else if ((matcher = LoginCommands.LOGIN.getMatcher(inputLine)) != null) {
            System.out.println(controller.login(
                    matcher.group("username"),
                    matcher.group("password")
            )); // TODO: "stay logged in" flag !
        } else if (LoginCommands.SHOW_CURRENT_MENU.getMatcher(inputLine) != null) {
            System.out.println("You are currently in the Login Menu.");
        } else {
            System.out.println("Invalid command. Please try again.");
        }
    }
}

