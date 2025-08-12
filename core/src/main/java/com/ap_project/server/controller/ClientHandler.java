package com.ap_project.server.controller;

import com.ap_project.common.models.Farm;
import com.ap_project.common.models.Game;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.types.Gender;
import com.ap_project.common.models.network.Message;
import com.ap_project.common.models.network.MessageType;
import com.ap_project.common.utils.JSONUtils;
import com.ap_project.server.GameServer;
import com.ap_project.server.models.Lobby;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

import static com.ap_project.server.GameServer.*;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private User user;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            this.socket.setSoTimeout(300000);
        } catch (SocketException e) {
            System.out.println("[Server] Failed to set socket timeout: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            GameServer.clients.add(this);

            StringBuilder jsonBuffer = new StringBuilder();
            int braceCount = 0;
            boolean inQuotes = false;

            int c;
            while ((c = in.read()) != -1) {
                char character = (char) c;
                jsonBuffer.append(character);

                if (character == '"' && (jsonBuffer.length() == 1 || jsonBuffer.charAt(jsonBuffer.length() - 2) != '\\')) {
                    inQuotes = !inQuotes;
                }

                if (!inQuotes) {
                    if (character == '{') {
                        braceCount++;
                    } else if (character == '}') {
                        braceCount--;

                        if (braceCount == 0) {
                            String completeJson = jsonBuffer.toString();

                            try {
                                Message message = JSONUtils.fromJson(completeJson);
                                handleCommand(message);
                            } catch (Exception e) {
                                System.out.println("[Server] Error parsing JSON: " + e.getMessage());
                                e.printStackTrace();
                                sendError("Invalid JSON format");
                            }

                            jsonBuffer.setLength(0);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("[Server] Client disconnected: " + e.getMessage());
        } finally {
            try {
                socket.close();
                GameServer.clients.remove(this);
            } catch (IOException e) {
                System.out.println("[Server] Error closing client socket: " + e.getMessage());
            }
        }
    }

    private void handleCommand(Message message) {
        MessageType command = message.getType();

        HashMap<String, Object> body = message.getBody();
        if (body == null) {
            sendError("Invalid data for " + command);
            return;
        }

        switch (command) {
            case SIGNUP: {
                String username = body.get("username").toString();
                String password = body.get("password").toString();
                String nickname = body.get("nickname").toString();
                String email = body.get("email").toString();
                Gender gender = Gender.getGenderByName(body.get("gender").toString());

                handleSignup(username, password, nickname, email, gender);
                break;
            }

            case LOGIN: {
                String username = message.getBody().get("username").toString();
                String passwordHash = message.getBody().get("password").toString();

                handleLogin(username, passwordHash);
                break;
            }

            case REQUESTS_USERS_INFO:
                handleRequestUsersInfo();
                break;

            case CREATE_LOBBY: {
                String password = (String) body.get("password");
                String name = (String) body.get("name");
                boolean isPrivate = (Boolean) body.get("isPrivate");
                boolean isVisible = (Boolean) body.get("isVisible");

                handleCreateLobby(name, password, isPrivate, isVisible);
                break;
            }

            case REFRESH_LOBBIES:
                sendLobbyList();
                break;

            case JOIN_LOBBY: {
                String lobbyId = (String) body.get("lobbyId");
                String providedPassword = (String) body.get("password");

                handleJoinLobby(lobbyId, providedPassword);
                break;
            }

            case LEAVE_LOBBY: {
                handleLeaveLobby((String) body.get("lobbyId"));
                break;
            }

            case REQUEST_LOBBY_INFO: {
                handleRequestLobbyInfo((String) body.get("lobbyId"));
                break;
            }

            case START_GAME: {
                handleStartGame((String) body.get("lobbyId"));
                break;
            }

            case CHOSE_MAP: {
                String username = (String) body.get("username");
                String id = (String) body.get("id");
                Integer mapNumber = Integer.parseInt((String) body.get("mapNumber"));

                Game game = getGameById(id);
                if (game == null) {
                    sendError("Invalid game ID: " + id);
                }
                boolean haveAllPlayersChosenMap = true;
                for (User user : game.getPlayers()) {
                    if (user.getUsername().equals(username)) {
                        user.setFarm(new Farm(mapNumber));
                    }
                    if (user.getFarm() == null) haveAllPlayersChosenMap = false;
                }

                if (haveAllPlayersChosenMap) {
                    Message error = new Message(body, MessageType.GO_TO_GAME);
                    sendMessage(JSONUtils.toJson(error));
                }

                break;
            }

            default:
                sendError("Unknown command: " + command);
                break;
        }
    }

    private void handleSignup(String username, String password, String nickname, String email, Gender gender) {
        try {
            User user = new User(username, password, nickname, email, gender);
            users.add(user);
            System.out.println("[Server] Successfully added user: " + user.getUsername());
        } catch (Exception e) {
            System.out.println("[Server] Error creating user: " + e.getMessage());
        }
    }

    private void handleLogin(String username, String passwordHash) {
        HashMap<String, Object> body = new HashMap<>();

        User user = GameServer.getUserByUsername(username);
        if (user == null) {
            body.put("data", "User not found");
            Message error = new Message(body, MessageType.LOGIN_ERROR);
            sendMessage(JSONUtils.toJson(error));
            return;
        }

        if (!user.getPassword().equals(passwordHash)) {
            body.put("data", "Incorrect password");
            Message error = new Message(body, MessageType.LOGIN_ERROR);
            sendMessage(JSONUtils.toJson(error));
            return;
        }

        body = new HashMap<>();
        body.put("username", user.getUsername());
        body.put("password", user.getPassword());
        body.put("nickname", user.getNickname());
        body.put("email", user.getEmail());
        body.put("gender", user.getGender());

        Message success = new Message(body, MessageType.LOGIN_SUCCESS);
        this.user = user;
        sendMessage(JSONUtils.toJson(success));
        System.out.println("[Server]: Client logged in: " + username);
    }

    private void handleRequestUsersInfo() {
        try {
            List<String> usersInfo = new ArrayList<>();
            for (User user : users) {
                usersInfo.add(user.getUsername() + "    Lobbies: " + getUsersLobbies(user.getUsername()));

            }

            sendMessage(MessageType.USERS_INFO, usersInfo);
        } catch (Exception e) {
            System.out.println("[Server] Failed to send lobby list: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getUsersLobbies(String username) {
        StringBuilder lobbiesString = new StringBuilder();
        for (Lobby lobby : lobbies.values()) {
            for (ClientHandler client : clients) {
                if (client.getUser().getUsername().equals(username)) {
                    lobbiesString.append(lobby.getLobbyName()).append("( ").append(lobby.getLobbyId()).append(" ), ");
                }
            }
        }
        return lobbiesString.toString();
    }

    private void handleRequestLobbyInfo(String lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby == null) {
            sendError("Lobby not found");
            return;
        }

        StringBuilder players = new StringBuilder();
        for (ClientHandler player : lobby.getPlayers()) {
            players.append(player.getNickname()).append("|");
        }
        if (players.length() > 0) players.deleteCharAt(players.length() - 1);

        String lobbyInfoString = lobby.getLobbyId() + "," +
            lobby.getLobbyName() + "," +
            players + "," +
            lobby.isPrivate() + "," +
            lobby.isVisible();

        sendMessage(MessageType.LOBBY_INFO, lobbyInfoString);
    }

    private void handleCreateLobby(String lobbyName, String password, boolean isPrivate, boolean isVisible) {
        String lobbyId = UUID.randomUUID().toString().substring(0, 8);
        Lobby lobby = new Lobby(lobbyId, lobbyName, password, isPrivate, isVisible, this);

        lobbies.put(lobbyId, lobby);
        lobby.addPlayer(this);

        sendMessage(MessageType.LOBBY_CREATED, lobbyId);
        sendLobbyListToAll();
    }

    private void handleJoinLobby(String lobbyId, String providedPassword) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby == null) {
            sendError("Lobby not found");
            return;
        }

        for (ClientHandler client : lobby.getPlayers()) {
            if (client.user.equals(user)) {
                sendError("You are already in this lobby.");
                return;
            }
        }

        if (lobby.isFull()) {
            sendError("Lobby is full");
        } else if (lobby.isPrivate() && !lobby.getPassword().equals(providedPassword)) {
            sendError("Wrong password");
        } else {
            lobby.addPlayer(this);
            sendMessage(MessageType.JOIN_SUCCESS, lobbyId);
            sendLobbyListToAll();
        }
    }

    private void handleLeaveLobby(String lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby == null) {
            sendError("Lobby not found");
        } else {
            lobby.removePlayer(this);
            if (lobby.getPlayers().isEmpty()) {
                lobbies.remove(lobbyId);
            }
            sendLobbyListToAll();
        }
    }

    private void handleStartGame(String lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby == null) {
            sendError("Lobby not found");
            return;
        }

        if (lobby.getPlayers().size() < 2) {
            sendError("There must be at least two players to start the game");
            return;
        }

        if (!lobby.getAdmin().equals(this)) {
            sendError("Only the admin can start the game.");
            return;
        }

        List<ClientHandler> disconnectedPlayers = new ArrayList<>();
        for (ClientHandler client : lobby.getPlayers()) {
            if (!client.isConnected()) {
                disconnectedPlayers.add(client);
            }
        }

        if (!disconnectedPlayers.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder();
            for (ClientHandler client : disconnectedPlayers) {
                errorMsg.append(client.getNickname()).append(", ");
                lobby.removePlayer(client);
            }
            errorMsg.delete(errorMsg.length()-2, errorMsg.length());
            errorMsg.append(" not online.");
            sendError(errorMsg.toString());
            return;
        }

        ArrayList<User> users = new ArrayList<>();

        HashMap<String, Object> body = new HashMap<>();
        body.put("id", lobbyId);

        for (ClientHandler client : lobby.getPlayers()) {
            User player = client.user;

            HashMap<String, Object> playerData = new HashMap<>();
            playerData.put("username", player.getUsername());
            playerData.put("password", player.getPassword());
            playerData.put("nickname", player.getNickname());
            playerData.put("email", player.getEmail());
            playerData.put("gender", player.getGender());

            String index = String.valueOf(lobby.getPlayers().indexOf(client));
            body.put("player" + index, playerData);

            users.add(player);
        }

        games.add(new Game(users, lobbyId, true));

        Message message = new Message(body, MessageType.START_CHOOSE_MAP);
        sendMessageToAllInLobby(lobby, JSONUtils.toJson(message));
    }

    private void sendLobbyListToAll() {
        for (ClientHandler client : GameServer.clients) {
            client.sendLobbyList();
        }
    }

    public void sendLobbyList() {
        try {
            List<String> notUsedLobbyIds = new ArrayList<>();
            long now = System.currentTimeMillis();
            for (Lobby lobby : lobbies.values()) {
                if (lobby.getPlayers().size() == 1 && (now - lobby.getLastJoinTime()) > 5 * 60 * 1000) {
                    notUsedLobbyIds.add(lobby.getLobbyId());
                }
            }

            for (String id : notUsedLobbyIds) {
                lobbies.remove(id);
            }

            List<String> lobbyInfo = new ArrayList<>();
            for (Lobby lobby : lobbies.values()) {
                StringBuilder playersUsernames = new StringBuilder();
                for (ClientHandler client : lobby.getPlayers()) {
                    playersUsernames.append(client.getNickname()).append("-");
                }
                if (playersUsernames.length() > 0) {
                    playersUsernames.deleteCharAt(playersUsernames.length() - 1);
                }

                lobbyInfo.add(lobby.getLobbyId() + "," +
                    lobby.getLobbyName() + "," +
                    playersUsernames + "," +
                    lobby.isPrivate() + "," +
                    lobby.isVisible()
                );
            }

            sendMessage(MessageType.LOBBY_LIST, lobbyInfo);
        } catch (Exception e) {
            System.out.println("[Server] Failed to send lobby list: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendMessageToAllInLobby(Lobby lobby, String message) {

        System.out.println("LINE 439 BEFORE THE EXCEPTION");
        for (ClientHandler client : lobby.getPlayers()) {
            client.sendMessage(message);
        }
    }

    public void sendMessage(MessageType type, Object data) {
        try {
            Message message = new Message();
            message.setType(type);

            HashMap<String, Object> body = new HashMap<>();
            body.put("data", data);
            message.setBody(body);

            String jsonString = JSONUtils.toJson(message);
            out.write(jsonString);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            System.out.println("[Server] Failed to send message: " + e.getMessage());
        }
    }

    public void sendMessage(String jsonMessage) {
        try {
            out.write(jsonMessage);
            out.newLine();
            out.flush();
            System.out.println("[Server] Sent message: " + jsonMessage);
        } catch (IOException e) {
            System.out.println("[Server] Failed to send message: " + e.getMessage());
        }
    }

    public User getUser() {
        return user;
    }

    public String getNickname() {
        if (user == null) return "null";
        return user.getNickname();
    }

    public void sendError(String message) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("data", message);
        sendMessage(JSONUtils.toJson(new Message(body, MessageType.ERROR)));
    }

    public boolean isConnected() {
        if (socket.isClosed()) return false;

        try {
            socket.sendUrgentData(0xFF);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
