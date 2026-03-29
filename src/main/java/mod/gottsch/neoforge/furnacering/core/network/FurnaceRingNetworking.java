package mod.gottsch.neoforge.furnacering.core.network;

import mod.gottsch.neoforge.furnacering.core.FurnaceRing;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/**
 * @author by Mark Gottschling on 12/23/2025
 */
@EventBusSubscriber(modid = FurnaceRing.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class FurnaceRingNetworking {
    public static final ResourceLocation CHANNEL_ID = ResourceLocation.fromNamespaceAndPath(FurnaceRing.MOD_ID, "main_channel");

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(FurnaceRing.MOD_ID).versioned("1");

        registrar.playToServer(
                OpenCurioRingPacket.TYPE,
                OpenCurioRingPacket.STREAM_CODEC,
                ServerPayloadHandler::handleOpenRing
        );
    }
}
