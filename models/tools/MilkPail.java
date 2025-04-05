package models.tools;

import models.enums.types.ToolTypes;

public class MilkPail extends Tool {
    public MilkPail() {
        super("Milk Pail", ToolTypes.MILK_PAIL, 4);
    }

    public void milkCow() {
        System.out.println("Milking a cow...");
    }
}


