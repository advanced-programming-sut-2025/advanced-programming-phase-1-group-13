package com.ap_project.client.views.pregame;

import com.ap_project.server.controller.LobbyMenuController;
import com.ap_project.common.models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class LobbyRoomView extends ScreenAdapter {
    private final LobbyMenuController controller;
    private final String lobbyId;
    private final Stage stage;
    private final TextButton leaveButton;
    private final TextButton startButton;
    private final TextButton backButton;

    public LobbyRoomView(LobbyMenuController controller, String lobbyId, String lobbyName, ArrayList<String> players) {
        this.controller = controller;

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Image backgroundImage = new Image(GameAssetManager.getGameAssetManager().getMenuBackground());
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        Label lobbyNameLabel = new Label(lobbyName, skin);
        lobbyNameLabel.setFontScale(1.5f);

        Label lobbyIdLabel = new Label("Lobby ID: " + lobbyId, skin);
        this.lobbyId = lobbyId;

        VerticalGroup playersList = new VerticalGroup();
        for (String client : players) {
            Label playerLabel = new Label(client + (players.indexOf(client) == 0 ? " (Admin)" : ""), skin);

            HorizontalGroup row = new HorizontalGroup();
            row.space(20);
            row.space(20);
            row.left();
            row.left();
            row.addActor(playerLabel);

            playersList.addActor(row);
        }

        leaveButton = new TextButton("Leave Lobby", skin);
        startButton = new TextButton("Start Game", skin);
        backButton = new TextButton("Back", skin);

        ScrollPane scrollPane = new ScrollPane(playersList, skin);
        table.add(lobbyNameLabel).padBottom(20).row();
        table.add(lobbyIdLabel).padBottom(20).row();
        table.add(scrollPane).height(150).width(300).padBottom(20).row();
        table.add(leaveButton).width(400).padBottom(10).row();
        table.add(startButton).width(400).row();
        table.add(backButton).width(400).row();

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

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.backToLobbyMenu();
            }
        });
    }

    @Override
    public void render(float delta) {
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
