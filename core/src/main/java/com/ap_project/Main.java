package com.ap_project;

import com.ap_project.controllers.*;
import com.ap_project.controllers.login.ForgetPasswordMenuController;
import com.ap_project.controllers.login.LoginController;
import com.ap_project.controllers.signup.SecurityQuestionMenuController;
import com.ap_project.controllers.signup.SignUpMenuController;
import com.ap_project.models.GameAssetManager;
import com.ap_project.views.*;
import com.ap_project.views.login.ForgetPasswordMenuView;
import com.ap_project.views.login.LoginMenuView;
import com.ap_project.views.signup.SecurityQuestionMenuView;
import com.ap_project.views.signup.SignUpMenuView;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;
    private static final Skin skin = GameAssetManager.getGameAssetManager().getSkin();

    @Override
    public void create() {
        System.out.println("DEBUG: Main create");
        main = this;
        batch = new SpriteBatch();
        main.setScreen(new TitleMenuView(new TitleMenuController(), skin));
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
        Main.getMain().setScreen(new TitleMenuView(new TitleMenuController(), skin));
    }

    public static void goToSignUpMenu() {
        Main.getMain().setScreen(new SignUpMenuView(new SignUpMenuController(), skin));
    }

    public static void goToSecurityQuestionMenu(String username) {
        Main.getMain().setScreen(new SecurityQuestionMenuView(new SecurityQuestionMenuController(username), skin));
    }

    public static void goToLoginMenu() {
        Main.getMain().setScreen(new LoginMenuView(new LoginController(), skin));
    }

    public static void goToForgetPasswordMenu() {
        Main.getMain().setScreen(new ForgetPasswordMenuView(new ForgetPasswordMenuController(), skin));
    }

    public static void goToMainMenu() {
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
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
