package models.enums;

import views.*;

import java.util.Scanner;

public enum Menu {
    MAIN_MENU(new MainMenu());

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }

}
