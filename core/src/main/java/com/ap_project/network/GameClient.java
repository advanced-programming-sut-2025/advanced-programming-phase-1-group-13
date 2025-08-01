package com.ap_project.network;

import com.ap_project.controllers.pregame.LobbyMenuController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private LobbyMenuController lobbyMenuController;

    public GameClient(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        listenToServer();
    }

    public void setLobbyMenuController(LobbyMenuController controller) {
        this.lobbyMenuController = controller;
    }

    public void sendMessage(String command, String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append(command);
        for (String arg : args) {
            sb.append("::").append(arg);
        }
        out.println(sb.toString());
    }

    private void listenToServer() {
        new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.startsWith("LOBBY_LIST::")) {
                        String[] items = line.substring("LOBBY_LIST::".length()).split(";;");
                        List<String> lobbies = new ArrayList<>();
                        for (String item : items) {
                            if (!item.isBlank()) lobbies.add(item);
                        }
                        if (lobbyMenuController != null)
                            lobbyMenuController.updateLobbyListFromServer(lobbies);
                    } else if (line.startsWith("ERROR::")) {
                        String msg = line.substring("ERROR::".length());
                        if (lobbyMenuController != null)
                            lobbyMenuController.showError(msg);
                    }
                }
            } catch (Exception e) {
                System.out.println("Disconnected from server: " + e.getMessage());
                if (lobbyMenuController != null) lobbyMenuController.showError("Disconnected from server.");
            }
        }).start();
    }
}
