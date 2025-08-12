package com.ap_project.server;

import com.ap_project.common.models.User;
import com.ap_project.server.controller.ClientHandler;
import com.ap_project.server.models.Lobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class GameServer {
    public static final int PORT = 9999;

    public static final ArrayList<User> users = new ArrayList<>();
    public static final Map<String, Lobby> lobbies = Collections.synchronizedMap(new HashMap<>());
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

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
