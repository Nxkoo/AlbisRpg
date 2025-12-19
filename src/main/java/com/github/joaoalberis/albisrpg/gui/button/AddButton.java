package com.github.joaoalberis.albisrpg.gui.button;

import com.github.joaoalberis.albisrpg.Albisrpg;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AddButton extends ImageButton {

    private static ResourceLocation button = new ResourceLocation(Albisrpg.MODID, "textures/gui/button/add_button.png");
    private static ResourceLocation button_hover = new ResourceLocation(Albisrpg.MODID, "textures/gui/button/add_button_hover.png");


    public AddButton(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, int textureWidth, int textureHeight, OnPress onPress, Component component) {
        super(x, y, width, height, xTexStart, yTexStart, yDiffTex, button, textureWidth, textureHeight, onPress, component);
    }

    @Override
    public void renderTexture(GuiGraphics p_283546_, ResourceLocation p_281674_, int p_281808_, int p_282444_, int p_283651_, int p_281601_, int p_283472_, int p_282390_, int p_281441_, int p_281711_, int p_281541_) {
        if (this.isHovered){
            super.renderTexture(p_283546_, button_hover, p_281808_, p_282444_, p_283651_, p_281601_, p_283472_, p_282390_, p_281441_, p_281711_, p_281541_);
            return;
        }
        super.renderTexture(p_283546_, button, p_281808_, p_282444_, p_283651_, p_281601_, p_283472_, p_282390_, p_281441_, p_281711_, p_281541_);
    }
}
