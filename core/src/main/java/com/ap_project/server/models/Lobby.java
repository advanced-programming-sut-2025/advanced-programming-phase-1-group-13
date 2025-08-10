package com.ap_project.server.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lobby {
    private final int id;
    private final String name;
    private final String password;
    public final boolean isPrivate;
    public final boolean isVisible;
    private final List<String> players = new ArrayList<>();
    private final String admin;
    private final Instant creationTime;
    private final int MAX_CAPACITY = 4;

    public Lobby(String name, String password, boolean isPrivate, boolean isVisible, String admin) {
        this.id = (new Random()).nextInt(Integer.MAX_VALUE);
        this.name = name;
        this.password = password;
        this.isPrivate = isPrivate;
        this.isVisible = isVisible;
        this.admin = admin;
        this.creationTime = Instant.now();
        this.players.add(admin);
    }

    public boolean addPlayer(String player) {
        if (players.size() < MAX_CAPACITY) {
            return players.add(player);
        }
        return false;
    }

    public void removePlayer(String player) {
        players.remove(player);
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public boolean isFull() {
        return players.size() >= MAX_CAPACITY;
    }

    public String getAdmin() {
        return admin;
    }

    public int getId() {
        return id;
    }

    public List<String> getPlayers() {
        return players;
    }

    public boolean checkPassword(String input) {
        return password == null || password.equals(input);
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public String getName() {
        return name;
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

    public int getMAX_CAPACITY() {
        return MAX_CAPACITY;
    }
}
