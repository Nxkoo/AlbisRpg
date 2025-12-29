package com.github.joaoalberis.albisrpg.gui;

import com.github.joaoalberis.albisrpg.Albisrpg;
import com.github.joaoalberis.albisrpg.capability.PlayerCapability;
import com.github.joaoalberis.albisrpg.classes.PlayerClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Optional;

public class SelectClass extends Screen {

    private static final ResourceLocation BACKGROUND_LOCATION = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/demo_background.png");
    private static final Component TITTLE = Component.translatable("gui." + Albisrpg.MODID + ".selectclass.title");

    private int leftPos, topPos;
    private int bgWidth, bgHeight;

    public SelectClass(Component component) {
        super(component);
    }

    @Override
    protected void init() {
        super.init();
        this.bgHeight = 256;
        this.bgWidth = 256;

        this.leftPos = (this.width - bgWidth) / 2;
        this.topPos = (this.height - bgHeight) / 2 + 31;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft instance = Minecraft.getInstance();
        this.renderBackground(graphics);

        int y = (this.height - bgHeight) / 2 + 35;

        graphics.blit(BACKGROUND_LOCATION, leftPos, topPos, 0, 0, bgWidth, bgHeight);
        graphics.drawString(instance.font, TITTLE, (this.width  / 2 - TITTLE.getString().length() * 2) - 10, y, 0xffffff, true);

        y += 20;

        for ( PlayerClass c : PlayerClass.values() ) {
            this.addRenderableWidget(Button.builder(
                    Component.literal(c.getName()),
                    button -> this.handleButtonClass(button, c.ordinal())
            ).bounds(leftPos + 20, y, 50, 20).build());
            y += 40;
        }

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    private void handleButtonClass( Button button, int classIndex ) {
        String className = button.getMessage().getString();
        LocalPlayer player = Minecraft.getInstance().player;
        if ( player == null ) return;
        player.getCapability(PlayerCapability.PLAYER_CAPABILITY).ifPresent(c -> {
            PlayerClass playerClass = PlayerClass.values()[classIndex];
            c.setUpCharacter(playerClass);
            player.displayClientMessage(Component.literal("Your class was select -> " + className), true);
            c.syncToServer(player);
        });
        this.onClose();
    }
}
