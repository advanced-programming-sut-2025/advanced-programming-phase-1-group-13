package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.Position;
import com.ap_project.common.models.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import static com.ap_project.client.views.game.GameMenuView.hoverOnImage;

public class MapView implements Screen, InputProcessor {
    private Stage stage;
    private final Image map;
    private final ArrayList<Image> players;
    private final ArrayList<Vector2> farmPositions;
    private final Image closeButton;
    private final GameView gameView;

    public MapView(GameView gameView) {
        this.map = new Image(GameAssetManager.getGameAssetManager().getMap(App.getCurrentGame()));
        map.setPosition(
            (Gdx.graphics.getWidth() - map.getWidth()) / 2,
            (Gdx.graphics.getHeight() - map.getHeight()) / 2
        );

        players = new ArrayList<>();
        for (User player : App.getCurrentGame().getPlayers()) {
            players.add(new Image(GameAssetManager.getGameAssetManager().getPlayerIcon(player.getGender())));
        }

        this.farmPositions = new ArrayList<>();
        farmPositions.add(new Vector2(830, 730));
        farmPositions.add(new Vector2(580, 540));
        farmPositions.add(new Vector2(830, 350));
        farmPositions.add(new Vector2(1080, 540));

        this.closeButton = new Image(GameAssetManager.getGameAssetManager().getCloseButton());
        closeButton.setPosition(
            Gdx.graphics.getWidth() / 2f + 400f,
            Gdx.graphics.getHeight() / 2f + 300f
        );

        this.gameView = gameView;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        stage.addActor(map);

        for (int i = 0; i < players.size(); i++) {
            Image player = players.get(i);
            Position playerPosition = App.getCurrentGame().getPlayers().get(i).getPosition();
            player.setPosition(
                farmPositions.get(i).x + 160 * playerPosition.getX()/ 74f,
                farmPositions.get(i).y - 160 * playerPosition.getY()/ 74f
            );
            stage.addActor(player);
        }

        stage.addActor(closeButton);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
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
        if (hoverOnImage(closeButton, screenX, Gdx.graphics.getHeight() - screenY)) {
            Main.getMain().setScreen(gameView);
            return true;
        }

        System.out.println(screenX + " " + screenY);
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
}
