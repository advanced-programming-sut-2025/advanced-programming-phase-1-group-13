package com.ap_project.common;

import com.ap_project.common.models.*;
import com.ap_project.common.models.enums.*;
import com.ap_project.common.models.enums.environment.Direction;
import com.ap_project.common.models.enums.types.Gender;
import com.ap_project.common.models.inventory.Backpack;
import com.ap_project.common.utils.JSONUtils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import static java.sql.DriverManager.getConnection;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:users.db";
    private static final String DB_GAMES = "jdbc:sqlite:games.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            initializeDatabases();
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void initializeDatabases() {
        createUserDatabase();
        createGameDatabase();
    }


    public static void initializeDatabase() {
        createUserDatabase();
        createGameDatabase();
    }

    private static void createUserDatabase() {
        try (Connection conn = getConnection(DB_URL)) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet tables = meta.getTables(null, null, "users", null)) {
                if (!tables.next()) {
                    createUserTable();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error initializing user database: " + e.getMessage());
        }
    }

    private static void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
            "username TEXT PRIMARY KEY," +
            "password TEXT NOT NULL," +
            "nickname TEXT NOT NULL," +
            "email TEXT NOT NULL," +
            "gender TEXT NOT NULL," +
            "avatarNumber INTEGER DEFAULT 1," +
            "woodCount INTEGER DEFAULT 0," +
            "stoneCount INTEGER DEFAULT 0," +
            "energy INTEGER DEFAULT 200," +
            "maxEnergy INTEGER DEFAULT 200," +
            "isEnergyUnlimited INTEGER DEFAULT 0," +
            "balance REAL DEFAULT 0," +
            "spentMoney REAL DEFAULT 0," +
            "mostEarnedMoney INTEGER DEFAULT 0," +
            "numberOfGames INTEGER DEFAULT 0," +
            "isDepressed INTEGER DEFAULT 0," +
            "isInVillage INTEGER DEFAULT 0," +
            "spouseUsername TEXT," +
            "rejectionTime TEXT," +
            "positionX INTEGER DEFAULT 0," +
            "positionY INTEGER DEFAULT 0," +
            "direction TEXT DEFAULT 'DOWN'," +
            "currentTool TEXT," +
            "backpack TEXT," +
            "skillLevels TEXT," +
            "skillPoints TEXT," +
            "learntCraftRecipes TEXT," +
            "learntCookingRecipes TEXT," +
            "securityQuestions TEXT," +
            "answers TEXT," +
            "gifts TEXT," +
            "relationships TEXT," +
            "marriageRequests TEXT," +
            "privateChats TEXT," +
            "unreadMessages TEXT," +
            "music TEXT," +
            "defaultEmojis TEXT," +
            "defaultReactions TEXT" +
            ")";

        executeUpdate(DB_URL, sql);
    }

    private static void createGameDatabase() {
        try (Connection conn = getConnection(DB_GAMES)) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet tables = meta.getTables(null, null, "games", null)) {
                if (!tables.next()) {
                    createGameTable();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error initializing game database: " + e.getMessage());
        }
    }


    public static void updateUser(User user) {
        String sql = "UPDATE users SET " +
            "password = ?, " +
            "nickname = ?, " +
            "email = ?, " +
            "avatarNumber = ?, " +
            "woodCount = ?, " +
            "stoneCount = ?, " +
            "energy = ?, " +
            "maxEnergy = ?, " +
            "isEnergyUnlimited = ?, " +
            "balance = ?, " +
            "spentMoney = ?, " +
            "mostEarnedMoney = ?, " +
            "numberOfGames = ?, " +
            "isDepressed = ?, " +
            "isInVillage = ?, " +
            "spouseUsername = ?, " +
            "rejectionTime = ?, " +
            "positionX = ?, " +
            "positionY = ?, " +
            "direction = ?, " +
            "currentTool = ?, " +
            "backpack = ?, " +
            "skillLevels = ?, " +
            "skillPoints = ?, " +
            "learntCraftRecipes = ?, " +
            "learntCookingRecipes = ?, " +
            "securityQuestions = ?, " +
            "answers = ?, " +
            "gifts = ?, " +
            "relationships = ?, " +
            "marriageRequests = ?, " +
            "privateChats = ?, " +
            "unreadMessages = ?, " +
            "music = ?, " +
            "defaultEmojis = ?, " +
            "defaultReactions = ? " +
            "WHERE username = ?";

        try (Connection conn = getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getNickname());
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getAvatarNumber());
            pstmt.setInt(5, user.getWoodCount());
            pstmt.setInt(6, user.getStoneCount());
            pstmt.setInt(7, user.getEnergy());
            pstmt.setInt(8, user.getMaxEnergy());
            pstmt.setBoolean(9, user.isEnergyUnlimited());
            pstmt.setDouble(10, user.getBalance());
            pstmt.setDouble(11, user.getSpentMoney());
            pstmt.setInt(12, user.getMostEarnedMoney());
            pstmt.setInt(13, user.getNumberOfGames());
            pstmt.setBoolean(14, user.isDepressed());
            pstmt.setBoolean(15, user.isInVillage());

            pstmt.setString(16, user.getSpouse() != null ? user.getSpouse().getUsername() : null);
            pstmt.setString(17, user.getRejectionTime() != null ? user.getRejectionTime().toString() : null);

            Position pos = user.getPosition();
            pstmt.setInt(18, pos != null ? pos.getX() : 0);
            pstmt.setInt(19, pos != null ? pos.getY() : 0);
            pstmt.setString(20, user.getDirection() != null ? user.getDirection().name() : "DOWN");

            pstmt.setString(21, user.getCurrentTool() != null ?
                user.getCurrentTool().getName() : null);

            pstmt.setString(22, user.getBackpack().toString());
            pstmt.setString(23, user.getSkillLevels().toString());
            pstmt.setString(24, user.getSkillPoints().toString());
            pstmt.setString(25, user.getLearntCraftRecipes().toString());
            pstmt.setString(26, user.getLearntCookingRecipes().toString());

            HashMap<String, String> securityQA = new HashMap<>();
            user.getQAndA().forEach((q, a) -> securityQA.put(q.name(), a));
            pstmt.setString(27, securityQA.toString());

            pstmt.setString(28, user.getGifts().toString());

            pstmt.setString(32, user.getUnreadPrivateMessagesMap().toString());

            pstmt.setString(33, user.getMusic().toString());
            pstmt.setString(34, user.getDefaultEmojis().toString());
            pstmt.setString(35, user.getDefaultReactions().toString());

            pstmt.setString(36, user.getUsername());

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                System.err.println("No user found with username: " + user.getUsername());
            } else {
                System.out.println("User " + user.getUsername() + " updated successfully");
            }
        } catch (SQLException e) {
            System.err.println("Error updating user " + user.getUsername() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static HashMap<String, Boolean> serializeRelationshipMap(HashMap<User, Boolean> map) {
        HashMap<String, Boolean> serialized = new HashMap<>();
        map.forEach((user, status) -> serialized.put(user.getUsername(), status));
        return serialized;
    }

    public static boolean registerUser(User user) {
        if (userExists(user.getUsername())) {
            System.err.println("Username " + user.getUsername() + " already exists");
            return false;
        }

        String sql = "INSERT INTO users(username, password, nickname, email, gender, " +
            "securityQuestion, answer) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getNickname());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getGender().toString());
            pstmt.setString(6, user.getQAndA().keySet().toString());
            pstmt.setString(7, user.getQAndA().values().iterator().next());

            pstmt.executeUpdate();
            System.out.println("User '" + user.getUsername() + "' registered successfully");
            return true;
        } catch (SQLException e) {
            System.err.println("Error registering user '" + user.getUsername() + "': " + e.getMessage());
            return false;
        }
    }

    private static boolean userExists(String username) {
        String sql = "SELECT username FROM users WHERE username = ?";

        try (Connection conn = getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error checking username existence: " + e.getMessage());
            return true;
        }
    }

    private static void createGameTable() {
        String sql = "CREATE TABLE IF NOT EXISTS games (" +
            "gameId TEXT PRIMARY KEY," +
            "gameData TEXT NOT NULL," +
            "players TEXT," +
            "time TEXT," +
            "weather TEXT," +
            "trades TEXT," +
            "gifts TEXT," +
            "quests TEXT" +
            ")";

        executeUpdate(DB_GAMES, sql);
    }

    private static void executeUpdate(String dbUrl, String sql) {
        try (Connection conn = getConnection(dbUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error executing SQL: " + e.getMessage());
        }
    }

    public static void addUser(User user) {
        String sql = "INSERT INTO users(username, password, nickname, email, gender, avatarNumber) " +
            "VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getNickname());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getGender().toString());
            pstmt.setInt(6, user.getAvatarNumber());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }

    public static User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("nickname"),
                    rs.getString("email"),
                    Gender.valueOf(rs.getString("gender"))
                );

                user.setAvatarNumber(rs.getInt("avatarNumber"));
                user.setEnergy(rs.getInt("energy"));
                user.setMaxEnergy(rs.getInt("maxEnergy"));
                user.setEnergyUnlimited(rs.getBoolean("isEnergyUnlimited"));
                user.setBalance(rs.getDouble("balance"));
                user.setSpentMoney(rs.getDouble("spentMoney"));
                user.setMostEarnedMoney(rs.getInt("mostEarnedMoney"));
                user.setNumberOfGames(rs.getInt("numberOfGames"));
                user.setDepressed(rs.getBoolean("isDepressed"));
                user.setInVillage(rs.getBoolean("isInVillage"));


                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error getting user: " + e.getMessage());
        }
        return null;
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                try {
                    User user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nickname"),
                        rs.getString("email"),
                        Gender.valueOf(rs.getString("gender"))
                    );

                    user.setAvatarNumber(rs.getInt("avatarNumber"));
                    user.setEnergy(rs.getInt("energy"));
                    user.setMaxEnergy(rs.getInt("maxEnergy"));
                    user.setEnergyUnlimited(rs.getBoolean("isEnergyUnlimited"));
                    user.setBalance(rs.getDouble("balance"));
                    user.setSpentMoney(rs.getDouble("spentMoney"));
                    user.setMostEarnedMoney(rs.getInt("mostEarnedMoney"));
                    user.setNumberOfGames(rs.getInt("numberOfGames"));
                    user.setDepressed(rs.getBoolean("isDepressed"));
                    user.setInVillage(rs.getBoolean("isInVillage"));

                    Position position = new Position(
                        rs.getInt("positionX"),
                        rs.getInt("positionY")
                    );
                    user.setPosition(position);

                    user.setDirection(Direction.valueOf(rs.getString("direction")));

                    String backpackJson = rs.getString("backpack");

                    String spouseUsername = rs.getString("spouseUsername");
                    if (spouseUsername != null && !spouseUsername.isEmpty()) {
                        User spouse = getUserByUsername(spouseUsername);
                        user.setSpouse(spouse);
                    }


                    users.add(user);
                } catch (Exception e) {
                    System.err.println("Error parsing user data for username " +
                        rs.getString("username") + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }


    public static User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("nickname"),
                    rs.getString("email"),
                    Gender.valueOf(rs.getString("gender"))
                );

                user.setAvatarNumber(rs.getInt("avatarNumber"));
                user.setEnergy(rs.getInt("energy"));
                user.setMaxEnergy(rs.getInt("maxEnergy"));
                user.setEnergyUnlimited(rs.getBoolean("isEnergyUnlimited"));
                user.setBalance(rs.getDouble("balance"));
                user.setSpentMoney(rs.getDouble("spentMoney"));
                user.setMostEarnedMoney(rs.getInt("mostEarnedMoney"));
                user.setNumberOfGames(rs.getInt("numberOfGames"));
                user.setDepressed(rs.getBoolean("isDepressed"));
                user.setInVillage(rs.getBoolean("isInVillage"));

                Position position = new Position(
                    rs.getInt("positionX"),
                    rs.getInt("positionY")
                );
                user.setPosition(position);

                user.setDirection(Direction.valueOf(rs.getString("direction")));

                String spouseUsername = rs.getString("spouseUsername");
                if (spouseUsername != null && !spouseUsername.isEmpty()) {
                    User spouse = getUserByUsername(spouseUsername);
                    user.setSpouse(spouse);
                }

                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user by email: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static Game loadGame(String gameId) {
        return null;
    }

    public static void saveGame(Game game) {
        String sql = "INSERT OR REPLACE INTO games (gameId, gameData, players, time, weather, trades, gifts, quests) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(DB_GAMES);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String gameDataJson = game.getGameData().toString();
            String playersJson = game.getPlayers().toString();
            String timeJson = game.getGameState().getTime().toString();
            String weatherJson = game.getGameState().getCurrentWeather() != null ? game.getGameState().getCurrentWeather().name() : null;
            String tradesJson = game.getTrades().toString();
            String questsJson = game.getQuests().toString();
            pstmt.setString(1, game.getId());
            pstmt.setString(2, gameDataJson);
            pstmt.setString(3, playersJson);
            pstmt.setString(4, timeJson);
            pstmt.setString(5, weatherJson);
            pstmt.setString(6, tradesJson);
            pstmt.setString(8, questsJson);

            pstmt.executeUpdate();
            System.out.println("Game " + game.getId() + " saved successfully.");

        } catch (SQLException e) {
            System.err.println("Error saving game " + game.getId() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

}
