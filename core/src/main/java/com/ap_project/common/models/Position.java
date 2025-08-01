package com.ap_project.common.models;

public class Position {
    private int x;
    private int y;

    public Position() {
    }

    public Position(Position other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
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

    public static Position getPositionByStrings(String xString, String yString) {
        if (!xString.matches("\\d+") || !yString.matches("\\d+")) {
            return null;
        }

        int x, y;
        x = Integer.parseInt(xString);
        y = Integer.parseInt(yString);
        return new Position(x, y);
    }

    public static boolean areClose(Position position1, Position position2) {
        int x1 = position1.getX();
        int y1 = position1.getY();
        int x2 = position2.getX();
        int y2 = position2.getY();
        return Math.abs(x1 - x2) <= 1 && Math.abs(y1 - y2) <= 1 && !(x1 == x2 && y1 == y2);
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
