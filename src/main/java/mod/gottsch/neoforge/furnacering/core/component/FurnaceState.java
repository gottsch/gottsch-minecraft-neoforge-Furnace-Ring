package mod.gottsch.neoforge.furnacering.core.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * the furnace state at any given time.
 * NOTE should not be used to determine if the furnace is active or not, since there
 * is only a limited number of slots that a furnace ring be active, BUT it will retain
 * its last state (burn time, cook time etc) while in a non-active slot.
 * the IS_SMELTING data component to check for active/non-active.
 *
 * @author by Mark Gottschling on 12/19/2025
 */
public record FurnaceState(int burnTime, int totalBurnTime, int cookTime, int totalCookTime) {

    // a constant for the default "empty" state
    public static final FurnaceState EMPTY = new FurnaceState(0, 0, 0, 0);

    // CODEC: required for saving to NBT/JSON (disk)
    public static final Codec<FurnaceState> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("burn_time").forGetter(FurnaceState::burnTime),
                    Codec.INT.fieldOf("total_burn_time").forGetter(FurnaceState::totalBurnTime),
                    Codec.INT.fieldOf("cook_time").forGetter(FurnaceState::cookTime),
                    Codec.INT.fieldOf("total_cook_time").forGetter(FurnaceState::totalCookTime)
            ).apply(instance, FurnaceState::new)
    );

    // STREAM_CODEC: Required for syncing between Server and Client (network)
    public static final StreamCodec<RegistryFriendlyByteBuf, FurnaceState> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, FurnaceState::burnTime,
            ByteBufCodecs.VAR_INT, FurnaceState::totalBurnTime,
            ByteBufCodecs.VAR_INT, FurnaceState::cookTime,
            ByteBufCodecs.VAR_INT, FurnaceState::totalCookTime,
            FurnaceState::new
    );

    // helper methods to create modified copies (since records are immutable)
    public FurnaceState withBurnTime(int time) {
        return new FurnaceState(time, this.totalBurnTime, this.cookTime, this.totalCookTime);
    }

    public FurnaceState withTotalBurnTime(int time) {
        return new FurnaceState(this.burnTime, time, this.cookTime, this.totalCookTime);
    }

    public FurnaceState withCookTime(int time) {
        return new FurnaceState(this.burnTime, this.totalBurnTime, time, this.totalCookTime);
    }

    public FurnaceState withTotalCookTime(int time) {
        return new FurnaceState(this.burnTime, this.totalBurnTime, this.cookTime, time);
    }
}
