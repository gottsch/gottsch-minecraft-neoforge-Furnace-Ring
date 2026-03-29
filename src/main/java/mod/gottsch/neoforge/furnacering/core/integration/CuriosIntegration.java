package mod.gottsch.neoforge.furnacering.core.integration;

import mod.gottsch.neoforge.furnacering.core.component.ComponentHelper;
import mod.gottsch.neoforge.furnacering.core.component.FurnaceRingComponents;
import mod.gottsch.neoforge.furnacering.core.item.FurnaceRingItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

/**
 * @author by Mark Gottschling on 12/23/2025
 */
public class CuriosIntegration {

    public static boolean isLoaded() {
        return ModList.get().isLoaded("curios");
    }

    public static void init() {
        // This method is called from your Main Mod Class only if Curios is loaded
        CuriosApi.registerCurio(FurnaceRingItems.FURNACE_RING.get(), new ICurioItem() {
            @Override
            public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
                // reset the component
                // TODO debug and check that this is actually being called.
                ComponentHelper.setSmelting(stack, false);
                // Force a sync to the client so the texture updates while the item is in the cursor
                if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
                    serverPlayer.containerMenu.broadcastChanges();
                }
            }
        });
        CuriosApi.registerCurio(FurnaceRingItems.BLAST_FURNACE_RING.get(), new ICurioItem() {
            @Override
            public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
                ComponentHelper.setSmelting(stack, false);
                if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
                    serverPlayer.containerMenu.broadcastChanges();
                }
            }
        });
        CuriosApi.registerCurio(FurnaceRingItems.SMOKER_RING.get(), new ICurioItem() {
            @Override
            public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
                ComponentHelper.setSmelting(stack, false);
                if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
                    serverPlayer.containerMenu.broadcastChanges();
                }
            }
        });
    }

    public static ICurio getCurioProvider(ItemStack stack) {
        return new ICurio() {
            @Override
            public void curioTick(SlotContext slotContext) {
                // call the ring's inventoryTick() method
                stack.getItem().inventoryTick(
                        stack,
                        slotContext.entity().level(),
                        slotContext.entity(),
                        -1, // dummy slot ID
                        false
                );
            }

            @Override
            public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                ComponentHelper.setSmelting(stack, false);
                // Force a sync to the client so the texture updates while the item is in the cursor
                if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
                    serverPlayer.containerMenu.broadcastChanges();
                }
            }

            @Override
            public ItemStack getStack() {
                return stack;
            }
        };
    }
}
