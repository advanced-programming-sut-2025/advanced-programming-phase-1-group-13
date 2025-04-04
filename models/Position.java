package models;

public class Position {
    private int x;
    private int y;

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
