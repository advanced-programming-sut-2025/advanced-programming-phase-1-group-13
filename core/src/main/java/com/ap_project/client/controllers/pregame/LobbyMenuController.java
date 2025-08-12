package com.ap_project.client.controllers.pregame;

import com.ap_project.Main;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.client.views.pregame.LobbyMenuView;
import com.ap_project.client.views.pregame.LobbyRoomView;
import com.ap_project.common.models.network.Message;
import com.ap_project.common.models.network.MessageType;
import com.ap_project.common.utils.JSONUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    public void createLobby(String name, String password, boolean isPrivate, boolean isVisible) {
        System.out.println("[Client] Creating lobby: " + name + ", private=" + isPrivate + ", visible=" + isVisible);

        if (name == null || name.trim().isEmpty()) {
            view.showError("Lobby name cannot be empty.");
            return;
        }

        HashMap<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("password", password != null ? password : "");
        body.put("isPrivate", isPrivate);
        body.put("isVisible", isVisible);

        Message message = new Message(body, MessageType.CREATE_LOBBY);
        client.sendMessage(JSONUtils.toJson(message));
    }

    public void refreshLobbyList() {
        Message message = new Message(new HashMap<>(), MessageType.REFRESH_LOBBIES);
        client.sendMessage(JSONUtils.toJson(message));
    }

    public void joinLobbyFromList(String lobbyInfoString) {
        String[] parts = lobbyInfoString.split(",");
        if (parts.length >= 4) {
            String lobbyId = parts[0];
            boolean isPrivate = Boolean.parseBoolean(parts[3]);

            if (isPrivate) {
                view.promptPasswordAndJoin(lobbyId);
            } else {
                joinLobbyWithPassword(lobbyId, "");
            }
        } else {
            view.showError("Invalid lobby data.");
        }
    }

    public void joinLobbyWithPassword(String lobbyId, String password) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("lobbyId", lobbyId);
        body.put("password", password);

        Message message = new Message(body, MessageType.JOIN_LOBBY);
        client.sendMessage(JSONUtils.toJson(message));
    }

    public void updateLobbyListFromServer(List<String> lobbyInfoList) {
        if (view != null) {
            view.updateLobbyList(lobbyInfoList);
        }
    }

    public void leaveLobby(String lobbyId) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("lobbyId", lobbyId);

        Message message = new Message(body, MessageType.LEAVE_LOBBY);
        client.sendMessage(JSONUtils.toJson(message));
    }

    public void startGame(String lobbyId) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("lobbyId", lobbyId);

        Message message = new Message(body, MessageType.START_GAME);
        client.sendMessage(JSONUtils.toJson(message));
    }

    public void backToLobbyMenu() {
        Main.getMain().setScreen(new LobbyMenuView(this, GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void showError(String message) {
        if (view != null) view.showError(message);
    }

    public void requestLobbyInfo(String lobbyId) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("lobbyId", lobbyId);

        Message message = new Message(body, MessageType.REQUEST_LOBBY_INFO);
        client.sendMessage(JSONUtils.toJson(message));
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

    public GameClient getClient() {
        return client;
    }
}
