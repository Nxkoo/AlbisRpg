package com.github.joaoalberis.albisrpg.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.INBTSerializable;

public interface PlayerCapabilityInterface extends INBTSerializable<CompoundTag> {

    void syncToClient(Entity entity);
    void syncToServer(Entity entity);
    String getPlayerClass();
    void setPlayerClass(String playerClass);
    int getLevel();
    void setLevel(int level);
    int getExperience();
    void setExperience(int experience);
    int getStrength();
    void setStrength(int strength);
    int getIntelligence();
    void setIntelligence(int intelligence);
    int getAgility();
    void setAgility(int agility);
    int getVitality();
    void setVitality(int vitality);

}
