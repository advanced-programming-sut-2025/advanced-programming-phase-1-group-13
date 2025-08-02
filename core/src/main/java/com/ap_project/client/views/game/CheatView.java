package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllerss.GameController;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.Result;
import com.ap_project.common.models.enums.commands.GameCommands;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.regex.Matcher;

public class CheatView implements Screen {
    private Stage stage;
    private final TextArea window;
    private final GameView gameView;
    private final GameController controller;

    public CheatView(GameView gameView, GameController controller) {
        this.window = new TextArea("", GameAssetManager.getGameAssetManager().getSkin());
        this.gameView = gameView;
        this.controller = controller;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        window.setSize(
            Gdx.graphics.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f
        );
        window.setPosition(
            (Gdx.graphics.getWidth() - window.getWidth()) / 2,
            (Gdx.graphics.getHeight() - window.getHeight()) / 2
        );

        window.setPrefRows(10);
        window.setAlignment(Align.topLeft);
        window.getStyle().font.getData().setScale(1f);
        ScrollPane scrollPane = new ScrollPane(window, GameAssetManager.getGameAssetManager().getSkin());
        scrollPane.setSize(window.getWidth(), window.getHeight());
        scrollPane.setPosition(window.getX(), window.getY());
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollbarsOnTop(true);

        stage.addActor(scrollPane);

        window.appendText("> ");
    }

    @Override
    public void render(float delta) {
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if ((Gdx.input.isKeyJustPressed(Input.Keys.ENTER) &&
            (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) ||
                Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)))) {
            processCurrentCommand();
        }
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

    private void processCurrentCommand() {
        String fullText = window.getText();
        String[] lines = fullText.split("\n");

        String command = "";
        for (int i = lines.length - 1; i >= 0; i--) {
            if (!lines[i].trim().isEmpty()) {
                command = lines[i].trim();
                break;
            }
        }

        if (!command.isEmpty()) {
            handleCommand(command.replaceAll("^> ", ""));
        }

        window.appendText("> ");
        window.setCursorPosition(window.getText().length());
    }

    private void handleCommand(String inputLine) {
        Result result = new Result(false, "");
        Matcher matcher;

        if ((matcher = GameCommands.CHEAT_ADV_TIME.getMatcher(inputLine)) != null) {
            result = controller.cheatAdvanceTime(matcher.group("hourIncrease"));
        }else if ((matcher = GameCommands.CHEAT_ADV_DATE.getMatcher(inputLine)) != null) {
            result = controller.cheatAdvanceDate(matcher.group("dayIncrease"));
        } else if ((GameCommands.SEASON.getMatcher(inputLine)) != null) {
            result = controller.showSeason();
        } else if ((matcher = GameCommands.CHEAT_THOR.getMatcher(inputLine)) != null) {
            result = controller.cheatThor(
                matcher.group("x"),
                matcher.group("y"));
        } else if ((matcher = GameCommands.CHEAT_WEATHER_SET.getMatcher(inputLine)) != null) {
            result = controller.cheatWeatherSet(matcher.group("type"));
        } else if ((GameCommands.GREENHOUSE_BUILD.getMatcher(inputLine)) != null) {
            result = controller.buildGreenhouse();
        } else if ((GameCommands.CHEAT_BUILD_GREENHOUSE.getMatcher(inputLine)) != null) {
            result = controller.cheatBuildNewGreenhouse();
        } else if ((matcher = GameCommands.CHEAT_ENERGY_SET.getMatcher(inputLine)) != null) {
            result = controller.setPlayerEnergy(matcher.group("value"));
        } else if ((GameCommands.CHEAT_ENERGY_UNLIMITED.getMatcher(inputLine)) != null) {
            result = controller.setUnlimitedEnergy();
        } else if ((GameCommands.CHEAT_ENERGY_LIMITED.getMatcher(inputLine)) != null) {
            result = controller.deactivateUnlimitedEnergy();
        } else if ((matcher = GameCommands.CHEAT_ADD_ITEM.getMatcher(inputLine)) != null) {
            result = controller.cheatAddItem(
                matcher.group("itemName"),
                matcher.group("count")
            );
        } else if ((matcher = GameCommands.CHEAT_SET_FRIENDSHIP.getMatcher(inputLine)) != null) {
            result = controller.cheatSetFriendship(
                matcher.group("name"),
                matcher.group("amount")
            );
        } else if ((matcher = GameCommands.CHEAT_ADD_DOLLARS.getMatcher(inputLine)) != null) {
            result = controller.cheatAddDollars(matcher.group("count"));
        } else if ((GameCommands.CHEAT_FAINT.getMatcher(inputLine)) != null) {
            App.getLoggedIn().faint();
        } else if ((GameCommands.EXIT.getMatcher(inputLine)) != null) {
            Main.getMain().setScreen(gameView);
        } else {
            result = new Result(false, "Invalid Command. Please try again!");
        }
        addText(result.message);
    }

    public void addText(String text) {
        window.appendText(text + "\n");
        window.setCursorPosition(window.getText().length());
    }
}
