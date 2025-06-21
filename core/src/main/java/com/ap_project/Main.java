package com.ap_project;

import com.ap_project.controllers.*;
import com.ap_project.controllers.login.*;
import com.ap_project.controllers.signup.*;
import com.ap_project.models.GameAssetManager;
import com.ap_project.views.*;
import com.ap_project.views.login.*;
import com.ap_project.views.signup.*;
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

    public static void goToSecurityQuestionMenu(String username) {
        Main.getMain().setScreen(new SecurityQuestionMenuView(new SecurityQuestionMenuController(username), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToLoginMenu() {
        Main.getMain().setScreen(new LoginMenuView(new LoginController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToForgetPasswordMenu() {
        Main.getMain().setScreen(new ForgetPasswordMenuView(new ForgetPasswordMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToChangePasswordMenu(String username) {
        Main.getMain().setScreen(new ChangePasswordMenuView(new ChangePasswordMenuController(username), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToMainMenu() {
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToProfileMenu() {
        Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
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
