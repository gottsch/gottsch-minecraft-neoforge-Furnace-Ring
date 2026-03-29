package mod.gottsch.neoforge.furnacering.core;

import mod.gottsch.neoforge.furnacering.core.component.FurnaceRingComponents;
import mod.gottsch.neoforge.furnacering.core.integration.CuriosIntegration;
import mod.gottsch.neoforge.furnacering.core.inventory.FurnaceRingContainers;
import mod.gottsch.neoforge.furnacering.core.item.FurnaceRingItemProperties;
import mod.gottsch.neoforge.furnacering.core.item.FurnaceRingItems;
import mod.gottsch.neoforge.furnacering.core.loot.modifier.FurnaceRingLootModifiers;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.theillusivec4.curios.api.CuriosCapability;

/**
 * @author by Mark Gottschling on 12/17/2025
 */
@Mod(FurnaceRing.MOD_ID)
public class FurnaceRing {
    public static final String MOD_ID = "furnacering";
    public static final Logger LOGGER =LoggerFactory.getLogger(FurnaceRing.MOD_ID);

    public FurnaceRing(IEventBus modEventBus, ModContainer modContainer) {
        // register
        FurnaceRingComponents.register(modEventBus);
        FurnaceRingItems.register(modEventBus);
        FurnaceRingContainers.register(modEventBus);
        FurnaceRingLootModifiers.register(modEventBus);

        // TODO register loot modifiers
        // TODO register integrations
        // register the items to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::setup);
    }

    private void setup(FMLClientSetupEvent event) {
            // enqueueWork ensures the code runs on the main render thread
        // Call your registration method here
        event.enqueueWork(FurnaceRingItemProperties::addCustomItemProperties);

        if (CuriosIntegration.isLoaded()) {
            // We call this in a separate method or class to prevent
            // the ClassLoader from seeing Curios classes prematurely.
            CuriosIntegration.init();
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(FurnaceRingItems.FURNACE_RING.get());
            event.accept(FurnaceRingItems.BLAST_FURNACE_RING.get());
            event.accept(FurnaceRingItems.SMOKER_RING.get());
        }
    }

    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        // Check if the Curios mod is actually installed
        if (ModList.get().isLoaded("curios")) {
            event.registerItem(
                    CuriosCapability.ITEM, // the Curios Capability
                    (stack, context) -> CuriosIntegration.getCurioProvider(stack),
                    FurnaceRingItems.FURNACE_RING.get()
            );
            event.registerItem(
                    CuriosCapability.ITEM,
                    (stack, context) -> CuriosIntegration.getCurioProvider(stack),
                    FurnaceRingItems.BLAST_FURNACE_RING.get()
            );
            event.registerItem(
                    CuriosCapability.ITEM,
                    (stack, context) -> CuriosIntegration.getCurioProvider(stack),
                    FurnaceRingItems.SMOKER_RING.get()
            );
        }
    }
}
