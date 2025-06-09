package com.project.models.enums.types;

import com.project.models.enums.Skill;
import com.project.models.inventory.Backpack;
import com.project.models.tools.*;

public enum ToolType implements ItemType {
    HOE("Hoe", Skill.FARMING),
    PICKAXE("Pickaxe", Skill.MINING),
    AXE("Axe", Skill.FORAGING),
    WATERING_CAN("Watering can", Skill.FARMING),
    FISHING_ROD("Fishing rod", Skill.FISHING),
    SCYTHE("Scythe", null),
    MILK_PAIL("Milk pail", null),
    SHEARS("Shears", null),
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

    public static Tool getToolByToolTypeAndMaterial(ToolType toolType, ToolMaterial material) {
        return switch (toolType) {
            case HOE -> new Hoe(material);
            case PICKAXE -> new Pickaxe(material);
            case AXE -> new Axe(material);
            case WATERING_CAN -> new WateringCan(material);
            case FISHING_ROD -> new FishingRod(material);
            case SCYTHE -> new Scythe(material);
            case MILK_PAIL -> new MilkPail(material);
            case SHEARS -> new Shear(material);
            case TRASH_CAN -> new TrashCan(material);
        };
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

