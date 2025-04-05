package models;
import models.enums.types.ToolTypes;

public class Scythe extends Tool {
    public Scythe() {
        super("Scythe", ToolTypes.SCYTHE, 2); // Fixed energy cost
    }

    public void cutGrass() {
        System.out.println("Scythe is cutting grass.");
    }
}
