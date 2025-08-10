package com.ap_project.client.views.pregame;

import com.ap_project.Main;
import com.ap_project.client.controllers.pregame.PreGameMenuController;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.enums.environment.Time;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.ap_project.client.views.game.GameMenuView.getFundString;

public class PreGameMenuView implements Screen {
    private Stage stage;
    private final Texture background;
    private final Label menuTitle;
    private final TextButton newGameButton;
    private final TextButton loadGameButton;
    private final TextButton scoreboardButton;
    private final TextButton lobbyButton;
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
        this.scoreboardButton = new TextButton("Scoreboard", skin);
        this.lobbyButton = new TextButton("Lobby", skin);
        this.backButton = new TextButton("Back", skin);

        this.savedGame = new Image(GameAssetManager.getGameAssetManager().getSavedGame());
        savedGame.setPosition(
            Gdx.graphics.getWidth() / 2f + 100,
            Gdx.graphics.getHeight() / 2f - 130
        );

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
        table.add(scoreboardButton).width(buttonWidth).padBottom(30).row();
        table.add(lobbyButton).width(buttonWidth).padBottom(30).row();
        table.add(backButton).width(buttonWidth).padBottom(30).row();

        table.padRight(500f);
        stage.addActor(table);

        stage.addActor(savedGame);
        Label savedGameLabel = new Label("Saved Game:", GameAssetManager.getGameAssetManager().getSkin());
        savedGameLabel.setFontScale(1.5f);
        savedGameLabel.setPosition(
            savedGame.getX() + 20,
            savedGame.getY() + 250
        );
        stage.addActor(savedGameLabel);

        if (App.getLoggedIn().getActiveGame() == null) {
            Label noSavedGames = new Label("No Saved Games", GameAssetManager.getGameAssetManager().getSkin());
            noSavedGames.setFontScale(1.5f);
            noSavedGames.setColor(34f / 255, 17f / 255, 34f / 255, 1);
            noSavedGames.setPosition(
                savedGame.getX() + savedGame.getWidth() / 2f - 1.5f * noSavedGames.getWidth() / 2f,
                savedGame.getY() + savedGame.getHeight() / 2f - noSavedGames.getHeight() / 2f
            );
            stage.addActor(noSavedGames);
        } else {
            Time time = App.getLoggedIn().getActiveGame().getGameState().getTime();
            Label duration = new Label("Day " + time.getDayInSeason() + " of " + time.getSeason().getName() + ", Year " + time.getYear(), GameAssetManager.getGameAssetManager().getSkin());
            duration.setFontScale(1.25f);
            duration.setColor(34f / 255, 17f / 255, 34f / 255, 1);
            duration.setPosition(
                savedGame.getX() + 50,
                savedGame.getY() + 135
            );
            stage.addActor(duration);

            Image coin = new Image(GameAssetManager.getGameAssetManager().getCoin());
            coin.setPosition(
                savedGame.getX() + 50,
                savedGame.getY() + 55
            );
            stage.addActor(coin);

            Label balance = new Label(getFundString((int) App.getLoggedIn().getBalance()), GameAssetManager.getGameAssetManager().getSkin());
            balance.setFontScale(1.25f);
            balance.setColor(34f / 255, 17f / 255, 34f / 255, 1);
            balance.setPosition(
                coin.getX() + coin.getWidth() + 10,
                coin.getY()
            );
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

    public TextButton getNewGameButton() {
        return newGameButton;
    }

    public TextButton getLoadGameButton() {
        return loadGameButton;
    }

    public TextButton getScoreboardButton() {
        return scoreboardButton;
    }

    public TextButton getLobbyButton() {
        return lobbyButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public Label getErrorMessageLabel() {
        return errorMessageLabel;
    }
}
