package com.github.joaoalberis.albisrpg.classes;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;

public enum PlayerClass {
    Warrior(Component.translatable("class.albisrpg.character.warrior").getString(), 8, 4, 2, 7),
    Mage(Component.translatable("class.albisrpg.character.mage").getString(), 2, 3, 9, 4),
    Rogue(Component.translatable("class.albisrpg.character.rogue").getString(), 5, 9, 2, 4);

    private String name;
    private final int strength;
    private final int agility;
    private final int intelligence;
    private final int vitality;

    PlayerClass( String name, int strength, int agility, int intelligence, int vitality ) {
        this.name = name;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.vitality = vitality;
    }

    public String getName() {
        return this.name;
    }

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getVitality() {
        return vitality;
    }

    /**
     * Used for internal purposes only.
     * Do not use this.
     * @param name translated
     */
    @ApiStatus.Internal
    public void setName( String name ) {
        this.name = name;
    }

    private static final Map<String, PlayerClass> classes;

    /**
     * Dynamically retrieves a {@link PlayerClass} using an id: string
     * @param id to be retrieved
     * @return {@link PlayerClass} retrieved via the id
     * @author Nxkoo
     */
    public static PlayerClass fromString(String id) {
        var result = classes.get(id.toLowerCase());

        if (result == null) throw new IllegalArgumentException("Class does not exist: " + id);

        return result;
    }

    static {
        classes = new HashMap<>();

        for (PlayerClass pc : PlayerClass.values()) {
            classes.put(pc.name().toLowerCase(), pc);
        }
    }
}
