package com.github.joaoalberis.albisrpg.network;

import com.github.joaoalberis.albisrpg.classes.PlayerClass;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class C2SUpdateTranslation {
    private final String currentLanguage;
    private final List<String> classes;

    public C2SUpdateTranslation( String currentLanguage, List<String> classes ) {
        this.currentLanguage = currentLanguage;
        this.classes = classes;

    }

    public static void encode( C2SUpdateTranslation msg, FriendlyByteBuf buf ) {
        buf.writeUtf(msg.currentLanguage);
        buf.writeInt(msg.classes.size());
        for ( String className : msg.classes ) {
            buf.writeUtf(className);
        }
    }

    public C2SUpdateTranslation( FriendlyByteBuf buf ) {
        this.currentLanguage = buf.readUtf();
        int size = buf.readInt();
        this.classes = new ArrayList<>();

        for ( int i = 0; i < size; i++ ) {
            this.classes.add(buf.readUtf());
        }
    }

    public static void handle( C2SUpdateTranslation msg, Supplier<NetworkEvent.Context> ctx ) {
        ctx.get().enqueueWork(() -> {
            var player = ctx.get().getSender();

            if (player == null) return;
            if ( player.getLanguage().equalsIgnoreCase(msg.currentLanguage) ) {
                for ( int i = 0; i < Math.min(msg.classes.size(), PlayerClass.values().length); i++ ) {
                    PlayerClass.values()[i].setName(msg.classes.get(i));
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
