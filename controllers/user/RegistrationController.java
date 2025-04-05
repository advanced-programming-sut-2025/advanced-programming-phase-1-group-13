package controllers.user;

import models.Result;
import models.User;
import models.enums.types.Gender;
import models.enums.SecurityQuestion;

import java.util.HashMap;
import java.util.Scanner;


public class RegistrationController {
    public Result registerUser(String username,
                               String password,
                               String email,
                               Gender gender) {
        // TODO
    }

    public Result randomPasswordGenerator() {
        // TODO
    }

    public Result showSecurityQuestions() {
        for (SecurityQuestion question : SecurityQuestion.values()) {
            System.out.println(question);
        }
    }

    public Result pickAndAnswerSecurityQuestion(User user, int questionNumber, String answer) {
        if (user == null) {
            return new Result(false, "User not found");
        }

        if (user.getQAndA() == null) {
            user.setQAndA(new HashMap<>());
        }

        SecurityQuestion[] questions = SecurityQuestion.values();

        if (questionNumber < 0 || questionNumber >= questions.length) {
            return new Result(false, "Invalid question number");
        }

        SecurityQuestion selectedQuestion = questions[questionNumber];
        user.getQAndA().put(selectedQuestion, answer);

        return new Result(true, "Security question saved");
    }


    public Result sha256() {
        // TODO
    }

    public boolean isPasswordFormatValid() {
        // TODO
    }

    public boolean isPasswordFormatValid() {
        // TODO
    }

    public boolean isEmailFormatValid() {
        // TODO
    }

    public boolean isNicknameFormatValid() {
        // TODO
    }

}
