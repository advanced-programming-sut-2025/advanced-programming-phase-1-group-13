package com.ap_project.models.enums.types;

import com.ap_project.models.enums.Skill;
import com.ap_project.models.inventory.Backpack;
import com.ap_project.models.tools.*;

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
        Tool result;
        switch (toolType) {
            case HOE:
                result = new Hoe(material);
                break;
            case PICKAXE:
                result = new Pickaxe(material);
                break;
            case AXE:
                result = new Axe(material);
                break;
            case WATERING_CAN:
                result = new WateringCan(material);
                break;
            case FISHING_ROD:
                result = new FishingRod(material);
                break;
            case SCYTHE:
                result = new Scythe(material);
                break;
            case MILK_PAIL:
                result = new MilkPail(material);
                break;
            case SHEARS:
                result = new Shear(material);
                break;
            case TRASH_CAN:
                result = new TrashCan(material);
                break;
            default:
                result = null;
        }
        return result;
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

