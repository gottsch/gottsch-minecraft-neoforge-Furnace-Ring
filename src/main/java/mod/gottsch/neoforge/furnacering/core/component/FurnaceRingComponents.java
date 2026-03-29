package mod.gottsch.neoforge.furnacering.core.component;

import com.mojang.serialization.Codec;
import mod.gottsch.neoforge.furnacering.core.FurnaceRing;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * @author by Mark Gottschling on 12/17/2025
 */
public class FurnaceRingComponents {

    @Deprecated
    public static final DeferredRegister<DataComponentType<?>> COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, FurnaceRing.MOD_ID);

    public static final DeferredRegister.DataComponents REGISTRAR =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, FurnaceRing.MOD_ID);

    @Deprecated
    // TODO redo into a DeferredHolder under REGISTRAR
    // the component stores the actual list of ItemStacks (the inventory contents)
    public static final Supplier<DataComponentType<ItemContainerContents>> ITEM_INVENTORY =
            // 2. use the generic .register() method
            COMPONENT_TYPES.register(
                    "item_inventory",
                    // 3. the lambda defines how to build the component type
                    () -> DataComponentType.<ItemContainerContents>builder()
                            .persistent(ItemContainerContents.CODEC)       // codec for disk/save persistence (NBT/JSON)
                            .networkSynchronized(ItemContainerContents.STREAM_CODEC) // streamCodec for network sync
                            .build()
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FurnaceState>> FURNACE_STATE =
            REGISTRAR.register("furnace_state", () -> DataComponentType.<FurnaceState>builder()
                    .persistent(FurnaceState.CODEC)
                    .networkSynchronized(FurnaceState.STREAM_CODEC)
                    .build());

    public static final Supplier<DataComponentType<Boolean>> IS_SMELTING =
            REGISTRAR.register("is_smelting", () -> DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL) // Sends to client
                    .build());

    public static void register(IEventBus modEventBus) {
        REGISTRAR.register(modEventBus);
    }
}
