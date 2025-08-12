package com.ap_project.client.controllers.pregame;

import com.ap_project.Main;
import com.ap_project.client.controllers.game.GameController;
import com.ap_project.client.controllers.login.LoginController;
import com.ap_project.client.views.game.FarmView;
import com.ap_project.client.views.login.LoginMenuView;
import com.ap_project.common.models.App;
import com.ap_project.common.models.Game;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.types.Gender;
import com.ap_project.common.models.network.Message;
import com.ap_project.common.models.network.MessageType;
import com.ap_project.common.utils.JSONUtils;
import com.badlogic.gdx.Gdx;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ap_project.Main.*;

public class GameClient {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private LobbyMenuController lobbyMenuController;

    public GameClient(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        listenToServer();
    }

    public void setLobbyMenuController(LobbyMenuController lobbyMenuController) {
        this.lobbyMenuController = lobbyMenuController;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("[Client] Error closing socket: " + e.getMessage());
        }
    }

    public void sendMessage(MessageType type, Object body) {
        try {
            Message message = new Message();
            message.setType(type);
            if (body instanceof java.util.Map) {
                message.setBody((HashMap<String, Object>) body);
            } else {
                java.util.HashMap<String, Object> map = new java.util.HashMap<>();
                map.put("data", body);
                message.setBody(map);
            }

            String jsonString = JSONUtils.toJson(message);
            sendMessage(jsonString);
        } catch (Exception e) {
            System.out.println("[Client] Failed to send message: " + e.getMessage());
        }
    }

    public void sendMessage(String jsonMessage) {
        try {
            out.write(jsonMessage);
            out.newLine();
            out.flush();
            System.out.println("[Client] Sent message: " + jsonMessage);
        } catch (IOException e) {
            System.out.println("[Client] Failed to send message: " + e.getMessage());
        }
    }

    private void listenToServer() {
        new Thread(() -> {
            try {
                StringBuilder jsonBuffer = new StringBuilder();
                int braceCount = 0;
                boolean inQuotes = false;

                int c;
                while ((c = in.read()) != -1) {
                    char character = (char) c;
                    jsonBuffer.append(character);

                    if (character == '"' && (jsonBuffer.length() == 1 ||
                        jsonBuffer.charAt(jsonBuffer.length()-2) != '\\')) {
                        inQuotes = !inQuotes;
                    }

                    if (!inQuotes) {
                        if (character == '{') {
                            braceCount++;
                        } else if (character == '}') {
                            braceCount--;

                            if (braceCount == 0) {
                                String completeJson = jsonBuffer.toString();
                                System.out.println("[Client] Received: " + completeJson);

                                try {
                                    Message message = JSONUtils.fromJson(completeJson);
                                    handleServerMessage(message);
                                } catch (Exception e) {
                                    System.out.println("[Client] Error parsing JSON: " + e.getMessage());
                                }

                                jsonBuffer.setLength(0);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("[Client] Disconnected from server: " + e.getMessage());
                if (lobbyMenuController != null) {
                    lobbyMenuController.showError("Disconnected from server.");
                }
            }
        }).start();
    }

    private void handleServerMessage(Message message) {
        System.out.println("[Client] Handling message: " + message);

        switch (message.getType()) {
            case LOGIN_SUCCESS:
                System.out.println(message.getBody());
                String username = message.getFromBody("username");
                String password = message.getFromBody("password");
                String nickname = message.getFromBody("nickname");
                String email = message.getFromBody("email");
                Gender gender = Gender.valueOf(message.getFromBody("gender"));
                Gdx.app.postRunnable(() -> {
                    if (Main.getMain().getScreen() instanceof LoginMenuView) {
                        LoginMenuView loginMenuView = (LoginMenuView) Main.getMain().getScreen();
                        loginMenuView.getController().handleLoginSuccess(new User(username, password, nickname, email, gender));
                    }
                });
                break;

            case LOGIN_ERROR:
                String errorMessage = message.getFromBody("data");
                Gdx.app.postRunnable(() -> {
                    if (Main.getMain().getScreen() instanceof LoginMenuView) {
                        LoginMenuView loginMenuView = (LoginMenuView) Main.getMain().getScreen();
                        loginMenuView.getController().handleLoginError(errorMessage);
                    }
                });
                break;

            case LOBBY_LIST:
                List<String> lobbies = message.<List<String>>getFromBody("data");
                if (lobbyMenuController != null) {
                    lobbyMenuController.updateLobbyListFromServer(lobbies);
                }
                break;

            case ERROR:
                String errorMsg = message.<String>getFromBody("data");
                if (lobbyMenuController != null) {
                    System.out.println("[Client] Error: " + errorMsg);
                    lobbyMenuController.showError(errorMsg);
                }
                break;

            case LOBBY_CREATED:
                String createdLobbyId = message.<String>getFromBody("data");
                System.out.println("[Client] Lobby created successfully. ID: " + createdLobbyId);
                break;

            case JOIN_SUCCESS:
                String joinedLobbyId = message.<String>getFromBody("data");
                System.out.println("[Client] Joined lobby successfully. ID: " + joinedLobbyId);
                break;

            case LOBBY_INFO:
                String lobbyInfoString = message.<String>getFromBody("data");
                System.out.println("[Client] Received lobby info: " + lobbyInfoString);
                if (lobbyMenuController != null) {
                    Gdx.app.postRunnable(() -> lobbyMenuController.openLobbyRoomView(lobbyInfoString));
                }
                break;

            case USERS_INFO:
                ArrayList<String> usersInfo = message.getFromBody("data");
                System.out.println("[Client] Received users info: " + usersInfo);
                Gdx.app.postRunnable(() -> goToUsersMenu(usersInfo));
                break;

            case START_CHOOSE_MAP:
                Map<String, Object> bodyMap = message.getBody();
                ArrayList<User> players = new ArrayList<>();

                Gdx.app.postRunnable(() -> {
                try {
                    for (int i = 1; i <= 4; i++) {
                        String playerKey = "player" + i;
                        if (bodyMap.containsKey(playerKey)) {
                            Map<String, Object> playerData = (Map<String, Object>) bodyMap.get(playerKey);

                            String playerUsername = (String) playerData.get("username");
                            String playerPassword = (String) playerData.get("password");
                            String playerNickname = (String) playerData.get("nickname");
                            String playerEmail = (String) playerData.get("email");
                            Gender playerGender = Gender.valueOf((String) playerData.get("gender"));

                            User player = App.getUserByUsername(playerUsername);
                            if (player == null) {
                                player = new User(playerUsername, playerPassword, playerNickname, playerEmail, playerGender);
                                App.addUser(player);
                            }

                            players.add(player);
                        }
                    }

                    Game game = new Game(players, (String) bodyMap.get("id"), false);
                    for (User user : players) {
                        user.setActiveGame(game);
                    }
                    App.addGame(game);
                    App.getLoggedIn().setActiveGame(game);
                    Main.goToMultiplayerChooseMapMenuView();
                } catch (Exception e) {
                    System.out.println("[Client] Error: " + e.getMessage());
                    e.printStackTrace();
                }
                });
                break;

            case GO_TO_GAME:
                App.getLoggedIn().resetPlayer();
                Gdx.app.postRunnable(() -> goToGame(new FarmView(new GameController(), GameAssetManager.getGameAssetManager().getSkin())));
                break;

            default:
                System.out.println("[Client] Unknown message type: " + message.getType());
        }
    }
}
