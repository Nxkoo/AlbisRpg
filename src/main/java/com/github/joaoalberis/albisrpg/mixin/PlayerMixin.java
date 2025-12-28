package com.github.joaoalberis.albisrpg.mixin;

import com.github.joaoalberis.albisrpg.capability.PlayerCapability;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    @Unique
    Player player = (Player) (Object) this;

    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Shadow public abstract <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing);

    @Inject(method = "getSpeed", at = @At("RETURN"), cancellable = true)
    public void updateSpeed(CallbackInfoReturnable<Float> cir){
        player.getCapability(PlayerCapability.PLAYER_CAPABILITY, null).ifPresent(c -> {
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
        player.getCapability(PlayerCapability.PLAYER_CAPABILITY, null).ifPresent(c -> {
            float baseValue = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float damageModifier = c.getDamage();
            newValue.set(baseValue + damageModifier);
        });
        return newValue.get();
    }

    @Inject(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setHealth(F)V"), cancellable = true)
    public void updateDefaultDamage(DamageSource pDamageSrc, float pDamageAmount, CallbackInfo ci){
        player.getCapability(PlayerCapability.PLAYER_CAPABILITY, null).ifPresent(c -> {
            if (c.getHealth() > 0){
                System.out.println(pDamageAmount);
                c.setHealth(c.getHealth() - (int) pDamageAmount);
                player.causeFoodExhaustion(pDamageSrc.getFoodExhaustion());
                c.syncToClient(player);
                ci.cancel();
            }
        });
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    public void regenHealth(CallbackInfo ci){
        player.getCapability(PlayerCapability.PLAYER_CAPABILITY, null).ifPresent(c -> {
            if (player.tickCount % 10 == 0){
                if (c.getHealth() <= 0){
                    player.hurt(player.damageSources().magic(), 2f);
                }
            }
            if (player.tickCount % 80 == 0 && c.getHealth() > 0){
                if (c.getHealth() < c.getMaxHealth()){
                    c.setHealth(c.getHealth() + 1);
                    c.syncToClient(player);
                }
            }
        });
    }

    @Inject(method = "eat", at = @At("HEAD"))
    public void regenHealthByFood(Level pLevel, ItemStack pFood, CallbackInfoReturnable<ItemStack> cir){
        player.getCapability(PlayerCapability.PLAYER_CAPABILITY, null).ifPresent(c -> {
            int foodValue = Objects.requireNonNull(pFood.getItem().getFoodProperties()).getNutrition();
            int regen = foodValue * 2;
            int health = c.getHealth();
            if (regen + health > c.getMaxHealth()){
                c.setHealth((int) c.getMaxHealth());
            }else {
                c.setHealth(health + regen);
            }
            c.syncToClient(player);
        });
    }

    @Inject(method = "canEat", at = @At("RETURN"), cancellable = true)
    public void modifyCanEat(boolean pCanAlwaysEat, CallbackInfoReturnable<Boolean> cir){
        player.getCapability(PlayerCapability.PLAYER_CAPABILITY, null).ifPresent(c -> {
            if (c.getHealth() < c.getMaxHealth()){
                cir.setReturnValue(true);
            }else {
                cir.setReturnValue(false);
            }
        });
    }

}
