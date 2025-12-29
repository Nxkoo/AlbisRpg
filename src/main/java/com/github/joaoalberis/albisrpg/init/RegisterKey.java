package com.github.joaoalberis.albisrpg.init;

import com.github.joaoalberis.albisrpg.capability.PlayerCapability;
import com.github.joaoalberis.albisrpg.capability.PlayerCapabilityImplementation;
import com.github.joaoalberis.albisrpg.capability.PlayerCapabilityInterface;
import com.github.joaoalberis.albisrpg.gui.CharacterGui;
import com.github.joaoalberis.albisrpg.gui.SelectClass;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterKey {

    public static final KeyMapping SELECTCLASS = new KeyMapping("key.albisrpg.selectclass", GLFW.GLFW_KEY_N, "key.categories.misc") {
        @Override
        public void setDown(boolean p_90846_) {
            if (this.isDown() != p_90846_ && p_90846_) {
                Minecraft instance = Minecraft.getInstance();
                if (instance.player != null){
                    LocalPlayer player = instance.player;
                    PlayerCapabilityInterface playerCapability = player.getCapability(PlayerCapability.PLAYER_CAPABILITY).orElse(new PlayerCapabilityImplementation());
                    if (playerCapability.getPlayerClass() == null) {
                        instance.setScreen(new SelectClass(Component.literal("teste")));
                    }else {
                        player.displayClientMessage(Component.literal("You already have a class: " + playerCapability.getPlayerClass().getName()), true);
                    }
                }
            }
            super.setDown(p_90846_);
        }
    };

    public static final KeyMapping OPEN_CHARACTER_GUI = new KeyMapping("key.albisrpg.character", GLFW.GLFW_KEY_V, "key.categories.albisrpg") {
        @Override
        public void setDown(boolean p_90846_) {
            if (this.isDown() != p_90846_ && p_90846_) {
                Minecraft instance = Minecraft.getInstance();
                if (instance.player != null){
                    LocalPlayer player = instance.player;
                    PlayerCapabilityInterface playerCapability = player.getCapability(PlayerCapability.PLAYER_CAPABILITY).orElse(new PlayerCapabilityImplementation());
                    if (playerCapability.getPlayerClass() != null) {
                        instance.setScreen(new CharacterGui());
                    }else {
                        player.displayClientMessage(Component.literal("You need to select your class"), true);
                    }
                }
            }
            super.setDown(p_90846_);
        }
    };

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(SELECTCLASS);
        event.register(OPEN_CHARACTER_GUI);
    }


    @Mod.EventBusSubscriber
    public static class KeyEvent {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (Minecraft.getInstance().screen == null) {
                SELECTCLASS.consumeClick();
                OPEN_CHARACTER_GUI.consumeClick();
            }
        }
    }

}
