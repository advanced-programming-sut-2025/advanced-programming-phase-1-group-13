package com.ap_project.views.pregame;

import com.ap_project.Main;
import com.ap_project.controllers.pregame.PreGameMenuController;
import com.ap_project.models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PreGameMenuView implements Screen {
    private Stage stage;
    private final Texture background;
    private final Label menuTitle;
    private final TextButton newGameButton;
    private final TextButton loadGameButton;
    private final TextButton backButton;
    private final Table table;
    private final PreGameMenuController controller;

    public PreGameMenuView(PreGameMenuController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getMenuBackground();

        this.menuTitle = new Label("PreGame Menu", skin);
        menuTitle.setFontScale(2.0f);

        this.newGameButton = new TextButton("New Game", skin);
        this.loadGameButton = new TextButton("Load Game", skin);
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

        table.clear();
        table.setFillParent(true);
        table.center();

        table.add(menuTitle).align(Align.center).colspan(3).padBottom(20).center().row();

        float buttonWidth = 425f;
        table.add(newGameButton).width(buttonWidth).padBottom(30).row();
        table.add(loadGameButton).width(buttonWidth).padBottom(30).row();
        table.add(backButton).width(buttonWidth).padBottom(30).row();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1f);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handlePreGameMenuButtons();
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

    public TextButton getNewGameButton() {
        return newGameButton;
    }

    public TextButton getLoadGameButton() {
        return loadGameButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }
}
