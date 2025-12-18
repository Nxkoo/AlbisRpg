package com.github.joaoalberis.albisrpg.capability;

import com.github.joaoalberis.albisrpg.Albisrpg;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber
public class PlayerCapabilityAttacher{

    public static class PlayerCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        public static final ResourceLocation IDENTIFIER = new ResourceLocation(Albisrpg.MODID, "playercapability");

        private final PlayerCapabilityInterface playerCapability = new PlayerCapabilityImplementation();
        private final LazyOptional<PlayerCapabilityInterface> data = LazyOptional.of(() -> playerCapability);

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return PlayerCapability.PLAYER_CAPABILITY.orEmpty(cap, this.data);
        }

        @Override
        public CompoundTag serializeNBT() {
            return this.playerCapability.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.playerCapability.deserializeNBT(nbt);
        }
    }

    @SubscribeEvent
    public static void attach(final AttachCapabilitiesEvent<Entity> event) {
        final PlayerCapabilityProvider provider = new PlayerCapabilityProvider();

        event.addCapability(PlayerCapabilityProvider.IDENTIFIER, provider);
    }
}
