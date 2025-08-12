package com.ap_project.server.models;

import com.ap_project.server.controller.ClientHandler;

import java.util.ArrayList;
import java.util.List;

public class LobbyData {
    private final String lobbyId;
    private final String lobbyName;
    private final String password;
    private final boolean isPrivate;
    private final boolean isVisible;
    private ClientHandler admin;
    private final List<ClientHandler> players = new ArrayList<>();
    private long lastJoinTime;
    private static final int MAX_CAPACITY = 4;

    public LobbyData(String lobbyId, String lobbyName, String password, boolean isPrivate, boolean isVisible, ClientHandler admin) {
        this.lobbyId = lobbyId;
        this.lobbyName = lobbyName;
        this.password = password;
        this.isPrivate = isPrivate;
        this.isVisible = isVisible;
        this.admin = admin;
        this.players.add(admin);
        this.lastJoinTime = System.currentTimeMillis();
    }

    public boolean isFull() {
        return players.size() >= MAX_CAPACITY;
    }

    public void addPlayer(ClientHandler player) {
        if (!players.contains(player) && !isFull()) {
            players.add(player);
        }
        this.lastJoinTime = System.currentTimeMillis();
    }

    public void setAdmin(ClientHandler newAdmin) {
        this.admin = newAdmin;
    }

    public void removePlayer(ClientHandler player) {
        players.remove(player);
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public List<ClientHandler> getPlayers() {
        return players;
    }

    public ClientHandler getAdmin() {
        return admin;
    }

    public long getLastJoinTime() {
        return lastJoinTime;
    }
}
