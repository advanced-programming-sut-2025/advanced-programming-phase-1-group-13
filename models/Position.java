package models;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addToX(int amount) {
        this.x += amount;
    }

    public void addToY(int amount) {
        this.y += amount;
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
