package com.project.models;

import java.util.List;

public class Path {
    private List<Tile> pathTiles;
    private int distanceInTiles, numOfTurns, energyNeeded;

    public List<Tile> getPathTiles() { return pathTiles; }
    public void setPathTiles(List<Tile> p) { pathTiles = p; }
    public int getDistanceInTiles() { return distanceInTiles; }
    public void setDistanceInTiles(int d) { distanceInTiles = d; }
    public int getNumOfTurns() { return numOfTurns; }
    public void setNumOfTurns(int t) { numOfTurns = t; }
    public int getEnergyNeeded() { return energyNeeded; }
    public void setEnergyNeeded(int e) { energyNeeded = e; }
}
