package com.ap_project.client.views.pregame;

import com.ap_project.Main;
import com.ap_project.client.controllers.pregame.ScoreboardController;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class ScoreboardView implements Screen {
    private Stage stage;
    private final Texture background;
    private final Label scoreboardTitle;
    private final Label sortByLabel;
    private final SelectBox<String> sortBySelectBox;
    private final TextButton backButton;
    private final Table table;
    private final ScoreboardController controller;

    public ScoreboardView(ScoreboardController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getMenuBackground();

        this.scoreboardTitle = new Label("Scoreboard", skin);
        this.scoreboardTitle.setFontScale(1.5f);

        this.sortByLabel = new Label("Sort by", skin);
        this.sortByLabel.setFontScale(1.5f);

        this.sortBySelectBox = new SelectBox<>(skin);
        String[] options = {"Money", "Number of Quests", "Total Skills"};
        sortBySelectBox.setItems(options);

        this.backButton = new TextButton("Back", skin);

        this.table = new Table();

        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Image backgroundImage = new Image(background);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        scoreboardTitle.setPosition(
            (Gdx.graphics.getWidth() - scoreboardTitle.getWidth()) / 2,
            Gdx.graphics.getHeight() - 300
        );
        stage.addActor(scoreboardTitle);

        table.setFillParent(true);
        table.center();

        table.row();

        sortBySelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updateScoreboard();
            }
        });

        Table bottomTable = new Table();
        bottomTable.setFillParent(true);
        bottomTable.bottom().padBottom(20);

        bottomTable.add(backButton).padRight(200);
        bottomTable.add(sortByLabel).padRight(10);
        bottomTable.add(sortBySelectBox);

        stage.addActor(table);
        stage.addActor(bottomTable);

        updateScoreboard();
    }

    private void updateScoreboard() {
        table.clearChildren();

        table.row();

        ArrayList<User> users = controller.getSortedUsers(sortBySelectBox.getSelected());
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        addHeaderLabel(table, "Rank", skin);
        addHeaderLabel(table, "Username", skin);
        addHeaderLabel(table, "Money", skin);
        addHeaderLabel(table, "Number of Quests", skin);
        addHeaderLabel(table, "Total Skills", skin);
        table.row();

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);

            addDataLabel(table, String.valueOf(i + 1), skin);
            addDataLabel(table, user.getUsername(), skin);
            addDataLabel(table, String.valueOf(user.getBalance()), skin);
            addDataLabel(table, String.valueOf(user.getNumberOfQuests()), skin);
            addDataLabel(table, String.valueOf(user.getTotalSkills()), skin);
            table.row();
        }

        Table bottomTable = new Table();
        bottomTable.add(backButton).padRight(200);
        bottomTable.add(sortByLabel).padRight(10);
        bottomTable.add(sortBySelectBox);

        table.add(bottomTable).colspan(5).padTop(50);
    }

    private void addHeaderLabel(Table table, String text, Skin skin) {
        Label label = new Label(text, skin);
        label.setFontScale(1f);
        table.add(label).pad(5).expandX().align(Align.center);
    }

    private void addDataLabel(Table table, String text, Skin skin) {
        Label label = new Label(text, skin);
        label.setFontScale(1f);
        table.add(label).pad(5).expandX().align(Align.center);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(39f / 255f, 33f / 255f, 48f / 255f, 1f);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleButtons();
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

    public TextButton getBackButton() {
        return backButton;
    }
}
