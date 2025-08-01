package com.ap_project.views.pregame;

import com.ap_project.controllers.pregame.LobbyMenuController;
import com.ap_project.models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.List;

public class LobbyMenuView implements Screen {
    private final Stage stage;
    private final LobbyMenuController controller;
    private final Skin skin;

    private final Table table;
    private final TextField lobbyNameField;
    private final CheckBox privateCheckBox;
    private final TextField passwordField;
    private final TextButton createLobbyButton;
    private final TextButton refreshButton;
    private final ScrollPane lobbyListScroll;
    private final VerticalGroup lobbyList;
    private final Label errorLabel;

    public LobbyMenuView(LobbyMenuController controller, Skin skin) {
        this.controller = controller;
        this.controller.setView(this);
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Image backgroundImage = new Image(GameAssetManager.getGameAssetManager().getMenuBackground());
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        table = new Table();
        table.setFillParent(true);
        table.top().padTop(30);

        lobbyNameField = new TextField("", skin);
        privateCheckBox = new CheckBox(" Private Lobby", skin);
        passwordField = new TextField("", skin);
        passwordField.setMessageText("Enter password");
        passwordField.setDisabled(true);

        createLobbyButton = new TextButton("Create Lobby", skin);
        refreshButton = new TextButton("Refresh", skin);

        errorLabel = new Label("", skin);
        errorLabel.setColor(1, 0, 0, 1);

        lobbyList = new VerticalGroup();
        lobbyList.top().left().columnLeft();
        lobbyListScroll = new ScrollPane(lobbyList, skin);
        lobbyListScroll.setFadeScrollBars(false);

        layoutUI();
        addListeners();
    }

    private void layoutUI() {
        table.add(new Label("Create New Lobby", skin)).colspan(2).padBottom(10).row();
        table.add(new Label("Lobby Name:", skin)).left();
        table.add(lobbyNameField).width(300).row();
        table.add(privateCheckBox).colspan(2).left().row();
        table.add(new Label("Password:", skin)).left();
        table.add(passwordField).width(300).row();
        table.add(createLobbyButton).colspan(2).padTop(10).row();

        table.add(new Label("Available Lobbies:", skin)).colspan(2).padTop(40).row();
        table.add(lobbyListScroll).colspan(2).width(500).height(300).row();
        table.add(refreshButton).colspan(2).padTop(10).row();
        table.add(errorLabel).colspan(2).padTop(10);

        stage.addActor(table);
    }

    private void addListeners() {
        privateCheckBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                passwordField.setDisabled(!privateCheckBox.isChecked());
            }
        });

        createLobbyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String name = lobbyNameField.getText();
                String password = privateCheckBox.isChecked() ? passwordField.getText() : null;
                boolean isPrivate = privateCheckBox.isChecked();
                controller.createLobby(name, password, isPrivate);
            }
        });

        refreshButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.refreshLobbyList();
            }
        });
    }

    public void updateLobbyList(List<String> lobbies) {
        lobbyList.clear();

        for (String rawLobbyInfo : lobbies) {
            String[] parts = rawLobbyInfo.split(",");
            if (parts.length < 4) continue; // skip invalid entries

            String lobbyId = parts[0];
            String name = parts[1];
            String players = parts[2];
            boolean isPrivate = Boolean.parseBoolean(parts[3]);

            String display = String.format("Lobby: %s (%s) | Players: %s | %s",
                name, lobbyId, players, isPrivate ? "Private 🔒" : "Public");

            Label infoLabel = new Label(display, skin);
            TextButton joinBtn = new TextButton("Join", skin);
            HorizontalGroup row = new HorizontalGroup();
            row.space(20);
            row.left();
            row.addActor(infoLabel);
            row.addActor(joinBtn);

            joinBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.joinLobbyFromList(rawLobbyInfo);
                }
            });

            lobbyList.addActor(row);
        }
    }

    public void showError(String message) {
        errorLabel.setText(message);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
}
