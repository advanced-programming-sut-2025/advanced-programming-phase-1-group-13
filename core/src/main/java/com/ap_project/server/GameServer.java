package com.ap_project.server;

import com.ap_project.common.models.App;
import com.ap_project.common.models.Game;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.types.Gender;
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
    public static final ArrayList<ClientHandler> clients = new ArrayList<>();
    public static final ArrayList<Game> games = new ArrayList<>();

    public static void main(String[] args) {
        // TODO: remove later
        users.add(new User("arvin", "2", "arv", "arvin@gmail.com", Gender.MAN));
        users.add(new User("dorsa", "2", "dor", "dorsa@gmail.com", Gender.WOMAN));
        users.add(new User("farrokhi", "2", "farrokh", "farrokhi@gmail.com", Gender.MAN));
        users.add(new User("selma", "2", "sel", "selma@gmail.com", Gender.WOMAN));

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[Server] GameServer running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[Server] New client connected.");
                ClientHandler handler = new ClientHandler(clientSocket);
                clients.add(handler);
                lobbies.put("1234", new Lobby("1234", "dorfar", "", false, true, clients.get(0))); // TODO: remove later
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

    public static Game getGameById(String id) {
        for (Game game : games) {
            if (game.getId().equals(id)) {
                return game;
            }
        }
        return null;
    }
}
