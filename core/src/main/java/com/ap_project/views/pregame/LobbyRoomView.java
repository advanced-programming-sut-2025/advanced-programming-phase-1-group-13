package com.ap_project.views.pregame;

import com.ap_project.Main;
import com.ap_project.controllers.pregame.LobbyMenuController;
import com.ap_project.models.GameAssetManager;
import com.ap_project.network.GameClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LobbyRoomView extends ScreenAdapter {

    private final Main main;
    private final LobbyMenuController controller;
    private final String lobbyId;
    private final Stage stage;
    private final Table table;
    private final Label titleLabel;
    private final List<String> playersList;
    private final ScrollPane scrollPane;
    private final TextButton leaveButton;
    private final TextButton startButton;

    public LobbyRoomView(Main main, LobbyMenuController controller, String lobbyId) {
        this.main = main;
        this.controller = controller;
        this.lobbyId = lobbyId;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        titleLabel = new Label("Lobby ID: " + lobbyId, GameAssetManager.getGameAssetManager().getSkin());

        playersList = new List<>(GameAssetManager.getGameAssetManager().getSkin());
        scrollPane = new ScrollPane(playersList, GameAssetManager.getGameAssetManager().getSkin());

        leaveButton = new TextButton("Leave Lobby", GameAssetManager.getGameAssetManager().getSkin());
        startButton = new TextButton("Start Game", GameAssetManager.getGameAssetManager().getSkin());

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
                controller.backToLobbyMenu();  // Return to lobby list after leaving
            }
        });

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.startGame(lobbyId);  // Only admin should see this as active
            }
        });
    }

    public void updatePlayers(String[] playerNames) {
        playersList.setItems(playerNames);
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
