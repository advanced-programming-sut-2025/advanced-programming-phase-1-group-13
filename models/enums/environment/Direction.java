package models.enums.environment;

import models.Position;

public enum Direction {
    UP("up", 0, 1),
    DOWN("down", 0, -1),
    RIGHT("right", 1, 0),
    LEFT("left", -1, 0),
    UP_RIGHT("up-right", 1, 1),
    UP_LEFT("up-left", -1, 1),
    DOWN_RIGHT("down-right", 1, -1),
    DOWN_LEFT("down-left", -1, -1);

    private final String displayName;
    private final int deltaX;
    private final int deltaY;

    Direction(String displayName, int deltaX, int deltaY) {
        this.displayName = displayName;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
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

    public Position getNewPosition(Position currentPosition, Direction direction) {
        Position newPosition = currentPosition;
        newPosition.addToX(direction.deltaX);
        newPosition.addToY(direction.deltaY);
        return newPosition;
    }

}
