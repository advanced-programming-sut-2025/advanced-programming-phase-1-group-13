package com.ap_project.lobby;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LobbyManager {
    private final Map<Integer, Lobby> lobbies = new HashMap<>();

    public Lobby createLobby(String name, String password, boolean isPrivate, boolean isVisible, String admin) {
        Lobby lobby = new Lobby(name, password, isPrivate, isVisible, admin);
        lobbies.put(lobby.getId(), lobby);
        return lobby;
    }

    public Lobby getLobbyById(String id) {
        return lobbies.get(id);
    }

    public boolean joinLobby(String id, String player, String password) {
        Lobby lobby = lobbies.get(id);
        if (lobby == null || lobby.isFull()) return false;
        if (!lobby.checkPassword(password)) return false;
        return lobby.addPlayer(player);
    }

    public void leaveLobby(String id, String player) {
        Lobby lobby = lobbies.get(id);
        if (lobby != null) {
            lobby.removePlayer(player);
            if (lobby.isEmpty()) {
                lobbies.remove(id);
            }
        }
    }

    public List<Lobby> getPublicVisibleLobbies() {
        return lobbies.values().stream()
            .filter(l -> !l.isPrivate && l.isVisible)
            .collect(Collectors.toList());
    }

    public void removeInactiveLobbies() {
        Instant now = Instant.now();
        lobbies.values().removeIf(lobby ->
            Duration.between(lobby.getCreationTime(), now).toMinutes() >= 5 &&
                lobby.getPlayers().size() == 1);
    }
}
