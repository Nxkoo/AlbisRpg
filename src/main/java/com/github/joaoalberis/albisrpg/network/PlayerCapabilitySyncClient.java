package com.github.joaoalberis.albisrpg.network;

import com.github.joaoalberis.albisrpg.capability.PlayerCapability;
import com.github.joaoalberis.albisrpg.capability.PlayerCapabilityImplementation;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerCapabilitySyncClient {

    private int target;
    private PlayerCapabilityImplementation playerCapability;

    public PlayerCapabilitySyncClient(PlayerCapabilityImplementation data, int entityid) {
        this.playerCapability = data;
        this.target = entityid;
    }

    public static void encode(PlayerCapabilitySyncClient msg, FriendlyByteBuf buf) {
        buf.writeNbt(msg.playerCapability.serializeNBT());
        buf.writeInt(msg.target);
    }

    public PlayerCapabilitySyncClient(FriendlyByteBuf buf) {
        this.playerCapability = new PlayerCapabilityImplementation();
        this.playerCapability.deserializeNBT(buf.readNbt());
        this.target = buf.readInt();
    }

    public static void handle(PlayerCapabilitySyncClient msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (!ctx.get().getDirection().getReceptionSide().isClient()) return;
            Minecraft.getInstance().player.level().getEntity(msg.target).getCapability(PlayerCapability.PLAYER_CAPABILITY, null).ifPresent(variables -> {
                variables.setPlayerClass(msg.playerCapability.getPlayerClass());
                variables.setLevel(msg.playerCapability.getLevel());
                variables.setExperience(msg.playerCapability.getExperience());
                variables.setStrength(msg.playerCapability.getStrength());
                variables.setAgility(msg.playerCapability.getAgility());
                variables.setIntelligence(msg.playerCapability.getIntelligence());
                variables.setVitality(msg.playerCapability.getVitality());
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
