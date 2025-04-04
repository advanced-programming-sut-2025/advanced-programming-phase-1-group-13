package models;
import models.enums.types.ToolTypes;

public class Tools {
    private String name;
    private ToolTypes type;
    private int energyCost;

    public Tools(String name, ToolTypes type, int energyCost) {
        this.name = name;
        this.type = type;
        this.energyCost = energyCost;
    }

    public String getName() {
        return name;
    }

    public ToolTypes getType() {
        return type;
    }

    public int getEnergyCost() {
        return energyCost;
    }
}

