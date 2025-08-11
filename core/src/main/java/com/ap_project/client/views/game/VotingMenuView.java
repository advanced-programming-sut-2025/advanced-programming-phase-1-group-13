package com.ap_project.client.views.game;


import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.ap_project.Main.getBatch;

public class VotingMenuView implements Screen, InputProcessor {
    private Stage stage;
    private final Image window;
    private final Image closeButton;
    private final GameView gameView;


    private Skin skin;
    private SelectBox<String> playerSelectBox;
    private TextButton kickButton;
    private TextButton forceTerminateButton;

    public VotingMenuView(GameView gameView) {
        this.gameView = gameView;
        this.window = new Image(GameAssetManager.getGameAssetManager().getToolMenu());
        float windowX = (Gdx.graphics.getWidth() - window.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - window.getHeight()) / 2;
        window.setPosition(windowX, windowY);
        this.skin = GameAssetManager.getGameAssetManager().getSkin();
        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(window);

        layoutUI();
        addCloseButton();
    }

    private void layoutUI() {
        playerSelectBox = new SelectBox<>(skin);

        Array<String> options = new Array<>();
        for (User player : App.getCurrentGame().getPlayers()) {
            if (player.equals(App.getLoggedIn())) continue;
            options.add(player.getUsername());
        }
        playerSelectBox.setItems(options);

        kickButton = new TextButton("Kick", skin);

        kickButton.addListener(e -> {
            if (kickButton.isPressed()) {
                String selected = playerSelectBox.getSelected();
                System.out.println("Kicking: " + selected);
                return true;
            }
            return false;
        });

        forceTerminateButton = new TextButton("Force Terminate", skin);
        forceTerminateButton.addListener(e -> {
            if (forceTerminateButton.isPressed()) {
                String selected = playerSelectBox.getSelected();
                System.out.println("Force Terminating: " + selected);
                return true;
            }
            return false;
        });

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.padTop(50);

        table.add(forceTerminateButton).width(440).padBottom(20).row();
        table.add(playerSelectBox).width(440).padBottom(10).row();
        table.add(kickButton).width(440);

        stage.addActor(table);
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
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void addCloseButton() {
        float buttonX = Gdx.graphics.getWidth() / 2f + 400f;
        float buttonY = window.getY() + window.getHeight() + 20f;

        closeButton.setPosition(buttonX, buttonY);
        closeButton.setSize(closeButton.getWidth(), closeButton.getHeight());

        stage.addActor(closeButton);
    }
}
