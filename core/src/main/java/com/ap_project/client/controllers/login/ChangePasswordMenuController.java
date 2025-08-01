package com.ap_project.client.controllers.login;

import com.ap_project.common.models.App;
import com.ap_project.common.models.Result;
import com.ap_project.common.models.User;
import com.ap_project.client.views.login.ChangePasswordMenuView;

import static com.ap_project.Main.goToLoginMenu;
import static com.ap_project.client.controllers.login.LoginController.hashSha256;
import static com.ap_project.client.controllers.login.LoginController.randomPasswordGenerator;

public class ChangePasswordMenuController {
    private final String username;
    private ChangePasswordMenuView view;

    public ChangePasswordMenuController(String username) {
        this.username = username;
    }

    public void handleChangePasswordMenuButtons() {
        if (view != null) {
            if (view.getEnterButton().isChecked()) {
                String newPassword = view.getNewPasswordField().getText();
                Result result = changePassword(newPassword);
                if (result.success) {
                    goToLoginMenu();
                } else {
                    view.setErrorMessage(result.message);
                }
            } else if (view.getRandomPasswordButton().isChecked()) {
                view.getNewPasswordField().setPasswordMode(false);
                view.getNewPasswordField().setText(randomPasswordGenerator());
            }
            view.getEnterButton().setChecked(false);
            view.getRandomPasswordButton().setChecked(false);
        }
    }

    public void setView(ChangePasswordMenuView view) {
        this.view = view;
    }

    public Result changePassword(String newPassword) {
        String VALID_PASSWORD_REGEX = "^[a-zA-Z0-9?<>,\"';:\\\\/|\\[\\] {}+=)(*&^%$#!]+$";
        if (!newPassword.matches(VALID_PASSWORD_REGEX) | newPassword.length() < 8) {
            return new Result(false, "New password does not meet requirements.");
        }
        User user = App.getUserByUsername(username);
        user.setPassword(newPassword);
        return new Result(true, "Password changed successfully.");
    }

}
