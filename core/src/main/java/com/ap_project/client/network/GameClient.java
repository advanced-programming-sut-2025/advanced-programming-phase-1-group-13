package com.ap_project.network;

import com.ap_project.controllers.pregame.LobbyMenuController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameClient {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private LobbyMenuController lobbyMenuController;

    public GameClient(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        listenToServer();
    }

    public void setLobbyMenuController(LobbyMenuController lobbyMenuController) {
        this.lobbyMenuController = lobbyMenuController;
    }

    public Object[] readResponse() throws IOException, ClassNotFoundException {
        String cmd = (String) in.readObject();
        Object data = in.readObject();
        return new Object[]{cmd, data};
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("[Client] Error closing socket: " + e.getMessage());
        }
    }

    public void sendMessage(String command, String[] args) {
        try {
            out.writeObject(command);
            out.writeObject(args);
            out.flush();
            System.out.println("[Client] Sent command: " + command + " with data: " + Arrays.toString(args));
        } catch (IOException e) {
            System.out.println("[Client] Failed to send command: " + e.getMessage());
        }
    }


    private void listenToServer() {
        new Thread(() -> {
            try {
                while (true) {
                    String command = (String) in.readObject();
                    Object data = in.readObject();

                    System.out.println("[Client] Received command: " + command);

                    switch (command) {
                        case "LOBBY_LIST":
                            if (data instanceof List) {
                                List<String> lobbies = (List<String>) data;
                                if (lobbyMenuController != null) {
                                    lobbyMenuController.updateLobbyListFromServer(lobbies);
                                }
                            }
                            break;
                        case "ERROR":
                            if (data instanceof String) {
                                if (lobbyMenuController != null) {
                                    lobbyMenuController.showError((String) data);
                                }
                            }
                            break;
                        case "LOBBY_CREATED":
                            System.out.println("[Client] Lobby created successfully. ID: " + data);
                            break;
                        case "JOIN_SUCCESS":
                            System.out.println("[Client] Joined lobby successfully. ID: " + data);
                            break;
                        default:
                            System.out.println("[Client] Unknown server command: " + command);
                    }
                }
            } catch (Exception e) {
                System.out.println("[Client] Disconnected from server: " + e.getMessage());
                if (lobbyMenuController != null) {
                    lobbyMenuController.showError("Disconnected from server.");
                }
            }
        }).start();
    }

}
