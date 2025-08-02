package com.ap_project.controllers.pregame;

import com.ap_project.views.pregame.LobbyMenuView;
import com.ap_project.network.GameClient;
import java.util.ArrayList;
import java.util.List;

public class LobbyMenuController {
    private LobbyMenuView view;
    private final GameClient client;

    public LobbyMenuController(GameClient client) {
        this.client = client;
    }

    public void setView(LobbyMenuView view) {
        this.view = view;
    }

    public void createLobby(String name, String password, boolean isPrivate) {
        if (name == null || name.trim().isEmpty()) {
            view.showError("Lobby name cannot be empty.");
            return;
        }

        // Build and send a create lobby request to the server
        client.sendMessage("CREATE_LOBBY", new String[]{name, password != null ? password : "", Boolean.toString(isPrivate)});
    }

    public void refreshLobbyList() {
        client.sendMessage("REFRESH_LOBBIES", new String[]{});
    }

    public void joinLobbyFromList(String lobbyInfoString) {
        // You can parse the lobby ID from the string if needed
        String[] parts = lobbyInfoString.split("::");
        if (parts.length > 0) {
            String lobbyId = parts[0];
            client.sendMessage("JOIN_LOBBY", new String[]{lobbyId});
        } else {
            view.showError("Invalid lobby entry.");
        }
    }

    // Called by networking system when server responds with updated lobby list
    public void updateLobbyListFromServer(List<String> lobbyInfoList) {
        if (view != null) {
            view.updateLobbyList(lobbyInfoList);
        }
    }

    public void showError(String message) {
        if (view != null) view.showError(message);
    }
}
