package com.ap_project.common.models.tools;

import com.ap_project.common.models.Animal;
import com.ap_project.common.models.App;
import com.ap_project.common.models.Item;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.Skill;
import com.ap_project.common.models.enums.SkillLevel;
import com.ap_project.common.models.enums.types.ToolMaterial;
import com.ap_project.common.models.enums.types.ToolType;

import java.util.ArrayList;
import java.util.HashMap;

public class Shear extends Tool {
    public Shear() {
        super(ToolType.SHEARS);
    }

    public Shear(ToolMaterial material) {
        super(ToolType.SHEARS, material);
    }

    @Override
    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills, Tool tool) {
        return 4;
    }

    @Override
    public Skill getRelatedSkill() {
        return super.getRelatedSkill();
    }

    public void useTool(Animal animal) {
        User player = App.getLoggedIn();
        int energyNeeded = calculateEnergyNeeded(player.getSkillLevels(), player.getCurrentTool());
        if (!player.isEnergyUnlimited() && energyNeeded >= player.getEnergy()) {
            player.faint();
            return;
        }
        HashMap<Item, Integer> items = player.getBackpack().getItems();
        for (Item item : animal.getProducedProducts()) {
            items.put(item, items.getOrDefault(item, 0) + 1);
        }
        animal.setProducedProducts(new ArrayList<>());
        player.decreaseEnergyBy(4);
    }
}


