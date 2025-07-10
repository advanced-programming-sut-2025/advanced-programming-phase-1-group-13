package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.controllers.GameController;
import com.ap_project.models.App;
import com.ap_project.models.GameAssetManager;
import com.ap_project.models.enums.environment.Season;
import com.ap_project.models.enums.environment.Time;
import com.ap_project.models.enums.environment.Weather;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private Image inventoryHotbarImage;
    private Image selectedSlotImage;
    private int selectedSlotIndex;
    Image previousClockImage = null;
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

        this.inventoryHotbarImage = new Image(GameAssetManager.getGameAssetManager().getInventoryHotbar());
        this.selectedSlotImage = new Image(GameAssetManager.getGameAssetManager().getHotbarSelectedSlot());
        this.selectedSlotIndex = 0;

        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        addClock();
        updateClockInfo();

        addInventoryHotbar();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1f);
        Main.getBatch().begin();
        Main.getBatch().end();

        if (inventoryHotbarImage != null && selectedSlotImage != null) {
            selectedSlotImage.setPosition(
                ((stage.getWidth() - inventoryHotbarImage.getWidth()) / 2 + 16.0f)
                    + selectedSlotIndex * (selectedSlotImage.getWidth() + 1.0f),
                25.0f
            );
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            Time time = App.getCurrentGame().getGameState().getTime();
            time.setHour(time.getHour() + 1);
            if (time.getHour() == 22) {
                time.setHour(9);
            }
            System.out.println("Time set to " + time.getHour());
            updateClockInfo();
        }
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
        if (keycode >= Input.Keys.NUM_1 && keycode <= Input.Keys.NUM_9) {
            selectedSlotIndex = keycode - Input.Keys.NUM_1;
        }
        if (keycode >= Input.Keys.NUMPAD_1 && keycode <= Input.Keys.NUMPAD_9) {
            selectedSlotIndex = keycode - Input.Keys.NUMPAD_1;
        }

        if (keycode == Input.Keys.NUM_0 || keycode == Input.Keys.NUMPAD_0) {
            selectedSlotIndex = 9;
        }

        if (keycode == Input.Keys.MINUS) {
            selectedSlotIndex = 10;
        }

        if (keycode == Input.Keys.EQUALS) {
            selectedSlotIndex = 11;
        }

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

    private void addClock() {
        Image clockImage = new Image(clock);
        float scale = 0.90f;
        clockImage.setScale(scale);
        float xPosition = Gdx.graphics.getWidth() - clockImage.getWidth() * scale - 10;
        float yPosition = Gdx.graphics.getHeight() - clockImage.getHeight() * scale - 10;
        clockImage.setPosition(xPosition, yPosition);
        stage.addActor(clockImage);
    }

    private void updateClockInfo() {
        Image clockImage = new Image(clock);
        float scale = 0.90f;
        clockImage.setScale(scale);
        float xPosition = Gdx.graphics.getWidth() - clockImage.getWidth() * scale - 10;
        float yPosition = Gdx.graphics.getHeight() - clockImage.getHeight() * scale - 10;
        clockImage.setPosition(xPosition, yPosition);

        if (previousClockImage != null) {
            previousClockImage.remove();
        }
        Image clockArrowImage = new Image(clockArrow);
        clockArrowImage.setScale(scale);
        clockArrowImage.setOrigin(
            clockArrowImage.getWidth() / 2,
            clockArrowImage.getHeight()
        );
        clockArrowImage.setPosition(
            xPosition + (0.23f * clockImage.getWidth()),
            yPosition + (0.31f * clockImage.getHeight())
        );
        clockArrowImage.setRotation(getClockArrowDegree());
        stage.addActor(clockArrowImage);
        previousClockImage = clockArrowImage;

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

        updateTimeLabel();
        updateDateLabel();
        updateBalanceLabel(clockImage, xPosition, yPosition, scale);
    }

    private void updateDateLabel() {
        Time currentTime = App.getCurrentGame().getGameState().getTime();
        String weekday = currentTime.getWeekday().getName().substring(0, 3);
        int dayInSeason = currentTime.getDayInSeason();
        date.setText(weekday + ". " + dayInSeason);
    }

    private void updateTimeLabel() {
        int hour = App.getCurrentGame().getGameState().getTime().getHour();
        String text = hour % 12 + ":00 " + (hour < 12 ? "am" : "pm");
        if (text.equals("0:00 pm")) {
            text = "12:00 pm";
        }
        time.setText(text);
    }

    private void updateBalanceLabel(Image clockImage, float xPosition, float yPosition, float scale) {
        int balance = (int) App.getLoggedIn().getBalance();
        boolean started = false;
        for (int i = 7; i >= 0; i--) {
            int digit = balance / ((int) Math.pow(10, i));
            balance %= ((int) Math.pow(10, i));
            if (digit != 0 || i == 0 || started) {
                started = true;
                String digitString = Integer.toString(digit);
                Label digitLabel = new Label(digitString, GameAssetManager.getGameAssetManager().getSkin());
                digitLabel.setPosition(
                    xPosition + (0.75f * clockImage.getWidth()) - 24 * scale * i,
                    yPosition + (0.05f * clockImage.getHeight())
                );
                stage.addActor(digitLabel);
            }
        }
    }

    private float getClockArrowDegree() {
        float hour = (float) App.getCurrentGame().getGameState().getTime().getHour();
        return - 180 * (hour - 9f) / (22f - 9f);
    }

    public void addInventoryHotbar() {
        inventoryHotbarImage.setPosition((stage.getWidth() - inventoryHotbarImage.getWidth()) / 2, 10);
        stage.addActor(inventoryHotbarImage);
        stage.addActor(selectedSlotImage);
    }

    private void changeSelectedInventorySlot(int amount) {
        selectedSlotIndex += amount;
        if (selectedSlotIndex >= 11) {
            selectedSlotIndex = 0;
        }
        if (selectedSlotIndex < 0) {
            selectedSlotIndex = 11;
        }
    }
}
