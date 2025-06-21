package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.controllers.GameController;
import com.ap_project.models.App;
import com.ap_project.models.GameAssetManager;
import com.ap_project.models.enums.environment.Season;
import com.ap_project.models.enums.environment.Time;
import com.ap_project.models.enums.environment.Weather;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private final Label date;
    private final Label time;
    private final Texture clock;
    private final Texture clockArrow;
    private final GameController controller;

    public GameView(GameController controller, Skin skin) {
        this.date = new Label("", skin);
        // date.setColor(Color.BLACK);
        updateDateLabel();

        this.time = new Label("", skin);
        // time.setColor(Color.BLACK);
        updateTimeLabel();

        Weather weather = App.getCurrentGame().getGameState().getCurrentWeather();
        Season season = App.getCurrentGame().getGameState().getTime().getSeason();
        this.clock = GameAssetManager.getGameAssetManager().getClock(weather, season);
        this.clockArrow = GameAssetManager.getGameAssetManager().getClockArrow();

        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Image clockImage = new Image(clock);
        float scale = 0.90f;
        clockImage.setScale(scale);
        float xPosition = Gdx.graphics.getWidth() - clockImage.getWidth() * scale - 10;
        float yPosition = Gdx.graphics.getHeight() - clockImage.getHeight() * scale - 10;
        clockImage.setPosition(xPosition, yPosition);
        stage.addActor(clockImage);

        Image clockArrowImage = new Image(clockArrow);
        clockArrowImage.setScale(scale);
        clockArrowImage.setPosition(
            xPosition + (0.23f * clockImage.getWidth()),
            yPosition + (0.33f * clockImage.getHeight())
        );
        stage.addActor(clockArrowImage);

        date.setPosition(
            xPosition + (0.5f * clockImage.getWidth()) - 10,
            yPosition + (0.77f * clockImage.getHeight())
        );
        stage.addActor(date);

        time.setPosition(
            xPosition + (0.5f * clockImage.getWidth()) - 20,
            yPosition + (0.41f * clockImage.getHeight())
        );
        stage.addActor(time);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1f);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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

    private void updateDateLabel() {
        Time currentTime = App.getCurrentGame().getGameState().getTime();
        String weekday = currentTime.getWeekday().getName().substring(0, 3);
        int dayInSeason = currentTime.getDayInSeason();
        date.setText(weekday + ". " + dayInSeason);
    }

    private void updateTimeLabel() {
        int hour = App.getCurrentGame().getGameState().getTime().getHour();
        time.setText(hour % 12 + ":00 " + (hour < 12 ? "am" : "pm"));
    }

    private float getClockArrowDegree() {
        int hour = App.getCurrentGame().getGameState().getTime().getHour();
        return 3.141592f * (hour - 9) / (22 - 9);
    }
}
