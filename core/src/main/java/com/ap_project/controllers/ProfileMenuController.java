package com.ap_project.controllers;

import com.ap_project.models.App;
import com.ap_project.models.Result;
import com.ap_project.models.User;
import com.ap_project.models.enums.Menu;
import com.ap_project.models.enums.commands.LoginCommands;
import com.ap_project.views.ProfileMenuView;

import static com.ap_project.Main.goToMainMenu;
import static com.ap_project.models.App.getUserByEmail;
import static com.ap_project.models.App.getUserByUsername;

public class ProfileMenuController {
    private ProfileMenuView view;

    public void setView(ProfileMenuView view) {
        this.view = view;
    }

    public void handleProfileMenuButtons() {
        if (view != null) {
            Result result = new Result(true, "");
            if (view.getChangeAvatarButton().isChecked()) {
                App.getLoggedIn().setAvatarNumber(view.getAvatarBox().getSelected());
            }
            if (view.getChangeUsernameButton().isChecked()) {
                String username = view.getUsernameField().getText();
                result = changeUsername(username);
            } else if (view.getChangePasswordButton().isChecked()) {
                String password = view.getPasswordField().getText();
                result = changePassword(password);
            } else if (view.getChangeNicknameButton().isChecked()) {
                String nickname = view.getNicknameField().getText();
                result = changeNickname(nickname);
            } else if (view.getChangeEmailButton().isChecked()) {
                String email = view.getEmailField().getText();
                result = changeEmail(email);
            } else if (view.getBackButton().isChecked()) {
                goToMainMenu();
            }
            if (!result.success) {
                view.setErrorMessage(result.message);
            }
            view.getChangeUsernameButton().setChecked(false);
            view.getChangePasswordButton().setChecked(false);
            view.getChangeNicknameButton().setChecked(false);
            view.getChangeEmailButton().setChecked(false);
            view.getChangeAvatarButton().setChecked(false);
            view.getBackButton().setChecked(false);
        }
    }

    public Result changeUsername(String newUsername) {
        User currentUser = App.getLoggedIn();

        User existingUser = getUserByUsername(newUsername);
        if (existingUser != null && !existingUser.equals(currentUser)) {
            return new Result(false, "Username already taken.");
        }

        currentUser.setUsername(newUsername);
        return new Result(true, "Username changed successfully.");
    }

    public Result changeNickname(String newNickname) {
        App.getLoggedIn().setNickname(newNickname);
        return new Result(true, "Nickname changed successfully.");
    }

    public Result changeEmail(String newEmail) {
        if (!LoginCommands.VALID_EMAIL.matches(newEmail)) {
            return new Result(false, "Invalid email format.");
        }
        if (getUserByEmail(newEmail) != null) {
            return new Result(false, "Email already taken.");
        }
        App.getLoggedIn().setEmail(newEmail);
        return new Result(true, "Email changed successfully.");
    }

    public Result changePassword(String newPassword) {
        if (!LoginCommands.VALID_PASSWORD.matches(newPassword)) {
            return new Result(false, "New password does not meet requirements.");
        }
        App.getLoggedIn().setPassword(newPassword);
        return new Result(true, "Password changed successfully.");
    }

    public Result showCurrentMenu() {
        return new Result(true, "Current Menu: " +
                App.getCurrentMenu().toString());
    }

    public Result showUserInfo() {
        return new Result(true, App.getLoggedIn().toString());
    }

    public Result goToMainmenu() {
        App.setCurrentMenu(Menu.MAIN_MENU);
        return new Result(true, "Heading to Profile Menu");
    }
}
