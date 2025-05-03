package models.tools;

import models.Animal;
import models.App;
import models.Item;
import models.User;
import models.enums.Skill;
import models.enums.environment.Direction;

import java.util.ArrayList;
import java.util.HashMap;

public class MilkPail extends Tool {

    public MilkPail(int energyNeeded, Skill relatedSkill) {
        super(energyNeeded, relatedSkill);
    }

    @Override
    public int calculateEnergyNeeded() {
        return super.calculateEnergyNeeded();
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


