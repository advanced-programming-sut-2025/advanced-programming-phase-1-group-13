package controllers;

import models.*;
import models.enums.types.TileType;
import java.util.*;

public class PathFinder {
    private User player;
    private boolean[][] visited;
    private Position[][] parent;
    private Tile[][] farmMap;
    private int width, height;

    public PathFinder(User player) { this.player = player; }

    public Result walk(Path p, String conf) {
        boolean c = conf.equalsIgnoreCase("y");
        if(!c) return new Result(false,"Walk cancelled");
        if(player.getEnergy()<p.getEnergyNeeded())
            return new Result(false,"Not enough energy");
        Position last = p.getPathTiles().get(p.getPathTiles().size()-1).getPosition();
        player.changePosition(last);
        player.setEnergy(player.getEnergy()-p.getEnergyNeeded());
        return new Result(true,"Walked! Energy used=" + p.getEnergyNeeded());
    }

    public Path findValidPath(Position orig, Position dest) {
        Farm f = player.getFarm(); width=f.getWidth(); height=f.getHeight();
        farmMap = new Tile[width][height];
        for(Tile t: f.getFarmTiles()) {
            Position pos = t.getPosition();
            farmMap[pos.getX()][pos.getY()] = t;
        }
        if(!inBounds(dest) || !isAllowed(farmMap[dest.getX()][dest.getY()]))
            return null;

        visited = new boolean[width][height];
        parent = new Position[width][height];
        if(!dfs(orig.getX(), orig.getY(), dest)) return null;
        return buildPath(orig, dest);
    }

    private boolean dfs(int x, int y, Position dest) {
        visited[x][y] = true;
        if(x==dest.getX() && y==dest.getY()) return true;
        int[][] d={{0,1},{1,0},{0,-1},{-1,0}};
        for(int[] dir: d) {
            int nx=x+dir[0], ny=y+dir[1];
            if(inBounds(nx,ny) && !visited[nx][ny] && isAllowed(farmMap[nx][ny])) {
                parent[nx][ny] = new Position(x,y);
                if(dfs(nx, ny, dest)) return true;
            }
        }
        return false;
    }

    private Path buildPath(Position o, Position d) {
        List<Position> ps = new ArrayList<>();
        Position cur = d;
        while(cur!=null && !cur.equals(o)) {
            ps.add(cur);
            cur = parent[cur.getX()][cur.getY()];
        }
        ps.add(o);
        Collections.reverse(ps);

        List<Tile> tiles=new ArrayList<>();
        for(Position p:ps) tiles.add(farmMap[p.getX()][p.getY()]);

        Path p = new Path();
        p.setPathTiles((ArrayList<Tile>)tiles);
        p.setDistanceInTiles(tiles.size()-1);
        p.setNumOfTurns((tiles.size()-1+9)/10);
        p.setEnergyNeeded(tiles.size()-1);
        return p;
    }

    private boolean inBounds(Position p){ return inBounds(p.getX(), p.getY()); }
    private boolean inBounds(int x,int y){ return x>=0&&x<width&&y>=0&&y<height; }

    private boolean isAllowed(Tile t) {
        if(t.getItemPlaced()!=null) return false;
        TileType tp = t.getType();
        return tp!=TileType.WATER && tp!=TileType.STONE
                && tp!=TileType.TREE && tp!=TileType.WOOD_LOG;
    }
}
