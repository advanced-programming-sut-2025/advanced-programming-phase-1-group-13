package models;
import models.enums.types.ToolTypes;

public class Shears extends Tool {
    public Shears() {
        super("Shears", ToolTypes.SHEARS, 4);
    }

    public void shearSheep() {
        System.out.println("Shearing a sheep...");
    }
}


