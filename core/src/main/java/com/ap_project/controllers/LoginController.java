package com.ap_project.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import com.ap_project.models.App;
import com.ap_project.models.Result;
import com.ap_project.models.User;
import com.ap_project.models.enums.Menu;
import com.ap_project.models.enums.SecurityQuestion;
import com.ap_project.views.LoginMenuView;

import static com.ap_project.Main.goToMainMenu;
import static com.ap_project.Main.goToTitleMenu;
import static com.ap_project.models.App.getUserByUsername;

public class LoginController {
    private LoginMenuView view;

    public void handleLoginMenuButtons() {
        if (view != null) {
            if (view.getLoginButton().isChecked()) {
                String username = view.getUsernameField().getText();
                String password = view.getPasswordField().getText();
                Result result = login(username, password);
                if (result.success) {
                    goToMainMenu();
                } else {
                    view.setErrorMessage(result.message);
                }
            } else if (view.getForgotPasswordButton().isChecked()) {
                // TODO
                goToTitleMenu();
            } else if (view.getBackButton().isChecked()) {
                goToTitleMenu();
            }
            view.getLoginButton().setChecked(false);
            view.getForgotPasswordButton().setChecked(false);
            view.getBackButton().setChecked(false);
        }
    }

    public void setView(LoginMenuView view) {
        this.view = view;
    }

    public Result login(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) {
            return new Result(false, "User not found.");
        }

        if (!password.equals(user.getPassword())) {
            return new Result(false, "Incorrect password.");
        }

        App.setLoggedIn(user);
        App.setCurrentMenu(Menu.MAIN_MENU);
        return new Result(true, "Login successful. You are now in Main Menu.");
    }

    public Result forgotPassword(String username) {
        User user = getUserByUsername(username);
        if (user == null) {
            return new Result(false, "Username not found");
        }
        if (user.getQAndA() == null || user.getQAndA().isEmpty()) {
            return new Result(false, "You haven't picked any security questions! Regret it ...");
        }
        Random random = new Random();
        int index = random.nextInt(user.getQAndA().size());
        String securityQuestion = (new ArrayList<>(user.getQAndA().keySet())).get(index).getQuestion();//
        System.out.println("Answer the following security question. \nUse this format: \"answer -a <your answer>\"");
        return new Result(true, securityQuestion);
    }

    public Result validateSecurityQuestion(String username, String question, String answer) {
        SecurityQuestion securityQuestion = SecurityQuestion.getSecurityQuestionByQuestion(question);
        User user = getUserByUsername(username);
        if (user == null) {
            return new Result(false, "User not found.");
        }
        String correctAnswer = user.getQAndA().get(securityQuestion);
        String newPassword = randomPasswordGenerator();
        if (correctAnswer.equals(answer)) {
            user.setPassword(newPassword);
            return new Result(true, "Correct answer! Your new password is: " + newPassword);
        }
        return new Result(false, "Incorrect answer.");
    }

    public static String hashSha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String randomPasswordGenerator() {
        int len = 12;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    public Result exit() {
        App.setCurrentMenu(Menu.EXIT);
        return new Result(true, "Exiting ... Bye Bye!");
    }
}
