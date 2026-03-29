package mod.gottsch.neoforge.furnacering.core.item;

import mod.gottsch.neoforge.furnacering.core.FurnaceRing;
import mod.gottsch.neoforge.furnacering.core.component.FurnaceRingComponents;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

/**
 * @author by Mark Gottschling on 12/26/2025
 */
public class FurnaceRingItemProperties {
    public static void addCustomItemProperties() {
        // register for all three rings
        makeSmelting(FurnaceRingItems.FURNACE_RING.get());
        makeSmelting(FurnaceRingItems.BLAST_FURNACE_RING.get());
        makeSmelting(FurnaceRingItems.SMOKER_RING.get());
    }

    private static void makeSmelting(Item item) {
        ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(FurnaceRing.MOD_ID, "lit"),
                (stack, level, entity, seed) -> {
                    return stack.getOrDefault(FurnaceRingComponents.IS_SMELTING.get(), false) ? 1.0F : 0.0F;
                });
    }
}
