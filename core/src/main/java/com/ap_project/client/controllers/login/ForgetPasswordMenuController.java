package com.ap_project.controllers.login;

import com.ap_project.common.models.Result;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.SecurityQuestion;
import com.ap_project.client.views.login.ForgetPasswordMenuView;

import java.util.ArrayList;
import java.util.Random;

import static com.ap_project.Main.goToChangePasswordMenu;
import static com.ap_project.Main.goToLoginMenu;
import static com.ap_project.controllers.login.LoginController.randomPasswordGenerator;
import static com.ap_project.common.models.App.getUserByUsername;

public class ForgetPasswordMenuController {
    private ForgetPasswordMenuView view;

    public void handleForgetPasswordMenuButtons() {
        if (view != null) {
            if (view.getEnterButton().isChecked()) {
                String username = view.getUsernameField().getText();
                Result showQuestion = forgotPassword(username);
                if (showQuestion.success) {
                    String question = showQuestion.message;
                    view.setQuestion(question);
                    String answer = view.getAnswerField().getText();
                    Result answerCheck = validateSecurityQuestion(username, question, answer);
                    System.out.println(answerCheck.success);
                    if (answerCheck.success) {
                        goToChangePasswordMenu(username);
                    } else {
                        view.setErrorMessage(answerCheck.message);
                    }
                } else {
                    view.setErrorMessage(showQuestion.message);
                }
            } else if (view.getBackButton().isChecked()) {
                goToLoginMenu();
            }
            view.getEnterButton().setChecked(false);
            view.getBackButton().setChecked(false);
        }
    }

    public Result forgotPassword(String username) {
        User user = getUserByUsername(username);
        if (user == null) {
            return new Result(false, "Username not found");
        }
        if (user.getQAndA() == null || user.getQAndA().isEmpty()) {
            return new Result(false, "You haven't picked any security questions! Regret it ...");
        }
        Random random = new Random();
        int index = random.nextInt(user.getQAndA().size());
        String securityQuestion = (new ArrayList<>(user.getQAndA().keySet())).get(index).getQuestion();
        return new Result(true, securityQuestion);
    }

    public Result validateSecurityQuestion(String username, String question, String answer) {
        SecurityQuestion securityQuestion = SecurityQuestion.getSecurityQuestionByQuestion(question);
        User user = getUserByUsername(username);
        if (user == null) {
            return new Result(false, "User not found.");
        }
        String correctAnswer = user.getQAndA().get(securityQuestion);
        String newPassword = randomPasswordGenerator();
        if (correctAnswer.equals(answer)) {
            user.setPassword(newPassword);
            return new Result(true, "Correct answer! Your new password is: " + newPassword);
        }
        if (answer.isEmpty()) {
            return new Result(false, "");
        }
        return new Result(false, "Incorrect answer.");
    }

    public void setView(ForgetPasswordMenuView view) {
        this.view = view;
    }

}
