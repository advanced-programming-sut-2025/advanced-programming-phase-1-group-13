package controllers;

import models.Result;
import models.User;
import models.enums.commands.LoginCommands;

public class ProfileController {
    public Result changeUsername(User user, String newUsername) {
        if (LoginController.getUserByUsername(newUsername) != null) {
            return new Result(false, "Username already taken.");
        }
        LoginController.getUserByUsername(user.getUsername());
        user.setUsername(newUsername);
        return new Result(true, "Username changed successfully.");
    }

    public Result changeNickname(User user, String newNickname) {
        return new Result(true, "Nickname changed successfully.");
    }

    public Result changeEmail(User user, String newEmail) {
        if (!LoginCommands.EMAIL_REGEX.matches(newEmail)) {
            return new Result(false, "Invalid email format.");
        }
        for (User u : LoginController.users.values()) {
            if (u.getEmail().equalsIgnoreCase(newEmail)) {
                return new Result(false, "Email already in use.");
            }
        }
        user.setEmail(newEmail);
        return new Result(true, "Email changed successfully.");
    }

    public Result changePassword(User user, String oldPassword, String newPassword) {
        String currentHash = new LoginController().hashSha256(oldPassword);
        if (!user.getPassword().equals(currentHash)) {
            return new Result(false, "Old password does not match.");
        }

        if (!LoginCommands.PASSWORD_REGEX.matches(newPassword)) {
            return new Result(false, "New password does not meet requirements.");
        }
        user.setPassword(new LoginController().hashSha256(newPassword));
        return new Result(true, "Password changed successfully.");
    }
}
