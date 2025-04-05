package models.tools;

import models.enums.types.ToolTypes;

public class Shear extends Tool {
    public Shear() {
        super("Shears", ToolTypes.SHEARS, 4);
    }

    public void shearSheep() {
        System.out.println("Shearing a sheep...");
    }
}


