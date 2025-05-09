package controllers;

import models.App;
import models.Result;
import models.enums.commands.LoginCommands;

import static models.App.getUserByEmail;
import static models.App.getUserByUsername;

public class ProfileController {
    public Result changeUsername(String newUsername) {
        if (getUserByUsername(newUsername) != null) {
            return new Result(false, "Username already taken.");
        }
        App.getLoggedIn().setUsername(newUsername);
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

    public Result changePassword(String oldPassword, String newPassword) {
        String currentHash = new LoginController().hashSha256(oldPassword);
        if (!App.getLoggedIn().getPassword().equals(currentHash)) {
            return new Result(false, "Old password does not match.");
        }
        if (!LoginCommands.VALID_PASSWORD.matches(newPassword)) {
            return new Result(false, "New password does not meet requirements.");
        }
        App.getLoggedIn().setPassword(new LoginController().hashSha256(newPassword));
        return new Result(true, "Password changed successfully.");
    }

    public Result showCurrentMenu() {
        return new Result(true, "Current Menu: " +
                App.getCurrentMenu().toString());
    }

    public Result showUserInfo() {
        return new Result(true, App.getLoggedIn().toString());
    }
}
