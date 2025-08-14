package com.ap_project.client.controllers.pregame;

import com.ap_project.Main;
import com.ap_project.client.controllers.game.GameController;
import com.ap_project.client.views.game.*;
import com.ap_project.client.views.login.LoginMenuView;
import com.ap_project.common.models.*;
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
            if (body instanceof Map) {
                message.setBody((HashMap<String, Object>) body);
            } else {
                HashMap<String, Object> map = new HashMap<>();
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
                        jsonBuffer.charAt(jsonBuffer.length() - 2) != '\\')) {
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
                                    e.printStackTrace();
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
            case LOGIN_SUCCESS: {
                System.out.println(message.getBody());
                String username = message.getFromBody("username");
                Gdx.app.postRunnable(() -> {
                    if (getMain().getScreen() instanceof LoginMenuView) {
                        LoginMenuView loginMenuView = (LoginMenuView) getMain().getScreen();
                        loginMenuView.getController().handleLoginSuccess(App.getUserByUsername(username));
                    }
                });
                break;
            }

            case LOGIN_ERROR: {
                String errorMessage = message.getFromBody("data");
                Gdx.app.postRunnable(() -> {
                    if (getMain().getScreen() instanceof LoginMenuView) {
                        LoginMenuView loginMenuView = (LoginMenuView) getMain().getScreen();
                        loginMenuView.getController().handleLoginError(errorMessage);
                    }
                });
                break;
            }

            case LOBBY_LIST: {
                List<String> lobbies = message.<List<String>>getFromBody("data");
                if (lobbyMenuController != null) {
                    lobbyMenuController.updateLobbyListFromServer(lobbies);
                }
                break;
            }

            case LOBBY_CREATED: {
                String createdLobbyId = message.<String>getFromBody("data");
                System.out.println("[Client] Lobby created successfully. ID: " + createdLobbyId);
                break;
            }

            case JOIN_SUCCESS: {
                String joinedLobbyId = message.<String>getFromBody("data");
                System.out.println("[Client] Joined lobby successfully. ID: " + joinedLobbyId);
                break;
            }

            case LOBBY_INFO: {
                String lobbyInfoString = message.getFromBody("data");
                System.out.println("[Client] Received lobby info: " + lobbyInfoString);
                if (lobbyMenuController != null) {
                    Gdx.app.postRunnable(() -> lobbyMenuController.openLobbyRoomView(lobbyInfoString));
                }
                break;
            }

            case UPDATE_SCOREBOARD_INFO: {
                HashMap<String, Object> scoreboardInfo = new HashMap<>();
                scoreboardInfo.put("username", App.getLoggedIn().getUsername());
                scoreboardInfo.put("money", String.valueOf(App.getLoggedIn().getBalance()));
                scoreboardInfo.put("quests", String.valueOf(App.getLoggedIn().getNumberOfQuests()));
                scoreboardInfo.put("skill", String.valueOf(App.getLoggedIn().getTotalSkills()));

                sendMessage(JSONUtils.toJson(new Message(scoreboardInfo, MessageType.SCOREBOARD_INFO_FROM_CLIENT)));

                break;
            }

            case SCOREBOARD_INFO: {
                String username = message.getFromBody("username");
                double money = message.getFromBody("money");

                User user = App.getUserByUsername(username);
                if (user == null) user = new User(username, "1", username, "", Gender.RATHER_NOT_SAY);

                user.setBalance(money);
            }

            case USERS_INFO: {
                ArrayList<String> usersInfo = message.getFromBody("data");
                System.out.println("[Client] Received users info: " + usersInfo);
                if (usersInfo == null) return;
                Gdx.app.postRunnable(() -> goToUsersMenu(usersInfo));
                break;
            }

            case START_CHOOSE_MAP: {
                Map<String, Object> bodyMap = message.getBody();
                ArrayList<User> players = new ArrayList<>();

                Gdx.app.postRunnable(() -> {
                    try {
                        for (int i = 0; i <= 3; i++) {
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
                        goToMultiplayerChooseMapMenuView();
                    } catch (Exception e) {
                        System.out.println("[Client] Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
                break;
            }

            case GO_TO_GAME: {
                App.getLoggedIn().resetPlayer();
                Gdx.app.postRunnable(() -> goToGame(new FarmView(new GameController(), GameAssetManager.getGameAssetManager().getSkin())));
                break;
            }

            case ASK_TERMINATE_VOTE: {
                GameView gameView;
                if (getMain().getScreen() instanceof VotingMenuView) {
                    gameView = ((VotingMenuView) getMain().getScreen()).getGameView();
                } else {
                    gameView = null;
                }
                Gdx.app.postRunnable(() -> goToTerminateMenu(gameView));
                break;
            }

            case FINISH_TERMINATE_VOTE: {
                GameView gameView;
                if (getMain().getScreen() instanceof TerminateMenuView) {
                    gameView = ((TerminateMenuView) getMain().getScreen()).getGameView();
                } else {
                    gameView = null;
                }

                boolean conclusion = Boolean.parseBoolean(message.getFromBody("conclusion"));
                if (conclusion) {
                    for (User player : App.getCurrentGame().getPlayers()) {
                        player.setActiveGame(null);
                        if (player.getMostEarnedMoney() < player.getBalance()) {
                            player.setMostEarnedMoney(player.getBalance());
                        }
                        player.resetPlayer();
                    }
                    App.getLoggedIn().setActiveGame(null);
                    Gdx.app.postRunnable(Main::goToPreGameMenu);
                } else {
                    Gdx.app.postRunnable(() -> goToVotingMenu(gameView));
                }
                break;
            }

            case ASK_KICK_VOTE: {
                GameView gameView;
                if (getMain().getScreen() instanceof VotingMenuView) {
                    gameView = ((VotingMenuView) getMain().getScreen()).getGameView();
                } else {
                    gameView = null;
                }
                String username = message.getFromBody("username");
                Gdx.app.postRunnable(() -> goToKickMenu(gameView, username));
                break;
            }

            case FINISH_KICK_VOTE: {
                GameView gameView;
                if (getMain().getScreen() instanceof KickMenuView) {
                    gameView = ((KickMenuView) getMain().getScreen()).getGameView();
                } else {
                    gameView = null;
                }

                boolean conclusion = Boolean.parseBoolean(message.getFromBody("conclusion"));
                if (conclusion) {
                    String username = message.getFromBody("username");
                    App.getCurrentGame().getPlayers().removeIf(player -> player.getUsername().equals(username));
                    if (App.getLoggedIn().getUsername().equals(username)) {
                        App.getLoggedIn().setActiveGame(null);
                        Gdx.app.postRunnable(Main::goToPreGameMenu);
                    }
                } else {
                    Gdx.app.postRunnable(() -> goToVotingMenu(gameView));
                }
                break;
            }

            case UPDATE_PLAYERS_IN_VILLAGE: {
                String username = message.getFromBody("username");
                User targetUser = App.getUserByUsername(username);
                if (targetUser == null) {
                    System.out.println("[Client] Error: User not found: " + username);
                    return;
                }

                targetUser.setInVillage(true);
                targetUser.setPosition(new Position(37, 40));

                break;
            }

            case ASK_MARRIAGE: {
                GameView gameView;
                if (getMain().getScreen() instanceof VotingMenuView) {
                    gameView = ((VotingMenuView) getMain().getScreen()).getGameView();
                } else {
                    gameView = null;
                }

                String sender = message.getFromBody("sender");
                String receiver = message.getFromBody("receiver");

                if (App.getLoggedIn().getUsername().equals(sender) ||
                    App.getLoggedIn().getUsername().equals(receiver)) {
                    Gdx.app.postRunnable(() -> goToProposeMenu(gameView, sender, receiver));
                }

                break;
            }

            case FINISH_PROPOSAL: {
                String sender = message.getFromBody("sender");
                String receiver = message.getFromBody("receiver");
                boolean result = Boolean.parseBoolean(message.getFromBody("result"));

                if (App.getLoggedIn().getUsername().equals(receiver)) {
                    GameController controller = new GameController();
                    System.out.println(controller.respondToMarriageRequest(String.valueOf(result), sender));
                    Gdx.app.postRunnable(() -> {
                        GameView villageView = new VillageView(new GameController(), GameAssetManager.getGameAssetManager().getSkin());
                        villageView.errorMessageLabel.setText(result ?
                            "You accepted " + sender + "'s marriage proposal. Congratulations! :)" :
                            "You rejected " + sender + "'s marriage proposal."
                        );
                        goToGame(villageView);
                    });
                }

                if (App.getLoggedIn().getUsername().equals(sender)) {
                    Gdx.app.postRunnable(() -> {
                        GameView villageView = new VillageView(new GameController(), GameAssetManager.getGameAssetManager().getSkin());
                        villageView.errorMessageLabel.setText(result ?
                            receiver + " accepted your marriage proposal. Congratulations! :)" :
                            receiver + " rejected your marriage proposal."
                        );
                        goToGame(villageView);
                    });
                }

                break;
            }

            case GIVE_FLOWER: {
                VillageView villageView;
                if (Main.getMain().getScreen() instanceof VillageView) {
                    villageView = (VillageView) Main.getMain().getScreen();
                } else {
                    return;
                }

                String receiver = message.getFromBody("receiver");
                System.out.println("LOGGED IN: " + App.getLoggedIn().getUsername() + ", RECEIVER: " + receiver);
                if (App.getLoggedIn().getUsername().equals(receiver)) {
                    Gdx.app.postRunnable(() -> {
                        villageView.errorMessageLabel.setText("You received flowers.");
                        villageView.setPlayerWithFlower(App.getLoggedIn());
                        Main.getMain().setScreen(villageView);
                    });
                }

                break;
            }

            case RECEIVE_GIFT: {
                VillageView villageView;
                if (Main.getMain().getScreen() instanceof VillageView) {
                    villageView = (VillageView) Main.getMain().getScreen();
                } else {
                    return;
                }

                String receiver = message.getFromBody("receiver");
                String sender = message.getFromBody("sender");
                String item = message.getFromBody("item");

                System.out.println("LOGGED IN: " + App.getLoggedIn().getUsername() + ", RECEIVER: " + receiver + ", SENDER: " + sender + ", ITEM: " + item);
                if (App.getLoggedIn().getUsername().equals(receiver)) {
                    Gdx.app.postRunnable(() -> {
                        App.getLoggedIn().getBackpack().addToInventory(Item.getItemByItemName(item), 1);
                        villageView.errorMessageLabel.setText("You received " + item + " from " + sender + ".");
                        Main.getMain().setScreen(villageView);
                    });
                }

                if (App.getLoggedIn().getUsername().equals(sender)) {
                    Gdx.app.postRunnable(() -> {
                        villageView.errorMessageLabel.setText("You gave " + item + " to " + receiver + ".");
                        Main.getMain().setScreen(villageView);
                    });
                }

                break;
            }

            case REQUEST_RADIO: {
                HashMap<String, Object> body = new HashMap<>();
                body.put("username", App.getLoggedIn().getUsername());
                List<String> musicPaths = App.getLoggedIn().getMusic();
                for (int i = 0; i < musicPaths.size(); i++) {
                    body.put("path" + (i + 1), musicPaths.get(i));
                }
                sendMessage(JSONUtils.toJson(new Message(body, MessageType.RADIO_INFO)));
                break;
            }

            case RADIO_INFO: {
                ArrayList<String> music = new ArrayList<>();
                HashMap<String, Object> body = message.getBody();
                for (int i = 1; body.containsKey("path" + i); i++) {
                    music.add((String) body.get("path" + i));
                }

                User user = App.getUserByUsername(message.getFromBody("username"));
                if (user == null) return;
                user.setMusic(music);
                break;
            }

            case ERROR: {
                String errorMsg = message.<String>getFromBody("data");
                if (lobbyMenuController != null) {
                    System.out.println("[Client] Error: " + errorMsg);
                    lobbyMenuController.showError(errorMsg);
                }
                break;
            }

            default:
                System.out.println("[Client] Unknown message type: " + message.getType());
        }
    }
}
