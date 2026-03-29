package mod.gottsch.neoforge.furnacering.core.event;

import mod.gottsch.neoforge.furnacering.core.FurnaceRing;
import mod.gottsch.neoforge.furnacering.core.item.AbstractFurnaceRingItem;
import mod.gottsch.neoforge.furnacering.core.network.OpenCurioRingPacket;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * @author by Mark Gottschling on 12/23/2025
 */
@EventBusSubscriber(modid = FurnaceRing.MOD_ID, value = Dist.CLIENT)
public class ScreenEvents {

    @SubscribeEvent
    public static void onScreenClick(ScreenEvent.MouseButtonPressed.Pre event) {
        if (event.getScreen() instanceof AbstractContainerScreen<?> screen) {
            if (event.getButton() == 1) { // right Click
                Slot slot = screen.getSlotUnderMouse();

                if (slot != null && slot.getItem().getItem() instanceof AbstractFurnaceRingItem) {
                    // Curios slots are added to the screen dynamically.
                    // we need to determine their index relative to the Curios inventory.

                    int localIndex = -1;

                    // check if it's a Curios Slot via class name
                    if (slot.getClass().getName().contains("curios")) {
                        // in Curios 1.21.1, the slot index passed to the constructor
                        // is usually the local index.
                        localIndex = slot.getContainerSlot();
                    }

                    if (localIndex != -1) {
                        PacketDistributor.sendToServer(new OpenCurioRingPacket(localIndex));
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
