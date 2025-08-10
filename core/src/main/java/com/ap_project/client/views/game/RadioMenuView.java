package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class RadioMenuView implements Screen {
    private Stage stage;
    private final Image window;
    private final Label radioTitle;
    private final ArrayList<Music> musicList;
    private final SelectBox<String> musicSelectBox;
    private final TextButton uploadButton;
    private final TextButton playButton;
    private final GameView gameView;
    private Music currentlyPlaying = null;
    private boolean isPlaying = false;

    public RadioMenuView(GameView gameView) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getRadioMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);

        this.radioTitle = new Label("Radio", GameAssetManager.getGameAssetManager().getSkin());

        this.musicList = new ArrayList<>();
        for (String path : App.getLoggedIn().getMusic()) {
            musicList.add(Gdx.audio.newMusic(Gdx.files.internal(path)));
        }

        this.musicSelectBox = new SelectBox<>(GameAssetManager.getGameAssetManager().getSkin());
        this.uploadButton = new TextButton("Upload", GameAssetManager.getGameAssetManager().getSkin()); // TODO
        this.playButton = new TextButton("Play", GameAssetManager.getGameAssetManager().getSkin());
        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(window);

        radioTitle.setFontScale(1.5f);
        radioTitle.setColor(Color.BLACK);
        radioTitle.setPosition(
            window.getX() + (window.getWidth() - radioTitle.getWidth()) / 2,
            window.getY() + window.getHeight() - 100
        );
        stage.addActor(radioTitle);

        uploadButton.setPosition(
            window.getX() + (window.getWidth() - uploadButton.getWidth()) / 2,
            window.getY() + 20
        );
        stage.addActor(uploadButton);

        if (musicList.isEmpty()) {
            Label noMusic = new Label("You don't have any music yet.", GameAssetManager.getGameAssetManager().getSkin());
            noMusic.setColor(Color.BLACK);
            noMusic.setPosition(
                window.getX() + (window.getWidth() - noMusic.getWidth()) / 2,
                window.getY() + window.getHeight() / 2
            );
            stage.addActor(noMusic);
        } else {
            String[] options = new String[musicList.size() + 1];
            options[0] = "None";

            for (int i = 0; i < App.getLoggedIn().getMusic().size(); i++) {
                String path = App.getLoggedIn().getMusic().get(i);
                String name = path.replace("Music/", "")
                    .replace("_", " ").replace(".mp3", "");
                options[i + 1] = name;
            }

            musicSelectBox.setItems(options);
            musicSelectBox.setSelected("None");

            musicSelectBox.setPosition(
                window.getX() + (window.getWidth() - musicSelectBox.getWidth()) / 2,
                window.getY() + window.getHeight() / 2 + 30
            );
            musicSelectBox.setWidth(500);

            // Add change listener for music selection
            musicSelectBox.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                    String selected = musicSelectBox.getSelected();
                    if (!selected.equals("None")) {
                        int index = musicSelectBox.getSelectedIndex() - 1;
                        currentlyPlaying = musicList.get(index);
                        currentlyPlaying.setVolume(0.5f); // Default volume
                        if (isPlaying) {
                            currentlyPlaying.play();
                        }
                    }
                }
            });

            stage.addActor(musicSelectBox);

            // Play button setup
            playButton.setPosition(
                window.getX() + (window.getWidth() / 2) - playButton.getWidth() - 10,
                window.getY() + window.getHeight() / 2 - 40
            );
            playButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                    if (currentlyPlaying != null && !isPlaying) {
                        currentlyPlaying.play();
                        isPlaying = true;
                    }
                }
            });
            stage.addActor(playButton);
        }
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Main.getMain().setScreen(gameView);
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
