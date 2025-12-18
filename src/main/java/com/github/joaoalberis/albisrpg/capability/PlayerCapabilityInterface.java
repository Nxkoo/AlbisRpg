package com.github.joaoalberis.albisrpg.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface PlayerCapabilityInterface extends INBTSerializable<CompoundTag> {

    String getPlayerClass();
    void setPlayerClass(String playerClass);

}
