package com.ap_project.controllers.login;

import com.ap_project.views.login.ChangePasswordMenuView;

public class ChangePasswordMenuController {
    private ChangePasswordMenuView view;

    public void handleChangePasswordMenuButtons() {
        if (view != null) {
            if (view.getEnterButton().isChecked()) {

            }
            view.getEnterButton().setChecked(false);
        }
    }

    public void setView(ChangePasswordMenuView view) {
        this.view = view;
    }
}
