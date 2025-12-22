package com.github.joaoalberis.albisrpg.events;

import com.github.joaoalberis.albisrpg.capability.PlayerCapability;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class KillMobEvent {

    @SubscribeEvent
    public static void onMobKilled(LivingDeathEvent event){
        if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide){
            LivingEntity mob = event.getEntity();
            if (!mob.getType().getDescriptionId().contains("albisrpg")){
                player.getCapability(PlayerCapability.PLAYER_CAPABILITY).ifPresent(c -> {
                    c.addExperience(0.5f);
                    c.syncToClient(player);
                });
            }
        }
    }
}
