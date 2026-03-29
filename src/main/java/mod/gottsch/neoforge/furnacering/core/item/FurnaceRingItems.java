package mod.gottsch.neoforge.furnacering.core.item;

import mod.gottsch.neoforge.furnacering.core.FurnaceRing;
import mod.gottsch.neoforge.furnacering.core.component.ComponentHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @author by Mark Gottschling on 12/17/2025
 */
public class FurnaceRingItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FurnaceRing.MOD_ID);

    // rings
    public static final DeferredItem<Item> FURNACE_RING = ITEMS.register("furnace_ring",
            () -> new FurnaceRingItem(new Item.Properties().component(DataComponents.CONTAINER, createDefaultContents(3))));
    public static final DeferredItem<Item> BLAST_FURNACE_RING = ITEMS.register("blast_furnace_ring",
            () -> new BlastFurnaceRingItem(new Item.Properties().component(DataComponents.CONTAINER, createDefaultContents(3))));
    public static final DeferredItem<Item> SMOKER_RING = ITEMS.register("smoker_ring",
            () -> new SmokerRingItem(new Item.Properties().component(DataComponents.CONTAINER, createDefaultContents(3))));

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

    // create the default ItemContainerContents
    public static ItemContainerContents createDefaultContents(int slots) {
        // create a NonNullList with the specified size, filled with empty ItemStacks
        NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);
        return ItemContainerContents.fromItems(items);
    }
}
