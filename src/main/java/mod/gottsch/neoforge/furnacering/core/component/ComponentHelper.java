package mod.gottsch.neoforge.furnacering.core.component;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.Optional;

/**
 * @author by Mark Gottschling on 12/17/2025
 */
public class ComponentHelper {
    /*
     * component accessors
     */

    public static FurnaceState stateOrDefault(ItemStack stack) {
       return  stack.getOrDefault(FurnaceRingComponents.FURNACE_STATE.get(), FurnaceState.EMPTY);
    }

    public static boolean smelting(ItemStack stack) {
        return stack.getOrDefault(FurnaceRingComponents.IS_SMELTING.get(), false);
    }

    // NOTE this is for a custom inventory, which is moot as vanilla provides DataComponents.CONTAINER
    // inventory
    @Deprecated
    public static Optional<ItemContainerContents> inventory(ItemStack stack) {
        return Optional.ofNullable(stack.get(FurnaceRingComponents.ITEM_INVENTORY));
    }

    public static Optional<ItemContainerContents> container(ItemStack stack) {
        return Optional.ofNullable(stack.get(DataComponents.CONTAINER));
    }

    public static ItemContainerContents containerOrDefault(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
    }

    public static NonNullList<ItemStack> containerAsNonNullList(ItemStack stack, int size) {
        NonNullList<ItemStack> mutableItems = NonNullList.withSize(size, ItemStack.EMPTY);
        containerOrDefault(stack).copyInto(mutableItems);
        return mutableItems;
    }

    // TODO copy to GottschCore or Treasure2
    public static ItemStackHandler containerAsHandler(ItemStack stack, int size) {
        Optional<ItemContainerContents> optionalContents = container(stack);

        // create the handler
        ItemStackHandler tempHandler = new ItemStackHandler(size) {
            @Override
            protected void onContentsChanged(int slot) {
                // build the list of current items in the handler
                NonNullList<ItemStack> updatedList = NonNullList.withSize(getSlots(), ItemStack.EMPTY);
                for (int i = 0; i < getSlots(); i++) {
                    updatedList.set(i, getStackInSlot(i));
                }
                // save back to the stack as a new component
                stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(updatedList));
            }
        };

        // populate the handler IF the optional is present
        optionalContents.ifPresent(contents -> {
            int slot = 0;
            // the most robust way to copy ALL slots (including empty ones):
            for (int i = 0; i < tempHandler.getSlots() && i < contents.getSlots(); i++) {
                tempHandler.setStackInSlot(i, contents.getStackInSlot(i));
            }
        });

        return tempHandler;
    }

    public static void setContainer(ItemStack stack, ItemContainerContents contents) {
        stack.set(DataComponents.CONTAINER, contents);
    }

    public static void setContainer(ItemStack stack, NonNullList<ItemStack> list) {
        setContainer(stack, ItemContainerContents.fromItems(list));
    }

    /*
     * component setters
     */
    public static void setInventory(ItemStack stack, ItemContainerContents containerContents) {
        stack.set(FurnaceRingComponents.ITEM_INVENTORY, containerContents);
    }

    public static void setState(ItemStack stack, FurnaceState state) {
        stack.set(FurnaceRingComponents.FURNACE_STATE, state);
    }

    public static void setSmelting(ItemStack stack, boolean smelting) {
        stack.set(FurnaceRingComponents.IS_SMELTING, smelting);
    }
}
