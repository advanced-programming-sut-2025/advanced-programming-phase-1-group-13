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
        this.nickname = "Nick";
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
            case "CREATE_LOBBY":
                System.out.println("LINE 50");

                if (data instanceof String[]) {
                    System.out.println("Creating lobby");

                    String[] argsCreate = (String[]) data;

                    if (argsCreate.length >= 3) {
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
            default:
                sendError("Unknown command: " + command);
                break;
        }
    }

    private void handleCreateLobby(String[] parts) {
        String lobbyName = parts[0];
        String password = parts[1];
        boolean isPrivate = Boolean.parseBoolean(parts[2]);

        String lobbyId = UUID.randomUUID().toString().substring(0, 8);
        LobbyData lobby = new LobbyData(lobbyId, lobbyName, password, isPrivate, this);

        lobbies.put(lobbyId, lobby);
        System.out.println("new lobby created and now we have :" + lobbies.size());
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
            lobby.addPlayer(this);
            sendMessage("JOIN_SUCCESS", lobbyId);
            sendLobbyListToAll();
        }
    }

    private void sendLobbyListToAll() {
        for (ClientHandler client : GameServer.clients) {
            client.sendLobbyList();
        }
    }

    public void sendLobbyList() {
        try {
            out.writeObject("LOBBY_LIST");
            List<String> lobbyInfo = new ArrayList<>();
            for (LobbyData lobby : lobbies.values()) {
                lobbyInfo.add(lobby.getLobbyId() + "," +
                    lobby.getLobbyName() + "," +
                    lobby.getPlayers().size() + "/4," +
                    lobby.isPrivate());
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
