package models.tools;

import models.Animal;
import models.App;
import models.Item;
import models.User;
import models.enums.Skill;
import models.enums.SkillLevel;
import models.enums.types.ToolMaterial;
import models.enums.types.ToolType;

import java.util.ArrayList;
import java.util.HashMap;
//
public class Shear extends Tool {
    public Shear() {
        super(ToolType.SHEARS);
    }

    public Shear(ToolMaterial material) {
        super(ToolType.SHEARS, material);
    }

    @Override
    public int calculateEnergyNeeded(HashMap<Skill, SkillLevel> playerSkills) {
        return super.calculateEnergyNeeded(playerSkills);
    }

    @Override
    public Skill getRelatedSkill() {
        return super.getRelatedSkill();
    }

    public void useTool(Animal animal) {
        User player = App.getLoggedIn();
        HashMap<Item, Integer> items = player.getBackpack().getItems();
        for (Item item : animal.getProducedProducts()) {
            items.put(item, items.getOrDefault(item, 0) + 1);
        }
        animal.setProducedProducts(new ArrayList<>());
        player.setEnergy(player.getEnergy() - 4);
    }
}


