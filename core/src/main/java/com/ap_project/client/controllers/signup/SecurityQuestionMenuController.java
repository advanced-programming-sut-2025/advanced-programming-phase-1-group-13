package com.ap_project.client.controllers.signup;

import com.ap_project.common.models.Result;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.SecurityQuestion;
import com.ap_project.client.views.signup.SecurityQuestionMenuView;

import static com.ap_project.Main.goToTitleMenu;
import static com.ap_project.common.models.App.getUserByUsername;

public class SecurityQuestionMenuController {
    private final String username;
    private SecurityQuestionMenuView view;

    public SecurityQuestionMenuController(String username) {
        this.username = username;
    }

    public void setView(SecurityQuestionMenuView view) {
        this.view = view;
    }

    public void handleSecurityQuestionMenuButtons() {
        if (view != null) {
            if (view.getEnterButton().isChecked()) {
                int questionNumber = view.getSecurityQuestions().getSelectedIndex();
                String answer = view.getAnswerField().getText();
                String repeatAnswer = view.getRepeatAnswerField().getText();
                Result result = pickSecurityQuestion(username, questionNumber, answer, repeatAnswer);
                if (result.success) {
                    goToTitleMenu();
                } else {
                    view.setErrorMessage(result.message);
                }
            } else if (view.getSkipButton().isChecked()) {
                goToTitleMenu();
            }
            view.getSkipButton().setChecked(false);
            view.getEnterButton().setChecked(false);
        }
    }

    public Result pickSecurityQuestion(String username, int questionNumber, String answer, String repeatAnswer) {
        SecurityQuestion question = SecurityQuestion.getSecurityQuestionByNumber(questionNumber + 1);

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
