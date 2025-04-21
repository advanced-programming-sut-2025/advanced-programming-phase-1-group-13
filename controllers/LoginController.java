package controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import models.App;
import models.Result;
import models.User;
import models.enums.SecurityQuestion;
import models.enums.types.Gender;
import models.enums.commands.LoginCommands;

import models.App;
import static models.enums.types.Gender.getGenderByString;

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
                               String confirmedPassword,
                               String nickname,
                               String email,
                               String genderStr) {
        if (!LoginCommands.USERNAME_REGEX.matches(username)) {
            return new Result(false, "Username invalid.");
        }
        if (!LoginCommands.PASSWORD_REGEX.matches(password)) {
            return new Result(false, "Password invalid.");
        }
        if (!LoginCommands.EMAIL_REGEX.matches(email)) {
            return new Result(false, "Email format invalid.");
        }
        if (getUserByUsername(username) != null) {
            return new Result(false, "Username already exists.");
        }
        if (getUserByEmail(email) != null) {
            return new Result(false, "Email already in use.");
        }
        if (!confirmedPassword.equals(password)) {
            return new Result(false, "Passwords do not match.");
        }

        Gender gender = getGenderByString(genderStr);
        String hash = hashSha256(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(hash);
        user.setEmail(email);
        user.setGender(gender);
        user.setNickname(nickname);
        user.setQAndA(new HashMap<>()); // fix this part!!!!!!!!!!
        App.getUsers().add(user);
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

    public Result forgotPassword(String username, String email, Scanner scanner) {
        User user = getUserByUsername(username);

        if (user == null || !user.getEmail().equalsIgnoreCase(email)) {
            return new Result(false, "Username/email mismatch.");
        }

        Result questionResult = askSecurityQuestion(user);
        if (!questionResult.success()) {
            return new Result(false, "No security question set.");
        }

        System.out.println("Security Question: " + questionResult.message());
        System.out.print("Please enter your answer: ");
        String userAnswer = scanner.nextLine().trim();

        Result validationResult = validateSecurityQuestion(user, userAnswer);
        if (!validationResult.success()) {
            return new Result(false, "Incorrect answer to security question.");
        }

        Result generatedPasswordResult = randomPasswordGenerator();
        String newPwd = generatedPasswordResult.message();
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

    public Result pickSecurityQuestion(User user, SecurityQuestion question, String answer) {
        if (user == null) {
            return new Result(false, "User not found.");
        }
        user.getQAndA().put(question, answer);
        return new Result(true, "Security question saved.");
    }


    public Result validateSecurityQuestion(User user, String answer) {
        if (user == null || user.getQAndA() == null) {
            return new Result(false, "No question to validate.");
        }
        SecurityQuestion q = user.getQAndA().keySet().iterator().next();
        String correct = user.getQAndA().get(q);
        if (correct.equalsIgnoreCase(answer)) {
            return new Result(true, "Security validated.");
        }
        return new Result(false, "Incorrect answer.");
    }
}