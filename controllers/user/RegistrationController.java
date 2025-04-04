package controllers.user;

import models.Result;
import models.enums.types.Gender;


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
        // TODO
    }

    public Result pickAndAnswerSecurityQuestion(int questionNumber, String answer) {
        // TODO : find the SecurityQuestion from its int
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
