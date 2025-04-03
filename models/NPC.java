package models;

import models.enums.environment.Time;
import models.enums.types.NPCType;
import models.enums.types.Role;

import java.util.ArrayList;
import java.util.HashMap;

public class NPC {
    NPCType name;
    Role role;
    ArrayList<String> dialog;
    HashMap<Time, Position> schedule;

    public NPC(NPCType name, Role role, ArrayList<String> dialog, HashMap<Time, Position> schedule) {
        this.name = name;
        this.role = role;
        this.dialog = dialog;
        this.schedule = schedule;
    }

    public NPCType getName() {
        return name;
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

    }
}
