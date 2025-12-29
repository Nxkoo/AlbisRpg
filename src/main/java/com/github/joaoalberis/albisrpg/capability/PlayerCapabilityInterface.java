package com.github.joaoalberis.albisrpg.capability;

import com.github.joaoalberis.albisrpg.classes.PlayerClass;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.INBTSerializable;

public interface PlayerCapabilityInterface extends INBTSerializable<CompoundTag> {

    void setUpCharacter(PlayerClass playerClass);
    void addExperience(float amount);
    void syncToClient(Entity entity);
    void syncToServer(Entity entity);
    PlayerClass getPlayerClass();
    void setPlayerClass(PlayerClass playerClass);
    int getLevel();
    void setLevel(int level);
    float getExperienceToNextLevel();
    void setExperienceToNextLevel(float experienceToNextLevel);
    int getPoints();
    void setPoints(int level);
    float getExperience();
    void setExperience(float experience);
    int getStrength();
    void setStrength(int strength);
    int getIntelligence();
    void setIntelligence(int intelligence);
    int getAgility();
    void setAgility(int agility);
    int getVitality();
    void setVitality(int vitality);
    int getDamage();
    void setDamage(int damage);
    int getDefense();
    void setDefense(int defense);
    float getSpeed();
    void setSpeed(float speed);
    int getMana();
    void setMana(int mana);
    int getMaxMana();
    void setMaxMana(int maxMana);
    float getHealth();
    void setHealth(float health);
    float getMaxHealth();
    void setMaxHealth(float maxHealth);
}
