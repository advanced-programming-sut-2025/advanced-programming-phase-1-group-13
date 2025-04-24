package controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

import models.App;
import models.Result;
import models.User;
import models.enums.SecurityQuestion;
import models.enums.types.Gender;
import models.enums.commands.LoginCommands;

public class LoginController {

    public static User getUserByUsername(String username) {
        for (User user : App.getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static User getUserByEmail(String email) {
        for (User user : App.getUsers()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public Result registerUser(String username,
                               String password,
                               String repeatPassword,
                               String nickname,
                               String email,
                               String genderString) {
        if (!LoginCommands.VALID_USERNAME.matches(username)) {
            return new Result(false, "Username invalid.");
        }
        if (!LoginCommands.VALID_PASSWORD.matches(password)) {
            return new Result(false, "Password invalid.");
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
        if (!password.equals(repeatPassword)) {
            return new Result(false, "Passwords do not match.");
        }
        Gender gender = Gender.getGenderByName(genderString);
        String hash = hashSha256(password);
        User user = new User(username, hash, nickname, email, gender);
        user.setQAndA(new HashMap<>()); // todo fix this part!!!!!!!!!!
        App.addUser(user);
        return new Result(true, "Registration successful.");
    }

    public Result randomPasswordGenerator() {
        int len = 12;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return new Result(true, sb.toString());
    }

    String hashSha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Result login(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) {
            return new Result(false, "User not found.");
        }
        String hash = hashSha256(password);
        if (!hash.equals(user.getPassword())) {
            return new Result(false, "Incorrect password.");
        }
        App.setLoggedIn(user);
        return new Result(true, "Login successful.");
    }

    public Result forgotPassword(String username, String email) {
        User user = getUserByUsername(username);
        if (user == null || !user.getEmail().equalsIgnoreCase(email)) {
            return new Result(false, "Username/Email mismatch.");
        }
        if (user.getQAndA() != null && !user.getQAndA().isEmpty()) {
            SecurityQuestion q = user.getQAndA().keySet().iterator().next();
            return new Result(true, q.name());
        }
        Result r = randomPasswordGenerator();
        String newPwd = r.getMessage();
        user.setPassword(hashSha256(newPwd));
        return new Result(true, "New password: " + newPwd);
    }

    public Result askSecurityQuestion(User user) {
        if (user == null || user.getQAndA() == null || user.getQAndA().isEmpty()) {
            return new Result(false, "No security question set.");
        }
        SecurityQuestion q = user.getQAndA().keySet().iterator().next();
        return new Result(true, q.name());
    }

    public Result validateSecurityQuestion(String answer) {
        if (App.getLoggedIn() == null || App.getLoggedIn().getQAndA() == null) {
            return new Result(false, "No question to validate.");
        }
        SecurityQuestion q = App.getLoggedIn().getQAndA().keySet().iterator().next();
        String correct = App.getLoggedIn().getQAndA().get(q);
        if (correct.equalsIgnoreCase(answer)) {
            return new Result(true, "Security validated.");
        }
        return new Result(false, "Incorrect answer.");
    }

    public Result pickSecurityQuestion(int questionNumber, String answer, String repeatAnswer) {
        if (App.getLoggedIn() == null) {
            return new Result(false, "User not found.");
        }
        SecurityQuestion question = SecurityQuestion.values()[questionNumber - 1];
        // todo: errors regarding question number and stuff?
        if (!answer.equals(repeatAnswer)) {
            return new Result(false, "Answers do not match.");
        }
        App.getLoggedIn().getQAndA().put(question, answer);
        return new Result(true, "Security question saved.");
    }

}