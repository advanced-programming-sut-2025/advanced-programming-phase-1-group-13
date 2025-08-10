package com.ap_project.client.views.pregame;

import com.ap_project.client.models.network.ClientHandler;
import com.ap_project.client.models.network.GameClient;
import com.ap_project.server.models.LobbyData;
import com.ap_project.server.controller.LobbyMenuController;
import com.ap_project.common.models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;
import java.net.Socket;

import static com.ap_project.server.GameServer.getLobby;
import static com.ap_project.server.GameServer.lobbies;

public class LobbyRoomView extends ScreenAdapter {
    private final LobbyMenuController controller;
    private final String lobbyId;
    private LobbyData lobby;
    private final Stage stage;
    private final Table table;
    private final Label titleLabel;
    //    private final List<String> playersList;
    private final ScrollPane scrollPane;
    private final VerticalGroup playersList;
    private final TextButton leaveButton;
    private final TextButton startButton;

    public LobbyRoomView(LobbyMenuController controller, String lobbyId) {
        this.controller = controller;
        this.lobbyId = lobbyId;
        this.lobby = getLobby(lobbyId);
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        titleLabel = new Label("Lobby ID: " + lobbyId, skin);

        playersList = new VerticalGroup();

        System.out.println("number of lobbies: " + lobbies.size());
        if (lobby !=null) {
            for (ClientHandler client : lobby.getPlayers()) {
                Label playerInfo = new Label(client.getNickname(), skin);

                HorizontalGroup row = new HorizontalGroup();
                row.space(20);
                row.space(20);
                row.left();
                row.left();
                row.addActor(playerInfo);

                playersList.addActor(row);
            }
        }

        leaveButton = new TextButton("Leave Lobby", skin);
        startButton = new TextButton("Start Game", skin);


        scrollPane = new ScrollPane(playersList, skin);
        table.add(titleLabel).padBottom(20).row();
        table.add(scrollPane).height(150).width(300).padBottom(20).row();
        table.add(leaveButton).width(150).padBottom(10).row();
        table.add(startButton).width(150).row();

        setupListeners();
    }

    private void setupListeners() {
        leaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.leaveLobby(lobbyId);
                controller.backToLobbyMenu();
            }
        });

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.startGame(lobbyId);
            }
        });
    }

    public void setStartButtonEnabled(boolean enabled) {
        startButton.setDisabled(!enabled);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
