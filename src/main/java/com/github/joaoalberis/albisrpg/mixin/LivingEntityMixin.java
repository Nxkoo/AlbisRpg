package com.github.joaoalberis.albisrpg.mixin;

import com.github.joaoalberis.albisrpg.capability.PlayerCapability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "getMaxHealth", at = @At("RETURN"), cancellable = true)
    public void updateHealth(CallbackInfoReturnable<Float> cir){
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof Player player){
            System.out.println("passou");
            player.getCapability(PlayerCapability.PLAYER_CAPABILITY).ifPresent(c -> {
                Float baseValue = cir.getReturnValue();
                int newValue = c.getHealth();
                System.out.println(baseValue);
                System.out.println(newValue);
                cir.setReturnValue(newValue + baseValue);
            });
        }
    }

}
