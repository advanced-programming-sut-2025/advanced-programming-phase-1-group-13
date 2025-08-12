package com.ap_project.server.controller;

import com.ap_project.common.models.network.Message;
import com.ap_project.common.models.network.MessageType;
import com.ap_project.common.utils.JSONUtils;
import com.ap_project.server.GameServer;
import com.ap_project.server.models.LobbyData;

import java.io.*;
import java.net.Socket;
import java.util.*;

import static com.ap_project.server.GameServer.lobbies;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private String nickname;

    public ClientHandler(Socket socket) {
        this.socket = socket;
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
            case SET_USERNAME: {
                this.nickname = (String) body.get("username");
                System.out.println("[Server] Username set to: " + this.nickname);
                break;
            }

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

            case START_GAME: {
                handleStartGame((String) body.get("lobbyId"));
                break;
            }

            case REQUEST_LOBBY_INFO: {
                handleRequestLobbyInfo((String) body.get("lobbyId"));
                break;
            }

            default:
                sendError("Unknown command: " + command);
                break;
        }
    }

    private void handleRequestLobbyInfo(String lobbyId) {
        LobbyData lobby = lobbies.get(lobbyId);
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
        LobbyData lobby = new LobbyData(lobbyId, lobbyName, password, isPrivate, isVisible, this);

        lobbies.put(lobbyId, lobby);
        lobby.addPlayer(this);

        sendMessage(MessageType.LOBBY_CREATED, lobbyId);
        sendLobbyListToAll();
    }

    private void handleJoinLobby(String lobbyId, String providedPassword) {
        LobbyData lobby = lobbies.get(lobbyId);
        if (lobby == null) {
            sendError("Lobby not found");
        } else if (lobby.isFull()) {
            sendError("Lobby is full");
        } else if (lobby.isPrivate() && !lobby.getPassword().equals(providedPassword)) {
            sendError("Wrong password");
        } else {
            for (ClientHandler player : lobby.getPlayers()) {
                if (player.getNickname().equals(nickname)) {
                    sendError("You are already in this lobby.");
                    return;
                }
            }
            lobby.addPlayer(this);
            sendMessage(MessageType.JOIN_SUCCESS, lobbyId);
            sendLobbyListToAll();
        }
    }

    private void handleLeaveLobby(String lobbyId) {
        LobbyData lobby = lobbies.get(lobbyId);
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
        LobbyData lobby = lobbies.get(lobbyId);
        if (lobby == null) {
            sendError("Lobby not found");
        } else {
            // TODO
        }
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
            for (LobbyData lobby : lobbies.values()) {
                if (lobby.getPlayers().size() == 1 && (now - lobby.getLastJoinTime()) > 5 * 60 * 1000) {
                    notUsedLobbyIds.add(lobby.getLobbyId());
                }
            }

            for (String id : notUsedLobbyIds) {
                lobbies.remove(id);
            }

            List<String> lobbyInfo = new ArrayList<>();
            for (LobbyData lobby : lobbies.values()) {
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

    public String getNickname() {
        return nickname;
    }

    public void sendError(String message) {
        sendMessage(MessageType.ERROR, message);
    }
}
