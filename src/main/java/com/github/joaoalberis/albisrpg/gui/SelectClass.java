package com.github.joaoalberis.albisrpg.gui;

import com.github.joaoalberis.albisrpg.Albisrpg;
import com.github.joaoalberis.albisrpg.capability.PlayerCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class SelectClass extends Screen {

    private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation("minecraft:textures/gui/demo_background.png");
    private static final Component TITTLE = Component.translatable("gui." + Albisrpg.MODID + ".selectclass.title");
    private static final List<String> CLASSES = List.of("Warrior", "Mage", "Rogue");

    private int leftPos, topPos;
    private int bgWidth, bgHeight;


    public SelectClass(Component p_96550_) {
        super(p_96550_);
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
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {
        Minecraft instance = Minecraft.getInstance();
        this.renderBackground(graphics);

        int y = (this.height - bgHeight) / 2 + 35;

        graphics.blit(BACKGROUND_LOCATION, leftPos, topPos, 0, 0, bgWidth, bgHeight);
        graphics.drawString(instance.font, TITTLE, (this.width  / 2 - TITTLE.getString().length() * 2) - 10, y, 0xffffff, true);

        y += 20;

        for (int i = 0; i < CLASSES.size(); i++){
            this.addRenderableWidget(Button.builder(
                    Component.literal(CLASSES.get(i)),
                    this::handlerButtonClass
            ).bounds(leftPos, y, 50, 20).build());
            y += 40;
        }

        super.render(graphics, mousex, mousey, partialTicks);
    }

    private void handlerButtonClass(Button button) {
        String className = button.getMessage().getString();
        LocalPlayer player = Minecraft.getInstance().player;
        player.getCapability(PlayerCapability.PLAYER_CAPABILITY).ifPresent(c -> {
            c.setPlayerClass(className);
            player.displayClientMessage(Component.literal("Your class was select -> " + className), true);
        });
        this.onClose();
    }
}
