package mod.gottsch.neoforge.furnacering.core.client.screen;

import mod.gottsch.neoforge.furnacering.core.FurnaceRing;
import mod.gottsch.neoforge.furnacering.core.inventory.AbstractFurnaceRingMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * @author by Mark Gottschling on 1/4/2026
 */
public abstract class BaseRingScreen<T extends AbstractFurnaceRingMenu> extends AbstractContainerScreen<T> {
    // furnace rings texture
    private final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(FurnaceRing.MOD_ID, "textures/gui/screen/furnace_ring.png");

    public BaseRingScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        // 1. background
        guiGraphics.blit(getTexture(), x, y, 0, 0, this.imageWidth, this.imageHeight);

        // 2. ring icon
        guiGraphics.blit(getTexture(), x + 9, y + 23, getUOffset(), getVOffset(), getUWidth(), getVHeight());

        // 3. flame
        if (this.menu.isLit()) {
            int lit = (int) this.menu.getLitProgress();
            // vanilla flame height is 13.
            // x+56, y+36 is the furnace flame position
            guiGraphics.blit(getTexture(), x + 56, y + 36 + 12 - lit, 176, 12 - lit, 14, lit + 1);
        }

        // 4. arrow
        int cook = (int) this.menu.getBurnProgress();
        // x+79, y+34 is the furnace arrow position
        guiGraphics.blit(getTexture(), x + 79, y + 34, 176, 14, cook + 1, 16);

    }
        @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    public int getUOffset() {
        return 0;
    }

    public int getVOffset() {
        return 166;
    }

    public int getUWidth() {
        return 39;
    }

    public int getVHeight() {
        return 40;
    }
}
