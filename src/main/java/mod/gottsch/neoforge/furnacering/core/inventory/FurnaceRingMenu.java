package mod.gottsch.neoforge.furnacering.core.inventory;

import mod.gottsch.neoforge.furnacering.core.component.ComponentHelper;
import mod.gottsch.neoforge.furnacering.core.component.FurnaceState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.RecipeType;

/**
 * @author by Mark Gottschling on 12/19/2025
 */
public class FurnaceRingMenu extends BaseFurnaceRingMenu {
    // a reference to the stack instance being displayed by this menu
//    private final ItemStack stack;

    // client constructor
    public FurnaceRingMenu(int containerId, Inventory playerInv, RegistryFriendlyByteBuf buffer) {
        this(containerId, playerInv, decodeStack(buffer));
    }

    // server constructor
    public FurnaceRingMenu(int containerId, Inventory playerInv, ItemStack stack) {
//        super(FurnaceRingContainers.FURNACE_RING_MENU.get(), RecipeType.SMELTING, RecipeBookType.FURNACE,
//                containerId, playerInv, createContainer(stack), createData(stack));
        super(FurnaceRingContainers.FURNACE_RING_MENU.get(), RecipeType.SMELTING, RecipeBookType.FURNACE,
                containerId, playerInv, stack);
//        this.stack = stack;
    }

//    private static ItemStack decodeStack(RegistryFriendlyByteBuf buffer) {
//        if (buffer instanceof RegistryFriendlyByteBuf registryBuf) {
//            return ItemStack.OPTIONAL_STREAM_CODEC.decode(registryBuf);
//        }
//        // fallback for safety
//        return ItemStack.EMPTY;
//    }

//    private static Container createContainer(ItemStack stack) {
//        // create a new inventory with 3 slots (Input, Fuel, Output)
//        SimpleContainer newContainer = new SimpleContainer(3);
//
//        // take items currently inside the Ring and put them in the temp container
//        ItemContainerContents contents = ComponentHelper.containerOrDefault(stack);
//        contents.copyInto(newContainer.getItems());
//
//        // every time a slot changes (item moved), update the ItemStack's component
//        newContainer.addListener(container -> {
//            stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(newContainer.getItems()));
//        });
//
//        return newContainer;
//    }
//
//    private static ContainerData createData(ItemStack stack) {
//        return new ContainerData() {
//            @Override
//            public int get(int index) {
//                // retrieve the record from the Ring
//                FurnaceState s = ComponentHelper.stateOrDefault(stack);
//                return switch (index) {
//                    case 0 -> s.burnTime();
//                    case 1 -> s.totalBurnTime();
//                    case 2 -> s.cookTime();
//                    case 3 -> s.totalCookTime();
//                    default -> 0;
//                };
//            }
//
//            @Override
//            public void set(int index, int value) {
//                // update the record and save it back to the specific Ring instance
//                FurnaceState s = ComponentHelper.stateOrDefault(stack);
//                FurnaceState newState = switch (index) {
//                    case 0 -> s.withBurnTime(value);
//                    case 1 -> s.withTotalBurnTime(value);
//                    case 2 -> s.withCookTime(value);
//                    case 3 -> s.withTotalCookTime(value);
//                    default -> s;
//                };
//                ComponentHelper.setState(stack, newState);
//            }
//
//            @Override
//            public int getCount() { return 4; }
//        };
//    }
//
//    public Container getContainer() {
//        return this.container;
//    }
//
//    @Override
//    public float getBurnProgress() {
//        int i = this.data.get(2); // Current cook time
//        int j = this.data.get(3); // Total cook time
//        return j != 0 && i != 0 ? (float) (i * 24) / j : 0;
//    }
//
//    @Override
//    public float getLitProgress() {
//        int i = this.data.get(1); // total burn time
//        if (i == 0) i = 200;
//        return (float) (this.data.get(0) * 13) / i;
//    }
//
//    public ItemStack getStack() {
//        return this.stack;
//    }
}
