package com.ap_project.client.views.game;

import com.ap_project.common.models.GameAssetManager;
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

import static com.ap_project.Main.getBatch;
import static com.ap_project.Main.goToKickMenu;

public class KickMenuView implements Screen {
    private Stage stage;
    private final Image window;
    private final Image closeButton;
    private final TextButton yesButton;
    private final TextButton noButton;
    private final Label titleLabel;

    public KickMenuView(Skin skin) {
        this.window = new Image(GameAssetManager.getGameAssetManager().getToolMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2f;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2f;
        window.setPosition(windowX, windowY);

        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());

        this.yesButton = new TextButton("Yes", skin);
        this.noButton = new TextButton("No", skin);

        this.titleLabel = new Label("Kick this player?", skin);
        titleLabel.setColor(Color.BLACK);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(window);
        addCloseButton();
        addTitleLabel();
        addOptionButtons();
    }

    private void addCloseButton() {
        float buttonX = window.getX() + window.getWidth() - closeButton.getWidth() - 10;
        float buttonY = window.getY() + window.getHeight() - closeButton.getHeight() - 10;

        closeButton.setPosition(buttonX, buttonY);

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Close button clicked");
            }
        });

        stage.addActor(closeButton);
    }

    private void addTitleLabel() {
        float titleX = window.getX() + (window.getWidth() - titleLabel.getWidth()) / 2f-50;
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
                System.out.println("YES clicked");
                goToKickMenu();
            }
        });

        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("NO clicked");
                goToKickMenu();
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

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        stage.dispose();
    }
}
