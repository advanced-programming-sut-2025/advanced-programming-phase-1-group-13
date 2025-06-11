package com.ap_project;

import com.ap_project.controllers.LoginController;
import com.ap_project.controllers.SignUpMenuController;
import com.ap_project.controllers.TitleMenuController;
import com.ap_project.models.GameAssetManager;
import com.ap_project.views.LoginMenuView;
import com.ap_project.views.SignUpMenuView;
import com.ap_project.views.TitleMenuView;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        main.setScreen(new TitleMenuView(new TitleMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static void goToTitleMenu() {
        Main.getMain().setScreen(new TitleMenuView(new TitleMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToSignUpMenu() {
        Main.getMain().setScreen(new SignUpMenuView(new SignUpMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToLoginMenu() {
        Main.getMain().setScreen(new LoginMenuView(new LoginController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }
}
