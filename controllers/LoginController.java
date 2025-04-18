package controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import models.Result;
import models.User;
import models.enums.SecurityQuestion;
import models.enums.types.Gender;
import models.enums.commands.LoginCommands;

public class LoginController {
    public static Map<String, User> users = new HashMap<>();

    public static User getUserByUsername(String username) {
        return users.get(username);
    }

    public Result registerUser(String username,
                               String password,
                               String email,
                               Gender gender) {
        if (!LoginCommands.USERNAME_REGEX.matches(username)) {
            return new Result(false, "Username invalid.");
        }
        if (!LoginCommands.PASSWORD_REGEX.matches(password)) {
            return new Result(false, "Password invalid.");
        }
        if (!LoginCommands.EMAIL_REGEX.matches(email)) {
            return new Result(false, "Email format invalid.");
        }
        if (users.containsKey(username)) {
            return new Result(false, "Username already exists.");
        }
        for (User u : users.values()) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return new Result(false, "Email already in use.");
            }
        }
        String hash = hashSha256(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(hash);
        user.setEmail(email);
        user.setGender(gender);
        user.setQAndA(new HashMap<>()); // fix this part!!!!!!!!!!
        users.put(username, user);
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
        return new Result(true, "Login successful.");
    }

    public Result forgotPassword(String username, String email) {
        User user = getUserByUsername(username);
        if (user == null || !user.getEmail().equalsIgnoreCase(email)) {
            return new Result(false, "Username/email mismatch.");
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