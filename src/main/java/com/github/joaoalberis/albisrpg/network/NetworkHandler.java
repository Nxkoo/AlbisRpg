package com.github.joaoalberis.albisrpg.network;

import com.github.joaoalberis.albisrpg.Albisrpg;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Albisrpg.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    static int id = 0;

    public static void registerMessages() {
        INSTANCE.registerMessage(id++,
                PlayerCapabilitySyncClient.class,
                PlayerCapabilitySyncClient::encode,
                PlayerCapabilitySyncClient::new,
                PlayerCapabilitySyncClient::handle);
        INSTANCE.registerMessage(id++,
                PlayerCapabilitySyncServer.class,
                PlayerCapabilitySyncServer::encode,
                PlayerCapabilitySyncServer::new,
                PlayerCapabilitySyncServer::handle);
    }

}
