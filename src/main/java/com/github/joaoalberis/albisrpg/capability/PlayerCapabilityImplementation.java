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
    private static final String NBT_KEY_EXPERIENCE_TO_NEXT_LEVEL = "experience_to_next_level";
    private static final String NBT_KEY_POINTS = "points";
    private static final String NBT_KEY_STRENGTH = "strength";
    private static final String NBT_KEY_INTELLIGENCE = "intelligence";
    private static final String NBT_KEY_AGILITY = "agility";
    private static final String NBT_KEY_VITALITY = "vitality";
    private static final String NBT_KEY_DAMAGE = "damage";
    private static final String NBT_KEY_DEFENSE = "defense";
    private static final String NBT_KEY_SPEED = "speed";
    private static final String NBT_KEY_MANA = "mana";
    private static final String NBT_KEY_MAX_MANA = "max_mana";
    private static final String NBT_KEY_HEALTH = "health";
    private static final String NBT_KEY_MAX_HEALTH = "max_health";


    private String playerClass = "";
    private int level = 1;
    private float experience = 0;
    private float experienceToNextLevel = 10;
    private int points = 0;
    private int strength = 1;
    private int intelligence = 1;
    private int agility = 1;
    private int vitality = 1;
    private int damage = 1;
    private int defense = 1;
    private float speed = 1;
    private int mana = 1;
    private int maxMana = 1;
    private int health = 1;
    private float maxHealth = 1;

    @Override
    public void addExperience(float amount){
        if (amount + experience >= experienceToNextLevel){
            this.experience = (amount + experience) - experienceToNextLevel;
            this.experienceToNextLevel = 1;
            this.level++;
            this.points++;
        }else {
            this.experience += amount;
        }
    }

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
    public float getExperience() {
        return this.experience;
    }

    @Override
    public void setExperience(float experience) {
        this.experience = experience;
    }

    @Override
    public float getExperienceToNextLevel() {
        return this.experienceToNextLevel;
    }

    @Override
    public void setExperienceToNextLevel(float experienceToNextLevel) {
        this.experienceToNextLevel = experienceToNextLevel;
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    @Override
    public void setPoints(int points) {
        this.points = points;
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
    public int getDamage() {
        return damage;
    }

    @Override
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public void setDefense(int defense) {
        this.defense = defense;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public void setMana(int mana) {
        this.mana = mana;
    }

    @Override
    public int getMaxMana() {
        return maxMana;
    }

    @Override
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public float getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString(NBT_KEY_PLAYER_CLASS, this.playerClass);
        compoundTag.putInt(NBT_KEY_LEVEL, this.level);
        compoundTag.putFloat(NBT_KEY_EXPERIENCE, this.experience);
        compoundTag.putFloat(NBT_KEY_EXPERIENCE_TO_NEXT_LEVEL, this.experienceToNextLevel);
        compoundTag.putInt(NBT_KEY_POINTS, this.points);
        compoundTag.putInt(NBT_KEY_STRENGTH, this.strength);
        compoundTag.putInt(NBT_KEY_INTELLIGENCE, this.intelligence);
        compoundTag.putInt(NBT_KEY_AGILITY, this.agility);
        compoundTag.putInt(NBT_KEY_VITALITY, this.vitality);
        compoundTag.putInt(NBT_KEY_DAMAGE, this.damage);
        compoundTag.putInt(NBT_KEY_DEFENSE, this.defense);
        compoundTag.putFloat(NBT_KEY_SPEED, this.speed);
        compoundTag.putInt(NBT_KEY_MANA, this.mana);
        compoundTag.putInt(NBT_KEY_MAX_MANA, this.maxMana);
        compoundTag.putInt(NBT_KEY_HEALTH, this.health);
        compoundTag.putFloat(NBT_KEY_MAX_HEALTH, this.maxHealth);

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
        this.experience = nbt.getFloat(NBT_KEY_EXPERIENCE);
        this.experienceToNextLevel = nbt.getFloat(NBT_KEY_EXPERIENCE_TO_NEXT_LEVEL);
        this.points = nbt.getInt(NBT_KEY_POINTS);
        this.strength = nbt.getInt(NBT_KEY_STRENGTH);
        this.intelligence = nbt.getInt(NBT_KEY_INTELLIGENCE);
        this.agility = nbt.getInt(NBT_KEY_AGILITY);
        this.vitality = nbt.getInt(NBT_KEY_VITALITY);
        this.damage = nbt.getInt(NBT_KEY_DAMAGE);
        this.defense = nbt.getInt(NBT_KEY_DEFENSE);
        this.speed = nbt.getFloat(NBT_KEY_SPEED);
        this.mana = nbt.getInt(NBT_KEY_MANA);
        this.maxMana = nbt.getInt(NBT_KEY_MAX_MANA);
        this.health = nbt.getInt(NBT_KEY_HEALTH);
        this.maxHealth = nbt.getFloat(NBT_KEY_MAX_HEALTH);
    }
}
