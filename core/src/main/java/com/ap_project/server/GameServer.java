package com.ap_project.server;

import com.ap_project.server.controller.ClientHandler;
import com.ap_project.server.models.LobbyData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameServer {
    public static final int PORT = 9999;

    public static final Map<String, LobbyData> lobbies = Collections.synchronizedMap(new HashMap<>());
    public static final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[Server] GameServer running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[Server] New client connected.");
                ClientHandler handler = new ClientHandler(clientSocket);
                clients.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.err.println("[Server] Failed to start server: " + e.getMessage());
        }
    }

    public static LobbyData getLobby(String lobbyId) {
        return lobbies.get(lobbyId);
    }
}
