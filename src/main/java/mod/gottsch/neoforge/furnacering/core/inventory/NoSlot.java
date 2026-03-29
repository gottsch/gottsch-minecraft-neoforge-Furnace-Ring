package mod.gottsch.neoforge.furnacering.core.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * @author by Mark Gottschling on 12/19/2025
 */
public class NoSlot extends Slot {

    public NoSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    /**
     * Disable slot from movement.
     */
    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }


    @Override
    public boolean mayPickup(Player player) {
        return false;
    }
}
