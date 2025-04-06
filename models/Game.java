package models;

import java.util.ArrayList;

public class Game {
    private ArrayList<Farm> farms;
    private ArrayList<Shop> shops;
    private ArrayList<User> players; // The 3 players
    // TODO: complete the class!


    public Game(ArrayList<User> players) {
        this.players = players;
        this.farms = new ArrayList<>();
        this.shops = new ArrayList<>();
    }
    /*
    private List<User> players;
    private Object gameMap;
    private Object time;
    private Weather currentWeather;

    public void newGame(String username_1, String username_2, String username_3) {

    }

    public void setGameMap(int map_number) {

    }

    public void loadGame(String callerUsername) {

    }

    public boolean forceTerminateGame(boolean vote_1, boolean vote_2, boolean vote_3) {

    }

    public void nextTurn(String callerUsername) {

    }
    */
}
