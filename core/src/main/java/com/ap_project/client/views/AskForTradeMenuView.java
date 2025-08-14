package com.ap_project.client.views;

import com.ap_project.Main;
import com.ap_project.client.views.game.GameView;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.network.Message;
import com.ap_project.common.models.network.MessageType;
import com.ap_project.common.utils.JSONUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.HashMap;

import static com.ap_project.Main.*;

public class AskForTradeMenuView implements Screen {
    private Stage stage;
    private final Image window;
    private final TextButton yesButton;
    private final TextButton noButton;
    private final Label titleLabel;
    private final GameView gameView;
    private final String sender;

    public AskForTradeMenuView(Skin skin, GameView gameView, String sender) {
        this.sender = sender;
        this.window = new Image(GameAssetManager.getGameAssetManager().getToolMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2f;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2f;
        window.setPosition(windowX, windowY);

        this.yesButton = new TextButton("Yes", skin);
        this.noButton = new TextButton("No", skin);
        this.gameView = gameView;

        this.titleLabel = new Label(sender + " is asking you to trade, do you accept?", skin);
        titleLabel.setColor(Color.BLACK);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(window);
        addTitleLabel();
        addOptionButtons();
    }

    private void addTitleLabel() {
        float titleX = window.getX() + (window.getWidth() - titleLabel.getWidth()) / 2f - 50;
        float titleY = window.getY() + window.getHeight() - 230;
        titleLabel.setPosition(titleX, titleY);
        titleLabel.setFontScale(1.5f);
        stage.addActor(titleLabel);
    }

    private void addOptionButtons() {
        float spacing = 40;
        float totalWidth = yesButton.getWidth() + spacing + noButton.getWidth();
        float startX = window.getX() + (window.getWidth() - totalWidth) / 2f;
        float buttonY = window.getY() + 200;

        yesButton.setPosition(startX, buttonY);
        noButton.setPosition(startX + yesButton.getWidth() + spacing, buttonY);

        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("sender", sender);
                body.put("receiver", App.getLoggedIn().getUsername());
                body.put("response", "true");
                Message message = new Message(body, MessageType.ANSWER_TRADE_REQUEST);
                getClient().sendMessage(JSONUtils.toJson(message));
                Main.getMain().setScreen(gameView);
            }
        });

        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("sender", sender);
                body.put("receiver", App.getLoggedIn().getUsername());
                body.put("response", "false");
                Message message = new Message(body, MessageType.ANSWER_TRADE_REQUEST);
                getClient().sendMessage(JSONUtils.toJson(message));
                Main.getMain().setScreen(gameView);
            }
        });

        stage.addActor(yesButton);
        stage.addActor(noButton);
    }

    @Override
    public void render(float delta) {
        getBatch().begin();
        getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }

    public GameView getGameView() {
        return gameView;
    }
}
