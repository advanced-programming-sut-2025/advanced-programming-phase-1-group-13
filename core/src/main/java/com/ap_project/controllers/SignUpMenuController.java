package com.ap_project.controllers;

import com.ap_project.models.App;
import com.ap_project.models.Result;
import com.ap_project.models.User;
import com.ap_project.models.enums.SecurityQuestion;
import com.ap_project.models.enums.commands.LoginCommands;
import com.ap_project.models.enums.types.Gender;
import com.ap_project.views.SignUpMenuView;

import static com.ap_project.Main.goToSecurityQuestionMenu;
import static com.ap_project.Main.goToTitleMenu;
import static com.ap_project.controllers.LoginController.hashSha256;
import static com.ap_project.controllers.LoginController.randomPasswordGenerator;
import static com.ap_project.models.App.getUserByEmail;
import static com.ap_project.models.App.getUserByUsername;

public class SignUpMenuController {
    private SignUpMenuView view;

    public void setView(SignUpMenuView view) {
        this.view = view;
    }

    public void handleSignUpMenuButtons() {
        if (view != null) {
            if (view.getSignUpButton().isChecked()) {
                // TODO
                goToSecurityQuestionMenu();
            } else if (view.getBackButton().isChecked()) {
                goToTitleMenu();
            }
            view.getSignUpButton().setChecked(false);
            view.getBackButton().setChecked(false);
        }
    }

    public Result registerUser(String username,
                               String password,
                               String repeatPassword,
                               String nickname,
                               String email,
                               String genderString,
                               boolean generateRandomPassword) {
        if (!LoginCommands.VALID_USERNAME.matches(username)) {
            return new Result(false, "Username invalid.");
        }
        if (!LoginCommands.VALID_EMAIL.matches(email)) {
            return new Result(false, "Email format invalid.");
        }
        if (getUserByUsername(username) != null) {
            return new Result(false, "Username already exists.");
        }
        if (getUserByEmail(email) != null) {
            return new Result(false, "Email already in use.");
        }

        String finalPassword;
        if (generateRandomPassword) {
            finalPassword = randomPasswordGenerator().message;
        } else {
            if (password == null || repeatPassword == null) {
                return new Result(false, "Password and repeat password must be provided.");
            }
            if (!LoginCommands.VALID_PASSWORD.matches(password) | password.length() < 8) {
                return new Result(false, "Password invalid.");
            }
            if (!password.equals(repeatPassword)) {
                return new Result(false, "Passwords do not match.");
            }
            finalPassword = password;
        }

        Gender gender = Gender.getGenderByName(genderString);
        User user = new User(username, finalPassword, hashSha256(finalPassword), nickname, email, gender);
        App.addUser(user);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Registration successful.\n");
        if (generateRandomPassword) {
            stringBuilder.append("Your generated password is: ").append(finalPassword).append("\n");
            stringBuilder.append("Please save this password securely.\n");
        }
        stringBuilder.append("Pick a Security question: \n");
        for (SecurityQuestion question : SecurityQuestion.values()) {
            stringBuilder.append(question.toString()).append("\n");
        }
        stringBuilder.append("Answer in this format: \"pick question -q <question number> -a <answer> -c <repeated answer>\"");

        return new Result(true, stringBuilder.toString());
    }

    public Result pickSecurityQuestion(String username, String questionNumberStr, String answer, String repeatAnswer) {
        int questionNumber = Integer.parseInt(questionNumberStr);
        SecurityQuestion question = SecurityQuestion.getSecurityQuestionByNumber(questionNumber);
        if (question == null) {
            return new Result(false, "Invalid question number.");
        }
        if (!answer.equals(repeatAnswer)) {
            return new Result(false, "Answers do not match.");
        }
        User user = getUserByUsername(username);
        if (user == null) {
            return new Result(false, "User not found.");
        }
        user.addQAndA(question, answer);
        return new Result(true, "Security question and answer added successfully.");
    }
}
