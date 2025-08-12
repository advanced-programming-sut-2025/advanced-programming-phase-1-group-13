package com.ap_project.client.controllers.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

import com.ap_project.Main;
import com.ap_project.common.models.App;
import com.ap_project.common.models.Result;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.Menu;
import com.ap_project.client.views.login.LoginMenuView;
import com.ap_project.common.models.network.Message;
import com.ap_project.common.models.network.MessageType;
import com.ap_project.common.utils.JSONUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static com.ap_project.Main.*;
import static com.ap_project.common.models.App.getUserByUsername;

public class LoginController {
    private LoginMenuView view;
    private boolean waitingForResponse = false;

    public void handleLoginMenuButtons() {
        if (waitingForResponse) return;

        if (view != null) {
            if (view.getLoginButton().isChecked() || Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                String username = view.getUsernameField().getText();
                String password = view.getPasswordField().getText();
                waitingForResponse = true;
                login(username, password);
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

    public void handleLoginSuccess(User user) {
        waitingForResponse = false;
        System.out.println(user);
        App.setLoggedIn(user);
        goToMainMenu();
    }

    public void handleLoginError(String error) {
        waitingForResponse = false;
        System.out.println("Error that should be in View" + error);
        view.setErrorMessage(error);
        if (view != null) {
            view.setErrorMessage(error);
        }
    }

    public void login(String username, String password) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("username", username);
        body.put("password", hashSha256(password));

        Message message = new Message(body, MessageType.LOGIN);
        Main.getClient().sendMessage(JSONUtils.toJson(message));
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
