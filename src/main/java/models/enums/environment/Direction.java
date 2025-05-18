package models.enums.environment;

import models.Position;

public enum Direction {
    UP("up", -1, 0),
    DOWN("down", 1, 0),
    LEFT("left", 0, -1),
    RIGHT("right", 0, 1),
    UP_RIGHT("up-right", -1, -1),
    UP_LEFT("up-left", -1, 1),
    DOWN_RIGHT("down-right", 1, -1),
    DOWN_LEFT("down-left", 1, 1);

    private final String displayName;
    private final int deltaX;
    private final int deltaY;

    Direction(String displayName, int deltaX, int deltaY) {
        this.displayName = displayName;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public static Position getNewPosition(Position currentPosition, Direction direction) {
        Position newPosition = new Position(currentPosition.getX(), currentPosition.getY());
        newPosition.addToX(direction.deltaX);
        newPosition.addToY(direction.deltaY);
        return newPosition;
    }

    public static Direction getDirectionByDisplayName(String name) {
        if (name == null) {
            return null;
        }
        return switch (name.toLowerCase()) {
            case "up" -> UP;
            case "u" -> UP;
            case "down" -> DOWN;
            case "d" -> DOWN;
            case "right" -> RIGHT;
            case "r" -> RIGHT;
            case "left" -> LEFT;
            case "l" -> LEFT;
            case "up-right" -> UP_RIGHT;
            case "ur" -> UP_RIGHT;
            case "up-left" -> UP_LEFT;
            case "ul" -> UP_LEFT;
            case "down-right" -> DOWN_RIGHT;
            case "dr" -> DOWN_RIGHT;
            case "down-left" -> DOWN_LEFT;
            case "dl" -> DOWN_LEFT;
            default -> null;
        };
    }
}