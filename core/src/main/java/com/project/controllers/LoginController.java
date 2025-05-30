package com.project.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import com.project.models.App;
import com.project.models.Result;
import com.project.models.User;
import com.project.models.enums.Menu;
import com.project.models.enums.SecurityQuestion;
import com.project.models.enums.types.Gender;
import com.project.models.enums.commands.LoginCommands;

import static com.project.models.App.getUserByEmail;
import static com.project.models.App.getUserByUsername;

public class LoginController {
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
            finalPassword = randomPasswordGenerator().message();
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


    public Result registerUser(String username,
                               String nickname,
                               String email,
                               String genderString,
                               boolean generateRandomPassword) {
        return registerUser(username, null, null, nickname, email, genderString, generateRandomPassword);
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

    public Result randomPasswordGenerator() {
        int len = 12;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return new Result(true, sb.toString());
    }

    public Result login(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) {
            return new Result(false, "User not found.");
        }

        if (!password.equals(user.getPassword())) {
            return new Result(false, "Incorrect password.");
        }

        App.setLoggedIn(user);
        App.setCurrentMenu(Menu.MAIN_MENU);
        return new Result(true, "Login successful. You are now in Main Menu.");
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
        String securityQuestion = (new ArrayList<>(user.getQAndA().keySet())).get(index).getQuestion();//
        System.out.println("Answer the following security question. \nUse this format: \"answer -a <your answer>\"");
        return new Result(true, securityQuestion);
    }

    public Result validateSecurityQuestion(String username, String question, String answer) {
        SecurityQuestion securityQuestion = SecurityQuestion.getSecurityQuestionByQuestion(question);
        User user = getUserByUsername(username);
        if (user == null) {
            return new Result(false, "User not found.");
        }
        String correctAnswer = user.getQAndA().get(securityQuestion);
        String newPassword = randomPasswordGenerator().message();
        if (correctAnswer.equals(answer)) {
            user.setPassword(newPassword);
            return new Result(true, "Correct answer! Your new password is: " + newPassword);
        }
        return new Result(false, "Incorrect answer.");
    }

    public Result exit() {
        App.setCurrentMenu(Menu.EXIT);
        return new Result(true, "Exiting ... Bye Bye!");
    }
}
