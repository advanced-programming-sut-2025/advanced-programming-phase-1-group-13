package com.ap_project.client.views.phase_one;

import com.ap_project.common.models.App;
import com.ap_project.common.models.enums.Menu;

import java.util.Scanner;

public class AppView {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            App.getCurrentMenu().checkCommand(scanner);
        } while ( App.getCurrentMenu() != Menu.EXIT);
    }
}
