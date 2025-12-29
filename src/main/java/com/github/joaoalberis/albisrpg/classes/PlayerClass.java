package com.github.joaoalberis.albisrpg.classes;

import net.minecraft.network.chat.Component;

public enum PlayerClass {
    Warrior(Component.translatable("class.albisrpg.character.warrior").getString(), 8, 4, 2, 7),
    Mage(Component.translatable("class.albisrpg.character.mage").getString(), 2, 3, 9, 4),
    Rogue(Component.translatable("class.albisrpg.character.rogue").getString(), 5, 9, 2, 4);

    private String name;
    private int strength, agility, intelligence, vitality;

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

    public void setName( String name ) {
        this.name = name;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }
}
