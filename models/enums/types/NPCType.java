package models.enums.types;

public enum NPCType {
    CLINT(Role.BLACKSMITH),
    MORRIS(Role.SHOPKEEPER),
    PIERRE(Role.SHOPKEEPER),
    ROBIN(Role.SHOPKEEPER),
    WILLY(Role.SHOPKEEPER),
    MARNIE(Role.SHOPKEEPER),
    GUS(Role.SHOPKEEPER),
    SEBASTIAN(Role.VILLAGER),
    ABIGAIL(Role.VILLAGER),
    HARVEY(Role.VILLAGER),
    LEA(Role.VILLAGER);

    Role role;

    NPCType(Role role) {
        this.role = role;
    }
}
