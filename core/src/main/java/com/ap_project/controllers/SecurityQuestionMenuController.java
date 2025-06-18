package com.ap_project.controllers;

import com.ap_project.views.SecurityQuestionMenuView;

import static com.ap_project.Main.goToMainMenu;

public class SecurityQuestionMenuController {
    private SecurityQuestionMenuView view;

    public void setView(SecurityQuestionMenuView view) {
        this.view = view;
    }

    public void handleSecurityQuestionMenuButtons() {
        if (view != null) {
            if (view.getEnterButton().isChecked()) {
                goToMainMenu();
            } else if (view.getSkipButton().isChecked()) {
                goToMainMenu();
            }
            view.getSkipButton().setChecked(false);
            view.getEnterButton().setChecked(false);
        }
    }
}
