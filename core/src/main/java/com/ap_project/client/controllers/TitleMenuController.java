package com.ap_project.client.controllers;

import com.ap_project.common.models.App;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.types.Gender;
import com.ap_project.client.views.TitleMenuView;
import com.badlogic.gdx.Gdx;

import static com.ap_project.Main.goToLoginMenu;
import static com.ap_project.Main.goToSignUpMenu;

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
        this.view = view;
    }
}
