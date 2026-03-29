package mod.gottsch.neoforge.furnacering.core.event;

import mod.gottsch.neoforge.furnacering.core.FurnaceRing;
import mod.gottsch.neoforge.furnacering.core.component.ComponentHelper;
import mod.gottsch.neoforge.furnacering.core.item.AbstractFurnaceRingItem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ContainerScreenEvent;

/**
 * @author by Mark Gottschling on 12/23/2025
 */
@EventBusSubscriber(modid = FurnaceRing.MOD_ID, value = Dist.CLIENT)
public class ClientOverlayEvents {

    @SubscribeEvent
    public static void renderCurioOverlay(ContainerScreenEvent.Render.Foreground event) {
        AbstractContainerScreen<?> screen = event.getContainerScreen();

        for (Slot slot : screen.getMenu().slots) {
            // check if it's our ring and it's currently burning
            if (slot.getItem().getItem() instanceof AbstractFurnaceRingItem) {
                ItemStack stack = slot.getItem();
                if (ComponentHelper.smelting(stack)) {
                    // draw a small orange rectangle or flame icon at the slot position
                    // slot coordinates are relative to the screen top-left
                    int x = slot.x;
                    int y = slot.y;

                    // render a simple colored highlight
                    event.getGuiGraphics().fill(x, y, x + 16, y + 16, 0x55FFA500); // semi-transparent orange
                }
            }
        }
    }
}
