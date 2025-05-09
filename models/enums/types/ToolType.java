package models.enums.types;

import models.enums.Skill;

public enum ToolType implements ItemType {
    HOE("Hoe", Skill.FARMING),
    PICKAXE("Pickaxe", Skill.MINING),
    AXE("Axe", Skill.FORAGING),
    WATERING_CAN("Watering can", Skill.FARMING),
    FISHING_ROD("Fishing rod", Skill.FISHING),
    SCYTHE("Scythe", null),
    MILK_PAIL("Milk pail", null),
    SHEARS("Shears", null),
    BACKPACK("Backpack", null),
    TRASH_CAN("Trash can", null);

    private final String name;
    private final Skill relatedSkill;


    ToolType(String name, Skill relatedSkill) {
        this.name = name;
        this.relatedSkill = relatedSkill;
    }

    public static ToolType getToolTypeByName(String name) {
        for (ToolType toolType : ToolType.values()) {
            if (toolType.name.equals(name)) {
                return toolType;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Skill getRelatedSkill() {
        return relatedSkill;
    }

    public static String getFullList() {
        StringBuilder sb = new StringBuilder();
        for (ToolType t : ToolType.values()) {
            sb.append(t.getName()).append("\n");
        }
        return sb.toString();
    }
}

