package models;

import models.enums.environment.Time;
import models.enums.environment.Weather;

import java.util.ArrayList;

public class Game {
    private ArrayList<Farm> farms;
    private ArrayList<Shop> shops;
    private ArrayList<User> players; // The 3 players
    private Object gameMap; // TODO!!!!!!!!!!!!!!!!
    private Time time;
    private Weather currentWeather;
    // TODO: complete the class!


    public Game(ArrayList<User> players) {
        this.players = players;
        this.farms = new ArrayList<>();
        this.shops = new ArrayList<>();
    }


    public void newGame(String username_1, String username_2, String username_3) {
        // TODO
    }

    public void setGameMap(int map_number) {
        // TODO
    }

    public void loadGame(String callerUsername) {
        // TODO
    }

    public boolean forceTerminateGame(boolean vote_1, boolean vote_2, boolean vote_3) {
        // TODO
        return false;
    }

    public void nextTurn(String callerUsername) {
        // TODO
    }

}
