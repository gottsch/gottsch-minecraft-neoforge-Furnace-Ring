package mod.gottsch.neoforge.furnacering.core.client.screen;

import mod.gottsch.neoforge.furnacering.core.inventory.FurnaceRingMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/**
 *
 * @author Mark Gottschling on Dec 18, 2025
 *
 */
public class FurnaceRingScreen extends BaseRingScreen<FurnaceRingMenu> {


	public FurnaceRingScreen(FurnaceRingMenu menu, Inventory inv, Component title) {
		super(menu, inv, title);
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

//	@Override
//	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
//		int x = (this.width - this.imageWidth) / 2;
//		int y = (this.height - this.imageHeight) / 2;
//
//		// 1. background
//		guiGraphics.blit(getTexture(), x, y, 0, 0, this.imageWidth, this.imageHeight);
//
//		// 2. ring icon
//		guiGraphics.blit(getTexture(), x + 9, y + 23, getUOffset(), getVOffset(), 48, 48);
//
//		// 3. flame
//		if (this.menu.isLit()) {
//			int lit = (int) this.menu.getLitProgress();
//			// vanilla flame height is 13.
//			// x+56, y+36 is the furnace flame position
//			guiGraphics.blit(getTexture(), x + 56, y + 36 + 12 - lit, 176, 12 - lit, 14, lit + 1);
//		}
//
//		// 4. arrow
//		int cook = (int) this.menu.getBurnProgress();
//		// x+79, y+34 is the furnace arrow position
//		guiGraphics.blit(getTexture(), x + 79, y + 34, 176, 14, cook + 1, 16);
//
//	}

//	@Override
//	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
//		super.render(guiGraphics, mouseX, mouseY, delta);
//	}

}
