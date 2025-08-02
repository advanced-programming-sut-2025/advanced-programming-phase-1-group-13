package com.ap_project.controllers.pregame;

import com.ap_project.Main;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.client.views.pregame.LobbyMenuView;
import com.ap_project.network.GameClient;
import com.ap_project.client.views.pregame.LobbyRoomView;

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
        System.out.println("[Client] Creating lobby: " + name + ", private=" + isPrivate);

        if (name == null || name.trim().isEmpty()) {
            view.showError("Lobby name cannot be empty.");
            return;
        }

        client.sendMessage("CREATE_LOBBY", new String[]{name, password != null ? password : "", Boolean.toString(isPrivate)});
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

    public void enterLobbyRoom(String lobbyId) {
        Main.getMain().setScreen(new LobbyRoomView(Main.getMain(), this, lobbyId));
    }

    public void leaveLobby(String lobbyId) {
        client.sendMessage("LEAVE_LOBBY", new String[]{lobbyId});
    }

    public void startGame(String lobbyId) {
        client.sendMessage("START_GAME", new String[]{lobbyId});
    }

    public void backToLobbyMenu() {
        Main.getMain().setScreen(new LobbyMenuView(this, GameAssetManager.getGameAssetManager().getSkin())); // Return to lobby list view
    }

    public void showError(String message) {
        if (view != null) view.showError(message);
    }
}
