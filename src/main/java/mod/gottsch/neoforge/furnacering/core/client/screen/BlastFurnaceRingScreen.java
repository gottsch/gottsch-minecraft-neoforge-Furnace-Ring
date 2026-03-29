package mod.gottsch.neoforge.furnacering.core.client.screen;

import mod.gottsch.neoforge.furnacering.core.inventory.BlastFurnaceRingMenu;
import mod.gottsch.neoforge.furnacering.core.inventory.FurnaceRingMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/**
 *
 * @author Mark Gottschling on Dec 18, 2025
 *
 */
public class BlastFurnaceRingScreen extends BaseRingScreen<BlastFurnaceRingMenu> {

	public BlastFurnaceRingScreen(BlastFurnaceRingMenu menu, Inventory inv, Component title) {
		super(menu, inv, title);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
		super.render(guiGraphics, mouseX, mouseY, delta);
	}

	@Override
	public int getUOffset() {
		return 39;
	}

	@Override
	public int getUWidth() {
		return 42;
	}

	@Override
	public int getVHeight() {
		return 42;
	}
}
