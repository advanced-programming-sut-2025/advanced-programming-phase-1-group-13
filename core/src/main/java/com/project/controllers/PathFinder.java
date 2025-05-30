package com.project.controllers;

import com.project.models.*;
import com.project.models.enums.types.ShopType;
import com.project.models.enums.types.TileType;
import java.util.*;

public class PathFinder {
    private User player;
    private boolean[][] visited;
    private Position[][] parent;
    private Tile[][] farmMap;
    private int width, height;

    public PathFinder(User player) {
        this.player = player;
    }

    public Result respondForWalkRequest(String xStr, String yStr) {
        try {
            int x = Integer.parseInt(xStr);
            int y = Integer.parseInt(yStr);
            Position dest = new Position(x, y);
            Position orig = player.getPosition();

            Path p = findValidPath(orig, dest);
            if (p == null) {
                return new Result(false, "No valid path found!");
            }

            String msg = String.format(
                    "Found path:\nDistance: %d tiles\nTurns: %d\nEnergy needed: %d",
                    p.getDistanceInTiles(), p.getNumOfTurns(), p.getEnergyNeeded());
            return new Result(true, msg);
        } catch (NumberFormatException e) {
            return new Result(false, "Invalid coordinates!");
        }
    }

    public Result walk(Path p, String conf) {
        boolean confirm = conf.equalsIgnoreCase("y");
        if (!confirm) {
            return new Result(false, "Walk cancelled");
        }
        int energyCost = p.getEnergyNeeded();
        if (player.getEnergy() < energyCost) {
            return new Result(false, "Not enough energy");
        }
        player.setEnergy(player.getEnergy() - energyCost);
        Position last = p.getPathTiles().get(p.getPathTiles().size() - 1).getPosition();
        player.changePosition(last);
        if (App.getCurrentGame().isInNPCVillage()) {
            Tile tile = App.getCurrentGame().getGameMap().getTileByPosition(last);
            if (tile.getType().equals(TileType.SHOP)) {
                App.setCurrentShop(whichShop(tile));
                System.out.println(whichShop(tile));
            } else {
                App.setCurrentShop(null);
                System.out.println("set null");
            }
        }
        return new Result(true, "Walked! Energy used=" + energyCost);
    }

    public static Shop whichShop(Tile tile) {
        for (Shop shop : App.getCurrentGame().getVillage().getShops()) {
            if (shop.containsPosition(tile.getPosition())) {
                return shop;
            }
        }

        return null;
    }

    public Path findValidPath(Position orig, Position dest) {
        Farm f = player.getFarm();
        width = f.getWidth();
        height = f.getHeight();
        farmMap = new Tile[height][width];

        for (Tile t : f.getFarmTiles()) {
            if (t == null) continue;
            Position pos = t.getPosition();
            if (pos != null && inBounds(pos)) {
                farmMap[pos.getY()][pos.getX()] = t;
            }
        }

        if (!inBounds(orig) || !isAllowed(farmMap[orig.getY()][orig.getX()]) ||
                !inBounds(dest) || !isAllowed(farmMap[dest.getY()][dest.getX()])) {
            return null;
        }

        visited = new boolean[height][width];
        parent = new Position[height][width];

        if (!bfs(orig, dest)) {
            return null;
        }
        return buildPath(orig, dest);
    }

    private boolean bfs(Position orig, Position dest) {
        Queue<Position> queue = new LinkedList<>();
        queue.offer(orig);
        visited[orig.getY()][orig.getX()] = true;

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            if (current.getX() == dest.getX() && current.getY() == dest.getY()) {
                return true;
            }
            for (int[] dir : directions) {
                int nx = current.getX() + dir[0];
                int ny = current.getY() + dir[1];
                if (inBounds(nx, ny) && !visited[ny][nx] && isAllowed(farmMap[ny][nx])) {
                    visited[ny][nx] = true;
                    parent[ny][nx] = current;
                    queue.offer(new Position(nx, ny));
                }
            }
        }
        return false;
    }

    private Path buildPath(Position origin, Position destination) {
        List<Position> positions = new ArrayList<>();
        Position current = destination;
        while (current != null && (current.getX() != origin.getX() || current.getY() != origin.getY())) {
            positions.add(current);
            current = parent[current.getY()][current.getX()];
        }
        positions.add(origin);
        Collections.reverse(positions);

        List<Tile> pathTiles = new ArrayList<>();
        for (Position p : positions) {
            pathTiles.add(farmMap[p.getY()][p.getX()]);
        }

        int turns = calculateTurns(positions);
        Path path = new Path();
        path.setPathTiles((ArrayList<Tile>) pathTiles);
        path.setDistanceInTiles(pathTiles.size() - 1);
        path.setNumOfTurns(turns);
        path.setEnergyNeeded((pathTiles.size() + (10 * turns)) / 20);
        return path;
    }

    private int calculateTurns(List<Position> path) {
        if (path.size() < 3) return 0;
        int turns = 0;
        int prevDirX = path.get(1).getX() - path.get(0).getX();
        int prevDirY = path.get(1).getY() - path.get(0).getY();
        for (int i = 2; i < path.size(); i++) {
            int curDirX = path.get(i).getX() - path.get(i-1).getX();
            int curDirY = path.get(i).getY() - path.get(i-1).getY();
            if (curDirX != prevDirX || curDirY != prevDirY) turns++;
            prevDirX = curDirX;
            prevDirY = curDirY;
        }
        return turns;
    }

    private boolean inBounds(Position p) {
        return inBounds(p.getX(), p.getY());
    }

    private boolean inBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private boolean isAllowed(Tile t) {
        if (t == null) return false;
        if (t.getItemPlacedOnTile() != null) return false;
        TileType tp = t.getType();
        return tp != TileType.WATER && tp != TileType.STONE
                && tp != TileType.TREE && tp != TileType.WOOD_LOG;
    }
}
