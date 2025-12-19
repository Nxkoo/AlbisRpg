package com.github.joaoalberis.albisrpg.network;

import com.github.joaoalberis.albisrpg.capability.PlayerCapability;
import com.github.joaoalberis.albisrpg.capability.PlayerCapabilityImplementation;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerCapabilitySyncServer {

    private PlayerCapabilityImplementation playerCapability;

    public PlayerCapabilitySyncServer(PlayerCapabilityImplementation data) {
        this.playerCapability = data;
    }

    public static void encode(PlayerCapabilitySyncServer msg, FriendlyByteBuf buf) {
        buf.writeNbt(msg.playerCapability.serializeNBT());
    }

    public PlayerCapabilitySyncServer(FriendlyByteBuf buf) {
        this.playerCapability = new PlayerCapabilityImplementation();
        this.playerCapability.deserializeNBT(buf.readNbt());
    }

    public static void handle(PlayerCapabilitySyncServer msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            player.getCapability(PlayerCapability.PLAYER_CAPABILITY).ifPresent(variables -> {
                variables.deserializeNBT(msg.playerCapability.serializeNBT());
                variables.syncToClient(player);
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
