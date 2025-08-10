package com.ap_project.client.models.network;

import com.ap_project.server.GameServer;
import com.ap_project.server.models.LobbyData;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ap_project.server.GameServer.lobbies;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String nickname;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            GameServer.clients.add(this);
            System.out.println("client handler");

            while (true) {
                String command = (String) in.readObject();
                Object data = in.readObject();

                System.out.println("[Server] Received command: " + command);
                handleCommand(command, data);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[Server] Client disconnected: " + e.getMessage());
        } finally {
            try {
                GameServer.clients.remove(this);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleCommand(String command, Object data) {
        System.out.println("Command: " + command);
        switch (command.toUpperCase()) {
            case "SET_USERNAME":
                if (data instanceof String[]) {
                    this.nickname = ((String[]) data)[0];
                    System.out.println("[Server] Username set to: " + this.nickname);
                } else {
                    sendError("Invalid data for SET_USERNAME");
                }
                break;
            case "CREATE_LOBBY":
                if (data instanceof String[]) {
                    String[] argsCreate = (String[]) data;

                    if (argsCreate.length >= 4) {
                        handleCreateLobby(argsCreate);
                    }
                } else {
                    sendError("Invalid data for CREATE_LOBBY");
                }
                break;
            case "REFRESH_LOBBIES":
                sendLobbyList();
                break;
            case "JOIN_LOBBY":
                if (data instanceof String[]) {
                    String[] argsJoin = (String[]) data;
                    if (argsJoin.length >= 1)
                        handleJoinLobby(argsJoin);
                } else {
                    sendError("Invalid data for JOIN_LOBBY");
                }
                break;
            case "LEAVE_LOBBY":
                if (data instanceof String[]) {
                    String[] argsJoin = (String[]) data;
                    if (argsJoin.length >= 1)
                        handleLeaveLobby(argsJoin);
                } else {
                    sendError("Invalid data for JOIN_LOBBY");
                }
                break;
            case "START_GAME":
                if (data instanceof String[]) {
                    String[] argsJoin = (String[]) data;
                    if (argsJoin.length >= 1)
                        handleStartGame(argsJoin);
                } else {
                    sendError("Invalid data for JOIN_LOBBY");
                }
                break;
            case "REQUEST_LOBBY_INFO":
                if (data instanceof String[]) {
                    String[] args = (String[]) data;
                    if (args.length >= 1) {
                        handleRequestLobbyInfo(args[0]);
                    } else {
                        sendError("No lobbyId provided");
                    }
                } else {
                    sendError("Invalid data for REQUEST_LOBBY_INFO");
                }
                break;
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
        if (players.length() > 0) {
            players.deleteCharAt(players.length() - 1);
        }

        String lobbyInfoString = lobby.getLobbyId() + "," +
            lobby.getLobbyName() + "," +
            players + "," +
            lobby.isPrivate() + "," +
            lobby.isVisible();

        sendMessage("LOBBY_INFO", lobbyInfoString);
    }

    private void handleCreateLobby(String[] parts) {
        String lobbyName = parts[0];
        String password = parts[1];
        boolean isPrivate = Boolean.parseBoolean(parts[2]);
        boolean isVisible = Boolean.parseBoolean(parts[3]);

        String lobbyId = UUID.randomUUID().toString().substring(0, 8);
        LobbyData lobby = new LobbyData(lobbyId, lobbyName, password, isPrivate, isVisible, this);

        lobbies.put(lobbyId, lobby);
        lobby.addPlayer(this);

        sendMessage("LOBBY_CREATED", lobbyId);
        sendLobbyListToAll();
    }

    private void handleJoinLobby(String[] parts) {
        String lobbyId = parts[0];
        String providedPassword = parts.length >= 2 ? parts[1] : "";

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
                }
            }
            lobby.addPlayer(this);
            sendMessage("JOIN_SUCCESS", lobbyId);
            sendLobbyListToAll();
        }
    }

    private void handleLeaveLobby(String[] parts) {
        String lobbyId = parts[0];

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

    private void handleStartGame(String[] parts) {
        String lobbyId = parts[0];

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

            out.writeObject("LOBBY_LIST");
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
            out.writeObject(lobbyInfo);
            out.flush();
        } catch (IOException e) {
            System.out.println("Failed to send lobby list: " + e.getMessage());
        }
    }

    public void sendMessage(String command, Object data) {
        try {
            out.writeObject(command);
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            System.out.println("Failed to send message: " + e.getMessage());
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void sendError(String message) {
        sendMessage("ERROR", message);
    }
}
