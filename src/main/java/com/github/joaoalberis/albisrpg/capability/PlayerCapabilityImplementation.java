package com.github.joaoalberis.albisrpg.capability;

import net.minecraft.nbt.CompoundTag;

public class PlayerCapabilityImplementation implements PlayerCapabilityInterface{

    private static String NBT_KEY_PLAYER_CLASS = "player_class";

    private String playerClass = "";

    @Override
    public String getPlayerClass() {
        return this.playerClass;
    }

    @Override
    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString(NBT_KEY_PLAYER_CLASS, this.playerClass);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.playerClass = nbt.getString(NBT_KEY_PLAYER_CLASS);
    }
}
