package com.ap_project.controllers;

import com.ap_project.models.App;
import com.ap_project.models.User;
import com.ap_project.models.enums.types.Gender;
import com.ap_project.views.TitleMenuView;
import com.badlogic.gdx.Gdx;

import static com.ap_project.Main.goToLoginMenu;
import static com.ap_project.Main.goToSignUpMenu;
import static com.ap_project.controllers.login.LoginController.hashSha256;

public class TitleMenuController {
    private TitleMenuView view;

    public void handleButtons() {
        if (view != null) {
            if (view.getSignUpButton().isChecked()) {
                goToSignUpMenu();
            } else if (view.getLoginButton().isChecked()) {
                goToLoginMenu();
            } else if (view.getExitButton().isChecked()) {
                Gdx.app.exit();
            }
            view.getSignUpButton().setChecked(false);
            view.getLoginButton().setChecked(false);
            view.getExitButton().setChecked(false);
        }
    }

    public void setView(TitleMenuView view) {
        // TODO: remove later
        App.addUser(new User("arvin", hashSha256("1"), hashSha256("1"), "arv", "arvin@gmail.com", Gender.MAN));
        App.addUser(new User("dorsa", hashSha256("2"), hashSha256("2"), "dor", "dorsa@gmail.com", Gender.WOMAN));
        App.addUser(new User("farrokhi", hashSha256("2"), hashSha256("2"), "farrokh", "farrokhi@gmail.com", Gender.MAN));
        App.addUser(new User("selma", hashSha256("3"), hashSha256("3"), "sel", "selma@gmail.com", Gender.WOMAN));
        this.view = view;
    }
}
