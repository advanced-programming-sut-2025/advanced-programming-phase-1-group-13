package com.ap_project.views.phase_one;

import com.ap_project.models.App;
import com.ap_project.models.Game;
import com.ap_project.models.User;
import com.ap_project.models.enums.Menu;
import com.ap_project.models.enums.commands.PreGameMenuCommands;
import com.ap_project.models.enums.commands.ProfileCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class PreGameMenu implements AppMenu {
    private final PhaseOnePreGameMenuController controller = new PhaseOnePreGameMenuController();
    Matcher matcher;
    private List<String> pendingUsernames = new ArrayList<>();
    private ArrayList<User> usersInGame = new ArrayList<>();

    @Override
    public void check(Scanner scanner) {
        String inputLine = scanner.nextLine();

        if ((matcher = PreGameMenuCommands.GAME_NEW.getMatcher(inputLine)) != null) {
            String usernamesInput = matcher.group("usernames");
            if (usernamesInput == null || usernamesInput.trim().isEmpty()) {
                System.out.println("Please provide at least one username.");
            } else {
                pendingUsernames = new ArrayList<>(Arrays.asList(usernamesInput.trim().split("\\s+")));
                pendingUsernames.add(App.getLoggedIn().getUsername());

                // Convert pendingUsernames to usersInGame
                usersInGame.clear();
                for (String username : pendingUsernames) {
                    User user = App.getUserByUsername(username);
                    if (user != null) {
                        usersInGame.add(user);
                    } else {
                        System.out.println("Warning: User '" + username + "' not found and will be ignored.");
                    }
                }

                System.out.println("Game setup started. Users: " + String.join(", ", pendingUsernames));
                System.out.println("Each user must select a map using: game map <mapNumber> <username>");

            }
        }

        else if ((matcher = PreGameMenuCommands.GAME_MAP.getMatcher(inputLine)) != null) {
            if (pendingUsernames.isEmpty()) {
                System.out.println("No game setup in progress. Start with 'game new <usernames>'.");
                return;
            }

            String mapNumber = matcher.group("mapNumber");
            String username = matcher.group("username");

            if (!pendingUsernames.contains(username)) {
                System.out.println("Invalid username or already assigned a map.");
                return;
            }

            User user = App.getUserByUsername(username);
            if (user == null) {
                System.out.println("User not found.");
                return;
            }

            System.out.println(controller.chooseGameMap(mapNumber, user));
            pendingUsernames.remove(username);

            if (pendingUsernames.isEmpty()) {
                System.out.println("All players have selected their maps. Switching to GAME MENU...");
                App.setCurrentGame(new Game(usersInGame));
                App.setCurrentMenu(Menu.GAME_MENU);
            } else {
                System.out.println("Remaining users: " + String.join(", ", pendingUsernames));
            }
        }

        else if ((matcher = PreGameMenuCommands.LOAD_GAME.getMatcher(inputLine)) != null) {
            System.out.println(controller.loadGame());
        } else if ((matcher = ProfileCommands.SHOW_CURRENT_MENU.getMatcher(inputLine)) != null) {
            System.out.println(controller.showCurrentMenu());
        } else if ((matcher = PreGameMenuCommands.EXIT.getMatcher(inputLine)) != null) {
            App.setCurrentMenu(Menu.MAIN_MENU);
            System.out.println("Menu switched to Main Menu.");
        } else {
            System.out.println("Invalid command. Please try again.");
        }
    }
}
