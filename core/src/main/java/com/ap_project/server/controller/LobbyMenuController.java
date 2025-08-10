package com.ap_project.server.controller;

import com.ap_project.Main;
import com.ap_project.client.models.network.GameClient;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.client.views.pregame.LobbyMenuView;
import com.ap_project.client.views.pregame.LobbyRoomView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LobbyMenuController {
    private LobbyMenuView view;
    private final GameClient client;

    public LobbyMenuController(GameClient client) {
        this.client = client;
    }

    public void setView(LobbyMenuView view) {
        this.view = view;
        try {
            client.sendMessage("SET_USERNAME", new String[]{App.getLoggedIn().getUsername()});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createLobby(String name, String password, boolean isPrivate, boolean isVisible) {
        System.out.println("[Client] Creating lobby: " + name + ", private=" + isPrivate + ", visible=" + isVisible);

        if (name == null || name.trim().isEmpty()) {
            view.showError("Lobby name cannot be empty.");
            return;
        }

        client.sendMessage("CREATE_LOBBY", new String[]{name, password != null ? password : "", Boolean.toString(isPrivate), Boolean.toString(isVisible)});
    }


    public void refreshLobbyList() {
        client.sendMessage("REFRESH_LOBBIES", new String[]{});
    }

    public void joinLobbyFromList(String lobbyInfoString) {
        String[] parts = lobbyInfoString.split(",");
        if (parts.length >= 4) {
            String lobbyId = parts[0];
            boolean isPrivate = Boolean.parseBoolean(parts[3]);

            if (isPrivate) {
                view.promptPasswordAndJoin(lobbyId);
            } else {
                client.sendMessage("JOIN_LOBBY", new String[]{lobbyId});
            }
        } else {
            view.showError("Invalid lobby data.");
        }
    }

    public void joinLobbyWithPassword(String lobbyId, String password) {
        client.sendMessage("JOIN_LOBBY", new String[]{lobbyId, password});
    }

    public void updateLobbyListFromServer(List<String> lobbyInfoList) {
        if (view != null) {
            view.updateLobbyList(lobbyInfoList);
        }
    }

    public void leaveLobby(String lobbyId) {
        client.sendMessage("LEAVE_LOBBY", new String[]{lobbyId});
    }

    public void startGame(String lobbyId) {
        client.sendMessage("START_GAME", new String[]{lobbyId});
    }

    public void backToLobbyMenu() {
        Main.getMain().setScreen(new LobbyMenuView(this, GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void showError(String message) {
        if (view != null) view.showError(message);
    }

    public void requestLobbyInfo(String lobbyId) {
        client.sendMessage("REQUEST_LOBBY_INFO", new String[]{lobbyId});
    }

    public void openLobbyRoomView(String lobbyInfoString) {
        String[] parts = lobbyInfoString.split(",");
        String lobbyId = parts[0];
        String lobbyName = parts[1];
        String playersStr = parts[2];

        ArrayList<String> players = new ArrayList<>(Arrays.asList(playersStr.split("\\|")));

        LobbyRoomView lobbyRoomView = new LobbyRoomView(this, lobbyId, lobbyName, players);
        Main.getMain().setScreen(lobbyRoomView);
    }

}
