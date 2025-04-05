package controllers.user;

import models.Result;
import models.User;
import models.enums.SecurityQuestion;

public class LoginController {
    public static User getUserByUsername(String username) {

    }

    public Result login(String username, String password) {

    }

    public Result askSecurityQuestion(User user) {
        if (user == null || user.getQAndA() == null || user.getQAndA().isEmpty()) {
            return new Result(false, "No security question set for this user!");
        }

        SecurityQuestion question = user.getQAndA().keySet().iterator().next();
        return new Result(true, "question");
    }

    public Result validateSecurityQuestion(User user, String answerToSecurityQuestion) {
        SecurityQuestion question = user.getQAndA().keySet().iterator().next();
        String correctAnswer = user.getQAndA().get(question);

        if (correctAnswer.equalsIgnoreCase(answerToSecurityQuestion)) {
            return new Result(true, "Security question validated successfully.");
        } else {
            return new Result(false, "Incorrect answer.");
        }
    }


    public Result forgotPassword(String username, String email) {

    }

}
