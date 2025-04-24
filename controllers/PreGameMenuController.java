package controllers;

import models.App;
import models.Result;
import models.enums.Menu;


public class PreGameMenuController {
    public Result gameNew(String player1, String player2, String player3) {
        // TODO
        return new Result(true, "");
    }

    public Result chooseGameMap(String mapNumberString) {
        int mapNumber = Integer.parseInt(mapNumberString); // TODO : ERRORS
        // TODO
        return new Result(true, "");
    }

    public Result loadGame() {
        // TODO
        return new Result(true, "");
    }

    public Result exitGame() {
        App.setCurrentMenu(Menu.PRE_GAME_MENU);
        return new Result(true, "Exiting Game ... Heading to Pre-Game Menu");
    }
}
