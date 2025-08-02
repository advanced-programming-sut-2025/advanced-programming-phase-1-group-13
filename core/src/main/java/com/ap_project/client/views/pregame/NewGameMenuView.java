package com.ap_project.client.views.pregame;

import com.ap_project.Main;
import com.ap_project.client.controllers.pregame.NewGameMenuController;
import com.ap_project.common.models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class NewGameMenuView implements Screen {
    private Stage stage;
    private final Texture background;
    private final Label menuTitle;
    private final Label description;
    private final Label player2;
    private final TextField player2Field;
    private final Label player3;
    private final TextField player3Field;
    private final Label player4;
    private final TextField player4Field;
    private final TextButton startButton;
    private final TextButton backButton;
    private final Label errorMessageLabel;
    private final Table table;
    private final NewGameMenuController controller;

    public NewGameMenuView(NewGameMenuController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getMenuBackground();

        this.menuTitle = new Label("New Game Menu", skin);
        menuTitle.setFontScale(1.5f);

        this.description = new Label("Enter 1 to 3 usernames to add to the game:", skin);
        description.setFontScale(1.5f);

        this.player2 = new Label("Player 2", skin);
        this.player2.setFontScale(1.5f);
        this.player2Field = new TextField("", skin);

        this.player3 = new Label("Player 3", skin);
        this.player3.setFontScale(1.5f);
        this.player3Field = new TextField("", skin);

        this.player4 = new Label("Player 4", skin);
        this.player4.setFontScale(1.5f);
        this.player4Field = new TextField("", skin);

        this.startButton = new TextButton("Start", skin);
        this.backButton = new TextButton("Back", skin);

        this.errorMessageLabel = new Label("", skin);
        errorMessageLabel.setColor(Color.RED);
        errorMessageLabel.setPosition(10, Gdx.graphics.getHeight() - 20);

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

        table.clear();
        table.setFillParent(true);
        table.center();

        table.add(menuTitle).align(Align.center).colspan(3).padBottom(20).center().row();

        table.add(description).align(Align.center).colspan(3).padBottom(20).center().row();

        table.add(player2).align(Align.right).padBottom(20).padRight(20);
        table.add(player2Field).width(750).padBottom(20).row();

        table.add(player3).align(Align.right).padBottom(20).padRight(20);
        table.add(player3Field).width(750).padBottom(20).row();

        table.add(player4).align(Align.right).padBottom(20).padRight(20);
        table.add(player4Field).width(750).padBottom(20).row();

        table.setPosition(table.getX(), table.getY() + 50);
        stage.addActor(table);

        backButton.setPosition(700, 150);
        backButton.setWidth(200);
        stage.addActor(backButton);

        startButton.setPosition(1000, 150);
        startButton.setWidth(200);
        stage.addActor(startButton);

        stage.addActor(errorMessageLabel);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1f);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleNewGameMenuButtons();
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

    public TextField getPlayer2Field() {
        return player2Field;
    }

    public TextField getPlayer3Field() {
        return player3Field;
    }

    public TextField getPlayer4Field() {
        return player4Field;
    }

    public TextButton getStartButton() {
        return startButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public void setErrorMessage(String message) {
        errorMessageLabel.setText(message);
    }
}
