package models;

public class Stone {
    private Mineral mineral;
    private Position position;

    public Stone(Position position) {
        this.position = position;
        this.mineral = new Mineral(position); // Assuming Stone contains a mineral
    }

    public Mineral getMineral() {
        return mineral;
    }

    public Position getPosition() {
        return position;
    }
}