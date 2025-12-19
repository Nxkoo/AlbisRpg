package com.github.joaoalberis.albisrpg.capability;

import com.github.joaoalberis.albisrpg.network.NetworkHandler;
import com.github.joaoalberis.albisrpg.network.PlayerCapabilitySyncClient;
import com.github.joaoalberis.albisrpg.network.PlayerCapabilitySyncServer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.PacketDistributor;

public class PlayerCapabilityImplementation implements PlayerCapabilityInterface{

    private static final String NBT_KEY_PLAYER_CLASS = "player_class";
    private static final String NBT_KEY_LEVEL = "level";
    private static final String NBT_KEY_EXPERIENCE = "experience";
    private static final String NBT_KEY_STRENGTH = "strength";
    private static final String NBT_KEY_INTELLIGENCE = "intelligence";
    private static final String NBT_KEY_AGILITY = "agility";
    private static final String NBT_KEY_VITALITY = "vitality";

    private String playerClass = "";
    private int level = 1;
    private int experience = 0;
    private int strength = 1;
    private int intelligence = 1;
    private int agility = 1;
    private int vitality = 1;

    @Override
    public void syncToServer(Entity entity){
        if (entity.level().isClientSide()){
            NetworkHandler.INSTANCE.sendToServer(new PlayerCapabilitySyncServer(this));
        }
    }

    @Override
    public void syncToClient(Entity entity){
        if (!entity.level().isClientSide() && entity instanceof ServerPlayer sp){
            NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sp), new PlayerCapabilitySyncClient(this, entity.getId()));
        }
    }

    @Override
    public String getPlayerClass() {
        return this.playerClass;
    }

    @Override
    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getExperience() {
        return this.experience;
    }

    @Override
    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public int getStrength() {
        return this.strength;
    }

    @Override
    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public int getIntelligence() {
        return this.intelligence;
    }

    @Override
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    @Override
    public int getAgility() {
        return this.agility;
    }

    @Override
    public void setAgility(int agility) {
        this.agility = agility;
    }

    @Override
    public int getVitality() {
        return this.vitality;
    }

    @Override
    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString(NBT_KEY_PLAYER_CLASS, this.playerClass);
        compoundTag.putInt(NBT_KEY_LEVEL, this.level);
        compoundTag.putInt(NBT_KEY_EXPERIENCE, this.experience);
        compoundTag.putInt(NBT_KEY_STRENGTH, this.strength);
        compoundTag.putInt(NBT_KEY_INTELLIGENCE, this.intelligence);
        compoundTag.putInt(NBT_KEY_AGILITY, this.agility);
        compoundTag.putInt(NBT_KEY_VITALITY, this.vitality);

        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag == null){
            tag = serializeNBT();
        }
        CompoundTag nbt = (CompoundTag) tag;
        if (nbt == null) {
            nbt = (CompoundTag) serializeNBT();
        }
        this.playerClass = nbt.getString(NBT_KEY_PLAYER_CLASS);
        this.level = nbt.getInt(NBT_KEY_LEVEL);
        this.experience = nbt.getInt(NBT_KEY_EXPERIENCE);
        this.strength = nbt.getInt(NBT_KEY_STRENGTH);
        this.intelligence = nbt.getInt(NBT_KEY_INTELLIGENCE);
        this.agility = nbt.getInt(NBT_KEY_AGILITY);
        this.vitality = nbt.getInt(NBT_KEY_VITALITY);
    }
}
