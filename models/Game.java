package models;

import models.enums.environment.Time;
import models.enums.environment.Weather;

import java.util.ArrayList;

public class Game {
    private ArrayList<User> players; // The 3 players
    private GameMap gameMap;
    private Time time;
    private Weather currentWeather;
    // TODO: complete the class!!


    public Game(ArrayList<User> players) {
        this.players = players;
    }

    public void newGame(String username1, String username2, String username3) {
        // TODO
    }

    public void setGameMap(int mapNumber) {
        // TODO
    }

    public void loadGame(String callerUsername) {
        // TODO
    }

    public boolean forceTerminateGame(boolean vote1, boolean vote2, boolean vote3) {
        // TODO
        return false;
    }

    public void nextTurn(String callerUsername) {
        // TODO
    }

}
