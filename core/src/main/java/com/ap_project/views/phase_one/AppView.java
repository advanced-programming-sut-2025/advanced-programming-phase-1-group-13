package com.ap_project.views.phase_one;

import com.ap_project.models.App;
import com.ap_project.models.enums.Menu;

import java.util.Scanner;

public class AppView {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            App.getCurrentMenu().checkCommand(scanner);
        } while ( App.getCurrentMenu() != Menu.EXIT);
    }
}
