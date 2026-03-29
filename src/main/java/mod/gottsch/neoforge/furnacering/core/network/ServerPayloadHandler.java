package mod.gottsch.neoforge.furnacering.core.network;

import mod.gottsch.neoforge.furnacering.core.inventory.BlastFurnaceRingMenu;
import mod.gottsch.neoforge.furnacering.core.inventory.FurnaceRingMenu;
import mod.gottsch.neoforge.furnacering.core.inventory.SmokerRingMenu;
import mod.gottsch.neoforge.furnacering.core.item.BlastFurnaceRingItem;
import mod.gottsch.neoforge.furnacering.core.item.FurnaceRingItem;
import mod.gottsch.neoforge.furnacering.core.item.SmokerRingItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import top.theillusivec4.curios.api.CuriosApi;

/**
 * @author by Mark Gottschling on 12/23/2025
 */
public class ServerPayloadHandler {
    public static void handleOpenRing(final OpenCurioRingPacket data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
//                // Use Curios API to find the stack
//                CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
//                    // Get the item from the specific slot index
//                    ItemStack stack = handler.getStacksHandler(CuriosApi.getSlot(player, "ring").get())
//                            .map(h -> h.getStacks().getStackInSlot(data.slotIndex()))
//                            .orElse(ItemStack.EMPTY);
//
//                    if (stack.getItem() instanceof FurnaceRingItem) {
//                        player.openMenu(new SimpleMenuProvider((id, inv, p) ->
//                                        new StandardFurnaceRingMenu(id, inv, stack), stack.getHoverName()),
//                                buf -> buf.writeItemStack(stack, false));
//                    }
//                });
                // access the specific "ring" slot group
                CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.getStacksHandler("ring"))
                        .ifPresent(stacksHandler -> {
                            // get the stack from the specific index sent by the client
                            ItemStack stack = stacksHandler.getStacks().getStackInSlot(data.slotIndex());

                            // NOTE this is OK since there is a small number of rings currently, and is only
                            //  checked on a right-click event. however, a better solution might be to send an
                            //  additional data point to indicate the ring, ex. ringType = 1 --> Furnace Ring
                            if (stack.getItem() instanceof FurnaceRingItem) {
                                player.openMenu(new SimpleMenuProvider((id, inv, p) ->
                                                new FurnaceRingMenu(id, inv, stack), stack.getHoverName()),
                                        // use the FriendlyByteBuf method
                                        buf -> ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, stack));
                            }
                            else if (stack.getItem() instanceof BlastFurnaceRingItem) {
                                player.openMenu(new SimpleMenuProvider((id, inv, p) ->
                                                new BlastFurnaceRingMenu(id, inv, stack), stack.getHoverName()),
                                        // use the FriendlyByteBuf method
                                        buf -> ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, stack));
                            }
                            else if (stack.getItem() instanceof SmokerRingItem) {
                                player.openMenu(new SimpleMenuProvider((id, inv, p) ->
                                                new SmokerRingMenu(id, inv, stack), stack.getHoverName()),
                                        // use the FriendlyByteBuf method
                                        buf -> ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, stack));
                            }
                        });
            }
        });

    }
}
