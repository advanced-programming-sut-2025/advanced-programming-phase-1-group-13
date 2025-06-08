package com.ap_project.controllers;

import com.ap_project.views.TitleMenuView;
import com.badlogic.gdx.Gdx;

public class TitleMenuController {
    private TitleMenuView view;

    public void handleButtons() {
        if (view != null) {
            if (view.getSignUpButton().isChecked()) {

            } else if (view.getLoginButton().isChecked()) {

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
