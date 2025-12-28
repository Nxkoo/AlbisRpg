package com.github.joaoalberis.albisrpg.capability;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerCapability {

    public static final Capability<PlayerCapabilityInterface> PLAYER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(PlayerCapabilityInterface.class);
    }

    private PlayerCapability() {
    }

    @Mod.EventBusSubscriber
    public static class PlayerCapabilitySync {
        @SubscribeEvent
        public static void onClone(PlayerEvent.Clone event){
            Player original = event.getOriginal();
            original.revive();
            Player player = event.getEntity();

            PlayerCapabilityInterface playerData = player.getCapability(PLAYER_CAPABILITY).orElse(new PlayerCapabilityImplementation());
            PlayerCapabilityInterface originalData = original.getCapability(PLAYER_CAPABILITY).orElse(new PlayerCapabilityImplementation());

            playerData.setPlayerClass(originalData.getPlayerClass());
            playerData.setLevel(originalData.getLevel());
            playerData.setExperience(originalData.getExperience());
            playerData.setExperienceToNextLevel(originalData.getExperienceToNextLevel());
            playerData.setPoints(originalData.getPoints());
            playerData.setStrength(originalData.getStrength());
            playerData.setIntelligence(originalData.getIntelligence());
            playerData.setAgility(originalData.getAgility());
            playerData.setVitality(originalData.getVitality());
            playerData.setDamage(originalData.getDamage());
            playerData.setDefense(originalData.getDefense());
            playerData.setSpeed(originalData.getSpeed());
            playerData.setMana(originalData.getMana());
            playerData.setMaxMana(originalData.getMaxMana());
            playerData.setHealth(originalData.getHealth());
            playerData.setMaxHealth(originalData.getMaxHealth());
        }

        @SubscribeEvent
        public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
            if (!event.getEntity().level().isClientSide()) {
                for (Entity entityiterator : new ArrayList<>(event.getEntity().level().players())) {
                    ((PlayerCapabilityImplementation) entityiterator.getCapability(PLAYER_CAPABILITY, null).orElse(new PlayerCapabilityImplementation())).syncToClient(entityiterator);
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (!event.getEntity().level().isClientSide()) {
                for (Entity entityiterator : new ArrayList<>(event.getEntity().level().players())) {
                    ((PlayerCapabilityImplementation) entityiterator.getCapability(PLAYER_CAPABILITY, null).orElse(new PlayerCapabilityImplementation())).syncToClient(entityiterator);
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
            if (!event.getEntity().level().isClientSide()) {
                for (Entity entityiterator : new ArrayList<>(event.getEntity().level().players())) {
                    entityiterator.getCapability(PLAYER_CAPABILITY, null).ifPresent(c -> {
                        c.setHealth((int) c.getMaxHealth());
                        c.syncToClient(entityiterator);
                    });
                }
            }
        }
    }

}
