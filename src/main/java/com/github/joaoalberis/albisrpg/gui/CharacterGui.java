package com.github.joaoalberis.albisrpg.gui;

import com.github.joaoalberis.albisrpg.Albisrpg;
import com.github.joaoalberis.albisrpg.capability.PlayerCapability;
import com.github.joaoalberis.albisrpg.capability.PlayerCapabilityImplementation;
import com.github.joaoalberis.albisrpg.capability.PlayerCapabilityInterface;
import com.github.joaoalberis.albisrpg.gui.button.AddButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CharacterGui extends Screen {

    private static final Component TITTLE = Component.translatable("gui.albisrpg.character.title");
    private static final Component LEVEL = Component.translatable("gui.albisrpg.character.level");
    private static final Component EXPERIENCE = Component.translatable("gui.albisrpg.character.experience");
    private static final Component CLASS = Component.translatable("gui.albisrpg.character.class");
    private static final Component ATTRIBUTES = Component.translatable("gui.albisrpg.character.ATTRIBUTES");
    private static final Component STRENGTH = Component.translatable("gui.albisrpg.character.attributes.strength");
    private static final Component AGILITY = Component.translatable("gui.albisrpg.character.attributes.agility");
    private static final Component INTELLIGENCE = Component.translatable("gui.albisrpg.character.attributes.intelligence");
    private static final Component VITALLY = Component.translatable("gui.albisrpg.character.attributes.vitality");
    private static final Component DAMAGE = Component.translatable("gui.albisrpg.character.stats.damage");
    private static final Component DEFENSE = Component.translatable("gui.albisrpg.character.stats.defense");
    private static final Component MANA = Component.translatable("gui.albisrpg.character.stats.mana");
    private static final Component HEALTH = Component.translatable("gui.albisrpg.character.stats.health");

    private static final ResourceLocation background = new ResourceLocation(Albisrpg.MODID, "textures/gui/screen/character.png");
    private int leftPos, topPos;
    private int bgWidth, bgHeight;
    private Minecraft minecraftInstance = null;
    private LocalPlayer player = null;

    public CharacterGui() {
        super(TITTLE);
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
        player.getCapability(PlayerCapability.PLAYER_CAPABILITY).ifPresent(c -> {
            int x = leftPos;
            int y = topPos;
            Font font = minecraftInstance.font;

            graphics.blitWithBorder(background, x, y, 0, 0, bgWidth, bgHeight, bgWidth, bgHeight, 10);
            x+=15;
            y+=20;
            graphics.drawString(font, LEVEL, x, y, 0x000000, false);
            graphics.drawString(font, String.valueOf(c.getLevel()), x + LEVEL.getString().length() * 6, y, 0x000000, false);
            y+=15;
            graphics.drawString(font, EXPERIENCE, x, y, 0x000000, false);
            graphics.drawString(font, String.valueOf(c.getExperience()), x + EXPERIENCE.getString().length() * 6, y, 0x000000, false);
            y+=15;
            graphics.drawString(font, CLASS, x, y, 0x000000, false);
            graphics.drawString(font, c.getPlayerClass(), x + CLASS.getString().length() * 6, y, 0x000000, false);
            y+=25;
            graphics.drawString(font, ATTRIBUTES, x, y, 0x000000, false);
            y+=15;
            x+=10;
            graphics.drawString(font, STRENGTH, x, y, 0x000000, false);
            graphics.drawString(font, String.valueOf(c.getStrength()), x + STRENGTH.getString().length() * 6, y, 0x000000, false);
            addButton(x - 12, y - 2, 10, 10, 10, 10, "strength");
            y+=15;
            graphics.drawString(font, AGILITY, x, y, 0x000000, false);
            graphics.drawString(font, String.valueOf(c.getAgility()), x + AGILITY.getString().length() * 6, y, 0x000000, false);
            addButton(x - 12, y - 2, 10, 10, 10, 10, "agility");
            y+=15;
            graphics.drawString(font, INTELLIGENCE, x, y, 0x000000, false);
            graphics.drawString(font, String.valueOf(c.getIntelligence()), x + INTELLIGENCE.getString().length() * 6, y, 0x000000, false);
            addButton(x - 12, y - 2, 10, 10, 10, 10, "intelligence");
            y+=15;
            graphics.drawString(font, VITALLY, x, y, 0x000000, false);
            graphics.drawString(font, String.valueOf(c.getVitality()), x + VITALLY.getString().length() * 6, y, 0x000000, false);
            addButton(x - 12, y - 2, 10, 10, 10, 10, "vitally");

            x=this.bgWidth;
            y=topPos * 2;
            graphics.drawString(font, DAMAGE, x, y, 0x000000, false);
            y+=10;
            graphics.drawString(font, DEFENSE, x, y, 0x000000, false);
            y+=10;
            graphics.drawString(font, MANA, x, y, 0x000000, false);
            y+=10;
            graphics.drawString(font, HEALTH, x, y, 0x000000, false);
        });

        super.render(graphics, mousex, mousey, partialTicks);
    }

    private void addButton(int x, int y, int width, int height, int textureWidth, int textureHeight, String name) {
        AddButton addButton = new AddButton(x, y, width, height, 0, 0, 0, textureWidth, textureHeight,this::handlerButtonClass, Component.literal(name));
        this.addRenderableWidget(addButton);
    }

    private void handlerButtonClass(Button button) {
        Component message = button.getMessage();
        player.getCapability(PlayerCapability.PLAYER_CAPABILITY).ifPresent(c -> {
            switch (message.getString()){
                case "strength" -> c.setStrength(c.getStrength() + 1);
                case "agility" -> c.setAgility(c.getAgility() + 1);
                case "intelligence" -> c.setIntelligence(c.getIntelligence() + 1);
                case "vitally" -> c.setVitality(c.getVitality() + 1);
            }
            c.syncToServer(player);
        });
    }
}
