package com.ap_project.common.models.enums.environment;

import com.ap_project.common.models.Position;

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

        Direction result;
        switch (name.toLowerCase()) {
            case "up":
            case "u":
                result = UP;
                break;
            case "down":
            case "d":
                result = DOWN;
                break;
            case "right":
            case "r":
                result = RIGHT;
                break;
            case "left":
            case "l":
                result = LEFT;
                break;
            case "up-right":
            case "ur":
                result = UP_RIGHT;
                break;
            case "up-left":
            case "ul":
                result = UP_LEFT;
                break;
            case "down-right":
            case "dr":
                result = DOWN_RIGHT;
                break;
            case "down-left":
            case "dl":
                result = DOWN_LEFT;
                break;
            default:
                result = null;
        }
        return result;
    }

    @Override
    public String toString() {
        if (this == Direction.UP || this == Direction.UP_RIGHT || this == Direction.UP_LEFT) {
            return "Up";
        }
        if (this == Direction.DOWN || this == Direction.DOWN_RIGHT || this == Direction.DOWN_LEFT) {
            return "Down";
        }
        if (this == Direction.RIGHT) {
            return "Right";
        }
        if (this == Direction.LEFT) {
            return "Left";
        }
        return null;
    }
}
