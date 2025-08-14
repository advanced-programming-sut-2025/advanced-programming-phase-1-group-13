package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.game.RadioController;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.User;
import com.ap_project.common.models.network.Message;
import com.ap_project.common.models.network.MessageType;
import com.ap_project.common.utils.JSONUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.HashMap;

public class RadioMenuView implements Screen {
    private Stage stage;
    private final Image window;
    private final Label radioTitle;
    private final ArrayList<Music> musicList;
    private final SelectBox<String> musicSelectBox;
    private final TextButton uploadButton;
    private final TextButton playButton;
    private final GameView gameView;
    private final RadioController controller;
    private final SelectBox<String> playerSelectBox;
    private final TextButton submitButton;
    private User user;
    private Music currentlyPlaying;

    public RadioMenuView(GameView gameView) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getRadioMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.radioTitle = new Label("Radio", GameAssetManager.getGameAssetManager().getSkin());
        this.musicSelectBox = new SelectBox<>(GameAssetManager.getGameAssetManager().getSkin());
        this.uploadButton = new TextButton("Upload", GameAssetManager.getGameAssetManager().getSkin());
        this.playButton = new TextButton("Play", GameAssetManager.getGameAssetManager().getSkin());
        this.submitButton = new TextButton("Submit", GameAssetManager.getGameAssetManager().getSkin());
        this.playerSelectBox = new SelectBox<>(GameAssetManager.getGameAssetManager().getSkin());

        Array<String> options = new Array<>();
        for (User player : App.getCurrentGame().getPlayers()) {
            options.add(player.getUsername());
        }
        playerSelectBox.setItems(options);

        this.user = App.getUserByUsername(playerSelectBox.getSelected());
        this.musicList = new ArrayList<>();
        refreshMusicList();

        this.gameView = gameView;
        this.controller = new RadioController();
    }

    private void refreshMusicList() {
        for (Music music : musicList) {
            music.dispose();
        }
        musicList.clear();

        for (String path : user.getMusic()) {
            try {
                Music music = Gdx.audio.newMusic(Gdx.files.local(path));
                musicList.add(music);
            } catch (Exception e) {
                System.out.println("Error loading music file: " + path);
            }
        }
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(window);

        radioTitle.setFontScale(1.5f);
        radioTitle.setColor(Color.BLACK);
        radioTitle.setPosition(
            window.getX() - 10 + (window.getWidth() - radioTitle.getWidth()) / 2,
            window.getY() + window.getHeight() - 100
        );
        radioTitle.setScale(1.3f);
        stage.addActor(radioTitle);

        uploadButton.setPosition(
            window.getX() + (window.getWidth() - uploadButton.getWidth()) / 2,
            window.getY() + 20
        );
        stage.addActor(uploadButton);

        playerSelectBox.setPosition(610, 550);
        playerSelectBox.setWidth(400f);
        submitButton.setPosition(1028, 530);

        stage.addActor(playerSelectBox);
        stage.addActor(submitButton);
        submitButton.setChecked(false);

        updateMusicList();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (currentlyPlaying != null) {
                currentlyPlaying.stop();
            }
            Main.getMain().setScreen(gameView);
        }

        if (submitButton.isChecked()) {
            String username = playerSelectBox.getSelected();
            user = App.getUserByUsername(username);
            refreshMusicList();
            updateMusicList();
            submitButton.setChecked(false);

            HashMap<String, Object> body = new HashMap<>();
            body.put("username", username);
            Message message = new Message(body, MessageType.REQUEST_RADIO);
            Main.getClient().sendMessage(JSONUtils.toJson(message));
        }

        if (uploadButton.isChecked()) {
            controller.handleFileSelection(() -> {
                user = App.getLoggedIn();
                playerSelectBox.setSelected(user.getUsername());
                refreshMusicList();
                updateMusicList();
            });
            uploadButton.setChecked(false);
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    public void updateMusicList() {
        Array<Actor> actors = stage.getActors();
        Array<Actor> toRemove = new Array<>();

        for (Actor actor : actors) {
            if (actor != window && actor != radioTitle && actor != uploadButton &&
                actor != submitButton && actor != playerSelectBox) {
                toRemove.add(actor);
            }
        }

        for (Actor actor : toRemove) {
            stage.getActors().removeValue(actor, true);
        }

        if (musicList.isEmpty()) {
            Label noMusic = new Label("No music available for " + user.getUsername(),
                GameAssetManager.getGameAssetManager().getSkin());
            noMusic.setColor(Color.BLACK);
            noMusic.setPosition(
                window.getX() + (window.getWidth() - noMusic.getWidth()) / 2,
                window.getY() + window.getHeight() / 2 - 60
            );
            stage.addActor(noMusic);
        } else {
            String[] options = new String[musicList.size() + 1];
            options[0] = "None";

            for (int i = 0; i < user.getMusic().size(); i++) {
                String path = user.getMusic().get(i);
                String name = path.substring(path.lastIndexOf('/') + 1)
                    .replace(".mp3", "")
                    .replace("_", " ");
                options[i + 1] = name;
            }

            musicSelectBox.setItems(options);
            musicSelectBox.setSelectedIndex(0);

            musicSelectBox.setPosition(
                window.getX() + ((window.getWidth() - musicSelectBox.getWidth()) / 2) - 150,
                window.getY() + window.getHeight() / 2 - 10
            );
            musicSelectBox.setWidth(500);
            stage.addActor(musicSelectBox);

            playButton.setPosition(
                window.getX() + (window.getWidth() / 2) - playButton.getWidth() - 180,
                window.getY() + window.getHeight() / 2 - 40
            );

            playButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    int selectedIndex = musicSelectBox.getSelectedIndex();
                    if (selectedIndex > 0) {
                        if (currentlyPlaying != null) {
                            currentlyPlaying.stop();
                        }
                        currentlyPlaying = musicList.get(selectedIndex - 1);
                        currentlyPlaying.play();
                        playButton.setText("Stop");
                    } else {
                        if (currentlyPlaying != null) {
                            currentlyPlaying.stop();
                            currentlyPlaying = null;
                            playButton.setText("Play");
                        }
                    }
                }
            });
            stage.addActor(playButton);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        for (Music music : musicList) {
            music.dispose();
        }
        if (currentlyPlaying != null) {
            currentlyPlaying.dispose();
        }
        stage.dispose();
    }

    public TextButton getSubmitButton() {
        return submitButton;
    }
}
