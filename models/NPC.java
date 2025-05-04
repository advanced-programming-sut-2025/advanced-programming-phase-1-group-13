package models;

import models.enums.environment.Time;
import models.enums.types.NPCType;
import models.enums.types.Role;

import java.util.ArrayList;
import java.util.HashMap;

public class NPC {
    NPCType type;
    Role role;
    ArrayList<String> dialog;

    public NPC(NPCType type) {
        this.type = type;
        this.role = type.getRole();
        this.dialog = new ArrayList<>();
    }

    public NPCType getType() {
        return type;
    }

    public Role getRole() {
        return role;
    }

    public ArrayList<String> getDialog() {
        return dialog;
    }

    public HashMap<Time, Position> getSchedule() {
        return schedule;
    }

    public void addDialog(String sentence) {
        // TODO
    }
}
