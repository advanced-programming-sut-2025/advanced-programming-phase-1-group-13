package com.ap_project.views.pregame;

import com.ap_project.Main;
import com.ap_project.controllers.pregame.PreGameMenuController;
import com.ap_project.models.App;
import com.ap_project.models.Game;
import com.ap_project.models.GameAssetManager;
import com.ap_project.models.enums.environment.Time;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.ap_project.views.game.GameMenuView.getFundString;

public class PreGameMenuView implements Screen {
    private Stage stage;
    private final Texture background;
    private final Label menuTitle;
    private final TextButton newGameButton;
    private final TextButton loadGameButton;
    private final TextButton backButton;
    private final Image savedGame;
    private final Label errorMessageLabel;
    private final Table table;
    private final PreGameMenuController controller;

    public PreGameMenuView(PreGameMenuController controller, Skin skin) {
        this.background = GameAssetManager.getGameAssetManager().getMenuBackground();

        this.menuTitle = new Label("PreGame Menu", skin);
        menuTitle.setFontScale(1.5f);

        this.newGameButton = new TextButton("New Game", skin);
        this.loadGameButton = new TextButton("Load Game", skin);
        this.backButton = new TextButton("Back", skin);

        this.savedGame = new Image(GameAssetManager.getGameAssetManager().getSavedGame());

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

        stage.addActor(errorMessageLabel);

        table.clear();
        table.setFillParent(true);
        table.center();

        table.add(menuTitle).align(Align.center).colspan(3).padBottom(20).center().row();

        float buttonWidth = 425f;
        table.add(newGameButton).width(buttonWidth).padBottom(30).row();
        table.add(loadGameButton).width(buttonWidth).padBottom(30).row();
        table.add(backButton).width(buttonWidth).padBottom(30).row();

        table.setPosition(table.getX() - 300, table.getY());
        stage.addActor(table);

        stage.addActor(savedGame);
        if (App.getLoggedIn().getActiveGame() == null) {
            Label noSavedGames = new Label("No Saved Games", GameAssetManager.getGameAssetManager().getSkin());
            noSavedGames.setFontScale(1.5f);
            stage.addActor(noSavedGames);
        } else {
            Time time = App.getLoggedIn().getActiveGame().getGameState().getTime();
            Label duration = new Label("Day " + time.getDayInSeason() + " of " + time.getSeason().getName() + ", Year " + time.getYear(), GameAssetManager.getGameAssetManager().getSkin());
            duration.setFontScale(1.5f);
            stage.addActor(duration);

            Image coin = new Image(GameAssetManager.getGameAssetManager().getCoin());
            stage.addActor(coin);
            Label balance = new Label(getFundString((int) App.getLoggedIn().getBalance()), GameAssetManager.getGameAssetManager().getSkin());
            balance.setFontScale(1.5f);
            stage.addActor(balance);
        }
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

    public Label getErrorMessageLabel() {
        return errorMessageLabel;
    }
}
