package mod.gottsch.neoforge.furnacering.core.client.screen;

import mod.gottsch.neoforge.furnacering.core.inventory.SmokerRingMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/**
 *
 * @author Mark Gottschling on Jan 4, 2026
 *
 */
public class SmokerRingScreen extends BaseRingScreen<SmokerRingMenu> {

	public SmokerRingScreen(SmokerRingMenu menu, Inventory inv, Component title) {
		super(menu, inv, title);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
		super.render(guiGraphics, mouseX, mouseY, delta);
	}

	@Override
	public int getUOffset() {
		return 81;
	}

	@Override
	public int getUWidth() {
		return 39;
	}

	@Override
	public int getVHeight() {
		return 39;
	}
}
