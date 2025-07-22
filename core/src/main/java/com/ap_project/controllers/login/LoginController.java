package com.ap_project.controllers.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import com.ap_project.models.App;
import com.ap_project.models.Result;
import com.ap_project.models.User;
import com.ap_project.models.enums.Menu;
import com.ap_project.views.login.LoginMenuView;

import static com.ap_project.Main.*;
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
                goToForgetPasswordMenu();
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

        String hashedPassword = hashSha256(password);
        if (!hashedPassword.equals(user.getPassword())) {
            return new Result(false, "Incorrect password.");
        }

        App.setLoggedIn(user);
        App.setCurrentMenu(Menu.MAIN_MENU);
        return new Result(true, "Login successful. You are now in Main Menu.");
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
