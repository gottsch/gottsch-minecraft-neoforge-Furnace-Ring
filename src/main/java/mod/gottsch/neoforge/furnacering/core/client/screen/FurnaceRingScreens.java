package mod.gottsch.neoforge.furnacering.core.client.screen;

import mod.gottsch.neoforge.furnacering.core.FurnaceRing;
import mod.gottsch.neoforge.furnacering.core.inventory.FurnaceRingContainers;
import mod.gottsch.neoforge.furnacering.core.inventory.FurnaceRingMenu;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

/**
 * @author by Mark Gottschling on 12/18/2025
 */
@EventBusSubscriber(modid = FurnaceRing.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FurnaceRingScreens {

    @SubscribeEvent
    public static void onRegisterScreens(RegisterMenuScreensEvent event) {

        event.register(
                FurnaceRingContainers.FURNACE_RING_MENU.get(),
                FurnaceRingScreen::new
        );

        event.register(
                FurnaceRingContainers.BLAST_FURNACE_RING_MENU.get(),
                BlastFurnaceRingScreen::new
        );

        event.register(
                FurnaceRingContainers.SMOKER_RING_MENU.get(),
                SmokerRingScreen::new
        );
    }
}
