package com.github.joaoalberis.albisrpg.gui;

import com.github.joaoalberis.albisrpg.Albisrpg;
import com.github.joaoalberis.albisrpg.capability.PlayerCapability;
import com.github.joaoalberis.albisrpg.capability.PlayerCapabilityImplementation;
import com.github.joaoalberis.albisrpg.capability.PlayerCapabilityInterface;
import com.github.joaoalberis.albisrpg.gui.button.AddButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CharacterGui extends Screen {

    private static final Component TITTLE = Component.translatable("gui.albisrpg.character.stats.title");
    private static final Component LEVEL = Component.translatable("gui.albisrpg.character.stats.level");
    private static final Component EXPERIENCE = Component.translatable("gui.albisrpg.character.stats.experience");
    private static final Component ATTRIBUTES = Component.translatable("gui.albisrpg.character.stats.ATTRIBUTES");
    private static final Component STRENGTH = Component.translatable("gui.albisrpg.character.stats.strength");
    private static final Component AGILITY = Component.translatable("gui.albisrpg.character.stats.agility");
    private static final Component INTELLIGENCE = Component.translatable("gui.albisrpg.character.stats.intelligence");
    private static final Component VITALLY = Component.translatable("gui.albisrpg.character.stats.vitality");
    private static final ResourceLocation background = new ResourceLocation(Albisrpg.MODID, "textures/gui/screen/character.png");
    private int leftPos, topPos;
    private int bgWidth, bgHeight;
    private Minecraft minecraftInstance = null;
    private LocalPlayer player = null;

    public CharacterGui(Component component) {
        super(component);
    }

    @Override
    protected void init() {
        super.init();
        this.bgHeight = 256;
        this.bgWidth = 256;
        this.leftPos = (this.width - bgWidth) / 2;
        this.topPos = (this.height - bgHeight) / 2 + 31;
        this.minecraftInstance = Minecraft.getInstance();
        if (minecraftInstance.player != null){
            this.player = minecraftInstance.player;
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {
        this.renderBackground(graphics);
        PlayerCapabilityInterface playerData = player.getCapability(PlayerCapability.PLAYER_CAPABILITY).orElse(new PlayerCapabilityImplementation());

        int x = leftPos;
        int y = topPos;
        graphics.blitWithBorder(background, x, y, 0, 0, bgWidth, bgHeight, bgWidth, bgHeight, 10);
        x+=15;
        y+=20;
        graphics.drawString(minecraftInstance.font, LEVEL, x, y, 0x000000, false);
        y+=15;
        graphics.drawString(minecraftInstance.font, EXPERIENCE, x, y, 0x000000, false);
        y+=35;
        graphics.drawString(minecraftInstance.font, ATTRIBUTES, x, y, 0x000000, false);
        y+=15;
        x+=10;
        graphics.drawString(minecraftInstance.font, STRENGTH, x, y, 0x000000, false);
        addButton(x - 12, y - 2, 10, 10, 10, 10, "+");
        y+=15;
        graphics.drawString(minecraftInstance.font, AGILITY, x, y, 0x000000, false);
        addButton(x - 12, y - 2, 10, 10, 10, 10, "+");
        y+=15;
        graphics.drawString(minecraftInstance.font, INTELLIGENCE, x, y, 0x000000, false);
        addButton(x - 12, y - 2, 10, 10, 10, 10, "+");
        y+=15;
        addButton(x - 12, y - 2, 10, 10, 10, 10, "+");
        graphics.drawString(minecraftInstance.font, VITALLY, x, y, 0x000000, false);

        x=this.bgWidth;
        y=topPos * 2;
        graphics.drawString(minecraftInstance.font, "Dano", x, y, 0x000000, false);
        y+=10;
        graphics.drawString(minecraftInstance.font, "Defesa", x, y, 0x000000, false);
        y+=10;
        graphics.drawString(minecraftInstance.font, "Mana", x, y, 0x000000, false);
        y+=10;
        graphics.drawString(minecraftInstance.font, "Vida", x, y, 0x000000, false);


        super.render(graphics, mousex, mousey, partialTicks);
    }

    private void addButton(int x, int y, int width, int height, int textureWidth, int textureHeight, String name) {
        AddButton addButton = new AddButton(x, y, width, height, 0, 0, 0, textureWidth, textureHeight,this::handlerButtonClass, Component.literal(name));
        this.addRenderableWidget(addButton);
    }

    private void handlerButtonClass(Button button) {
        System.out.println("teste");
    }
}
