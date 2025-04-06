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
}
