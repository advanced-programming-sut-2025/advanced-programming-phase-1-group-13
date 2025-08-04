package com.ap_project.common.models.tools;

import com.ap_project.common.models.Tile;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.Skill;
import com.ap_project.common.models.enums.SkillLevel;
import com.ap_project.common.models.enums.types.ToolMaterial;
import com.ap_project.common.models.enums.types.ToolType;

import java.util.HashMap;

public class TrashCan extends Tool {

    public TrashCan() {
        super(ToolType.TRASH_CAN);
    }

    public TrashCan(ToolMaterial material) {
        super(ToolType.TRASH_CAN, material);
    }

    @Override
    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills, Tool tool) {
        return 0;
    }

    @Override
    public Skill getRelatedSkill() {
        return super.getRelatedSkill();
    }

    @Override
    public void useTool(Tile tile, User player) {
        System.out.println("To use the trash can, type this command:\n" +
                "inventory trash -i <item’s name> -n <number>");
    }

    public static double calculateMoneyToEarnViaTrashCan(ToolMaterial trashCanMaterial,
                                                         Integer numOfItemToThrowAway,
                                                         Integer itemPrice) {
        double moneyToEarn = 0.0;
        switch (trashCanMaterial) {
            case BASIC:
                moneyToEarn = 0.0;
                break;
            case COPPER:
                moneyToEarn = (0.15 * numOfItemToThrowAway * itemPrice);
                break;
            case IRON_ORE:
                moneyToEarn = (0.3 * numOfItemToThrowAway * itemPrice);
                break;
            case GOLD:
                moneyToEarn = (0.45 * numOfItemToThrowAway * itemPrice);
                break;
            case IRIDIUM:
                moneyToEarn = (0.6 * numOfItemToThrowAway * itemPrice);
                break;
        }
        return moneyToEarn;
    }
}
