package com.ap_project.controllers.signup;

import com.ap_project.common.models.App;
import com.ap_project.common.models.Result;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.types.Gender;
import com.ap_project.client.views.signup.SignUpMenuView;

import java.util.Objects;

import static com.ap_project.Main.goToSecurityQuestionMenu;
import static com.ap_project.Main.goToTitleMenu;
import static com.ap_project.controllers.login.LoginController.randomPasswordGenerator;
import static com.ap_project.common.models.App.getUserByEmail;
import static com.ap_project.common.models.App.getUserByUsername;

public class SignUpMenuController {
    private SignUpMenuView view;

    public void setView(SignUpMenuView view) {
        this.view = view;
    }

    public void handleSignUpMenuButtons() {
        if (view != null) {
            if (view.getSignUpButton().isChecked()) {
                String username = view.getUsernameField().getText();
                String password = view.getPasswordField().getText();
                String repeatPassword = view.getRepeatPasswordField().getText();
                String nickname = view.getNicknameField().getText();
                String email = view.getEmailField().getText();
                String genderString = view.getGenders().getSelected();
                Result result = registerUser(username, password, repeatPassword, nickname, email, genderString);
                if (result.success) {
                    goToSecurityQuestionMenu(username);
                } else {
                    view.setErrorMessage(result.message);
                }
            } else if (view.getRandomPasswordButton().isChecked()) {
                String randomPassword = randomPasswordGenerator();
                view.getPasswordField().setText(randomPassword);
                view.getPasswordField().setPasswordMode(false);
                view.getRepeatPasswordField().setText(randomPassword);
                view.getRepeatPasswordField().setPasswordMode(false);
            } else if (view.getBackButton().isChecked()) {
                goToTitleMenu();
            }
            view.getSignUpButton().setChecked(false);
            view.getRandomPasswordButton().setChecked(false);
            view.getBackButton().setChecked(false);
        }
    }

    public Result registerUser(String username,
                               String password,
                               String repeatPassword,
                               String nickname,
                               String email,
                               String genderString) {
        String VALID_USERNAME_REGEX = "^[a-zA-Z0-9-]+$";
        if (!username.matches(VALID_USERNAME_REGEX)) {
            return new Result(false, "Username invalid.");
        }

        if (getUserByUsername(username) != null) {
            return new Result(false, "Username already exists.");
        }

        if (getUserByEmail(email) != null) {
            return new Result(false, "Email already in use.");
        }

        String VALID_PASSWORD_REGEX = "^[a-zA-Z0-9?<>,\"';:\\\\/|\\[\\] {}+=)(*&^%$#!]+$";
        if (Objects.equals(password, "") || Objects.equals(repeatPassword, "")) {
            return new Result(false, "Password and repeat password must be provided.");
        }
        if (!password.matches(VALID_PASSWORD_REGEX) | password.length() < 8) {
            return new Result(false, "Password invalid.");
        }
        if (!password.equals(repeatPassword)) {
            return new Result(false, "Passwords do not match.");
        }

        String VALID_EMAIL_REGEX = "^(?!.*\\.\\.)[A-Za-z0-9](?:[A-Za-z0-9._-]*[A-Za-z0-9])?@(?:[A-Za-z0-9](?:[A-Za-z0" +
            "-9-]*[A-Za-z0-9])?\\.)+[A-Za-z]{2,}$";
        if (!email.matches(VALID_EMAIL_REGEX)) {
            return new Result(false, "Email format invalid.");
        }

        Gender gender = Gender.getGenderByName(genderString);

        User user = new User(username, password, nickname, email, gender);
        App.addUser(user);

        return new Result(true, "Registration successful.");
    }
}
