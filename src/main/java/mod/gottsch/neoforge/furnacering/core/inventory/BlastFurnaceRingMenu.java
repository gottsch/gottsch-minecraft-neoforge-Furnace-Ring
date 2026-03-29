package mod.gottsch.neoforge.furnacering.core.inventory;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

/**
 * @author by Mark Gottschling on 1/4/2026
 */
public class BlastFurnaceRingMenu extends BaseFurnaceRingMenu {

    // client constructor
    public BlastFurnaceRingMenu(int containerId, Inventory playerInv, RegistryFriendlyByteBuf buffer) {
        this(containerId, playerInv, decodeStack(buffer));
    }

    // server constructor
    public BlastFurnaceRingMenu(int containerId, Inventory playerInv, ItemStack stack) {
        super(FurnaceRingContainers.BLAST_FURNACE_RING_MENU.get(), RecipeType.SMELTING, RecipeBookType.FURNACE,
                containerId, playerInv, stack);

    }
}
