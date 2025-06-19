package com.ap_project.controllers;

import com.ap_project.models.Result;
import com.ap_project.models.User;
import com.ap_project.models.enums.SecurityQuestion;
import com.ap_project.views.SecurityQuestionMenuView;

import static com.ap_project.Main.goToMainMenu;
import static com.ap_project.models.App.getUserByUsername;

public class SecurityQuestionMenuController {
    private SecurityQuestionMenuView view;

    public void setView(SecurityQuestionMenuView view) {
        this.view = view;
    }

    public void handleSecurityQuestionMenuButtons() {
        if (view != null) {
            if (view.getEnterButton().isChecked()) {
                goToMainMenu();
            } else if (view.getSkipButton().isChecked()) {
                goToMainMenu();
            }
            view.getSkipButton().setChecked(false);
            view.getEnterButton().setChecked(false);
        }
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
