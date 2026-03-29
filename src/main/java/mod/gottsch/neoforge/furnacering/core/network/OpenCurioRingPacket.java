package mod.gottsch.neoforge.furnacering.core.network;

import io.netty.buffer.ByteBuf;
import mod.gottsch.neoforge.furnacering.core.FurnaceRing;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * @author by Mark Gottschling on 12/23/2025
 */
public record OpenCurioRingPacket(int slotIndex) implements CustomPacketPayload {
    public static final Type<OpenCurioRingPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(FurnaceRing.MOD_ID, "open_curio_ring"));

    // Codec for networking
    public static final StreamCodec<ByteBuf, OpenCurioRingPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, OpenCurioRingPacket::slotIndex,
            OpenCurioRingPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
