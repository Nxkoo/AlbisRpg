package com.github.joaoalberis.albisrpg.mixin;

import com.github.joaoalberis.albisrpg.capability.PlayerCapability;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicReference;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Shadow public abstract <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing);

    @Inject(method = "getSpeed", at = @At("RETURN"), cancellable = true)
    public void updateSpeed(CallbackInfoReturnable<Float> cir){
        this.getCapability(PlayerCapability.PLAYER_CAPABILITY, null).ifPresent(c -> {
            float baseSpeed = cir.getReturnValue();
            float speedModifier = c.getSpeed();
            float newSpeed = baseSpeed + speedModifier / 1000;
            cir.setReturnValue(newSpeed);
        });
    }

    @Redirect(method = "attack",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D",
                    ordinal = 0)
            )
    private double updateAttack(Player instance, Attribute attribute){
        AtomicReference<Float> newValue = new AtomicReference<>((float) 0);
        this.getCapability(PlayerCapability.PLAYER_CAPABILITY, null).ifPresent(c -> {
            float baseValue = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float damageModifier = c.getDamage();
            newValue.set(baseValue + damageModifier);
        });
        return newValue.get();
    }

}
