package mod.gottsch.neoforge.furnacering.core.item;

import mod.gottsch.neoforge.furnacering.core.component.ComponentHelper;
import mod.gottsch.neoforge.furnacering.core.component.FurnaceRingComponents;
import mod.gottsch.neoforge.furnacering.core.component.FurnaceState;
import mod.gottsch.neoforge.furnacering.core.integration.CuriosIntegration;
import mod.gottsch.neoforge.furnacering.core.inventory.AbstractFurnaceRingMenu;
import mod.gottsch.neoforge.furnacering.core.inventory.BaseFurnaceRingMenu;
import mod.gottsch.neoforge.furnacering.core.inventory.FurnaceRingMenu;
import mod.gottsch.neoforge.furnacering.core.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

/**
 * @author by Mark Gottschling on 1/1/2026
 */
public abstract class AbstractFurnaceRingItem extends Item {
    public static final int INGREDIENT_SLOT = 0;
    public static final int FUEL_SLOT = 1;
    public static final int RESULT_SLOT = 2;

    protected final RecipeManager.CachedCheck<SingleRecipeInput, ? extends AbstractCookingRecipe> quickCheck;

    public AbstractFurnaceRingItem(Properties properties, RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(properties);
        this.quickCheck = RecipeManager.createCheck(recipeType);
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(ChatFormatting.YELLOW);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        LangUtil.appendAdvancedHoverText(tooltipComponents, tt -> {
            tooltipComponents.add(Component.literal(LangUtil.NEWLINE));
            Component lore = Component.translatable(LangUtil.tooltip("furnace_ring.usage"));
            for (String s : lore.getString().split("~")) {
                tooltipComponents.add(Component.translatable(s).withStyle(ChatFormatting.ITALIC));
            }
            appendHoverSpecials(stack, context.level(), tooltipComponents, tooltipFlag);
            appendHoverExtras(stack, context.level(), tooltipComponents, tooltipFlag);
        });
    }
        public  void appendHoverSpecials(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flag) {

    }

    public void appendHoverExtras(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flag) {
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            // load items from the Data Component
            ItemContainerContents contents = ComponentHelper.containerOrDefault(stack);
            NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
            contents.copyInto(items);

            // wrap them in a SimpleContainer (size 3 for Furnace)
            SimpleContainer tempContainer = new SimpleContainer(items.toArray(new ItemStack[0])) {
                @Override
                public void setChanged() {
                    super.setChanged();
                    // this is called every time a slot is updated in the GUI
                    stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.getItems()));
                }
            };

            // copy contents into the container
            if (contents.getSlots() == 3) {
                contents.copyInto(tempContainer.getItems());
            }

            serverPlayer.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> getRingMenu(id, inv, stack),
                    Component.literal(stack.getHoverName().getString())
            ), buf -> {
                // write the stack to the buffer
                ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, stack);
            });
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    abstract protected AbstractContainerMenu getRingMenu(int id, Inventory inv, ItemStack stack);

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        // 1. client-side and non-player short circuit
        if (level.isClientSide || !(entity instanceof ServerPlayer player)) {
            return;
        }

        // 2. determine if the ring is in an "active" slot
        // slotId == -1 is the standard for Curios providers
        boolean isCurio = slotId == -1;
        boolean isHand = isSelected || player.getOffhandItem() == stack;
        boolean isEquipped = isHand || isCurio;
        boolean isIgnore = (CuriosIntegration.isLoaded() && !isCurio)
                || (!CuriosIntegration.isLoaded() && (isCurio || (slotId > 8 && !isHand)));

        // 3. limitation Logic (max 2 rings if Curios is not installed)
        if (!CuriosIntegration.isLoaded() && !isCurio && !isIgnore) {
            // if Curios isn't installed, we only allow the first 2 FurnaceRings found in the hotbar.
            // slotId is the actual slot index so the test needs to be <= to ensure we check that slot as well
            int ringCount = 0;
            for (int i = 0; i <= slotId; i++) {
                if (player.getInventory().getItem(i).getItem() instanceof AbstractFurnaceRingItem) {
                    ringCount++;
                    // if this specific stack is the 3rd+ ring, it's disabled
                    if (player.getInventory().getItem(i) == stack && ringCount > 2) {
                        isEquipped = false;
                        break;
                    }
                }
            }
        }

        // 4. force state to false if not equipped or moved to an invalid slot.
        // this handles the "Stuck Texture" when dragging the item
        if (!isEquipped || isIgnore) {
            if (ComponentHelper.smelting(stack)) {
                ComponentHelper.setSmelting(stack, false);
                // spawn smoke puff logic here for polish
                spawnExtinguishEffect((ServerLevel)level, player);
            }
            return;
        }

        // 5. load state and items
        NonNullList<ItemStack> items = ComponentHelper.containerAsNonNullList(stack, 3);
        FurnaceState state = stack.getOrDefault(FurnaceRingComponents.FURNACE_STATE.get(), FurnaceState.EMPTY);
        boolean changed = false;
        boolean currentlySmelting = false;

        // 6. burn Logic (fuel depletion)
        if (state.burnTime() > 0) {
            state = state.withBurnTime(state.burnTime() - 1);
            changed = true;
        }

        // 7. smelting Logic
        boolean canSmelt = canSmelt(level, items);

        // consume new fuel if needed
        if (state.burnTime() <= 0 && canSmelt) {
            ItemStack fuelStack = items.get(FUEL_SLOT);
            int fuelValue = fuelStack.getBurnTime(getRecipeType());

            if (fuelValue > 0) {
                int totalCookTime = getTotalCookTime(level, items);
                state = new FurnaceState(fuelValue, fuelValue, state.cookTime(), totalCookTime);
                fuelStack.shrink(1);
                changed = true;
            }
        }

        // 8. progress arrow and IS_SMELTING state
        if (state.burnTime() > 0 && canSmelt) {
            int nextCook = state.cookTime() + 1;
            currentlySmelting = true;

            if (nextCook >= state.totalCookTime()) {
                smeltItem(level, items);
                state = state.withCookTime(0);
            } else {
                state = state.withCookTime(nextCook);
            }
        } else if (state.cookTime() > 0) {
            // vanilla cool-down progress reset
            state = state.withCookTime(Math.max(0, state.cookTime() - 2));
        }

        // 9. update IS_SMELTING component if it changed
        if (ComponentHelper.smelting(stack) != currentlySmelting) {
            stack.set(FurnaceRingComponents.IS_SMELTING.get(), currentlySmelting);
            changed = true;
        }

        // 10. sync Changes
        if (changed) {
            ComponentHelper.setContainer(stack, items);
            ComponentHelper.setState(stack, state);

            // sync to Menu if open
            if (player.containerMenu instanceof BaseFurnaceRingMenu menu && menu.getStack() == stack) {
                ItemContainerContents.fromItems(items).copyInto(((SimpleContainer) menu.getContainer()).getItems());
                menu.broadcastChanges();
            }
        }
    }

    protected boolean canSmelt(Level level, NonNullList<ItemStack> items) {
        ItemStack input = items.get(0);
        if (input.isEmpty()) return false;

        return level.getRecipeManager()
                .getRecipeFor(getRecipeType(), new SingleRecipeInput(input), level)
                .map(recipe -> {
                    ItemStack result = recipe.value().assemble(new SingleRecipeInput(input), level.registryAccess());
                    if (result.isEmpty()) return false;

                    ItemStack outputSlot = items.get(RESULT_SLOT);
                    if (outputSlot.isEmpty()) return true;
                    if (!ItemStack.isSameItem(outputSlot, result)) return false;
                    return outputSlot.getCount() + result.getCount() <= outputSlot.getMaxStackSize();
                }).orElse(false);
    }

    protected RecipeType<? extends AbstractCookingRecipe> getRecipeType() {
        return RecipeType.SMELTING;
    }

    protected void smeltItem(Level level, NonNullList<ItemStack> items) {
        ItemStack input = items.get(INGREDIENT_SLOT);
        level.getRecipeManager()
                .getRecipeFor(getRecipeType(), new SingleRecipeInput(input), level)
                .ifPresent(recipe -> {
                    ItemStack result = recipe.value().assemble(new SingleRecipeInput(input), level.registryAccess());
                    ItemStack output = items.get(RESULT_SLOT);

                    if (output.isEmpty()) {
                        items.set(RESULT_SLOT, result.copy());
                    } else {
                        output.grow(result.getCount());
                    }

                    if (input.is(Blocks.WET_SPONGE.asItem()) && !items.get(FUEL_SLOT).isEmpty() && items.get(FUEL_SLOT).is(Items.BUCKET)) {
                        items.set(FUEL_SLOT, new ItemStack(Items.WATER_BUCKET));
                    }

                    input.shrink(1);
                });
    }

    /*
     *
     */
    protected int getTotalCookTime(Level level, NonNullList<ItemStack> items) {
        SingleRecipeInput singlerecipeinput = new SingleRecipeInput(items.get(INGREDIENT_SLOT));
        return this.quickCheck.getRecipeFor(singlerecipeinput, level).map(p_300840_ -> p_300840_.value().getCookingTime()).orElse(200);
    }

    /*
     * visual / audio effects
     */
    protected void spawnExtinguishEffect(ServerLevel level, Player player) {
        for (int i = 0; i < 5; i++) {
            level.sendParticles(
                    ParticleTypes.LARGE_SMOKE,
                    player.getX(), player.getY() + 1.2, player.getZ(),
                    5,      // count
                    0.1,    // deltaX (spread)
                    0.1,    // deltaY
                    0.1,    // deltaZ
                    0.05    // speed (how fast they drift)
            );
        }
        // play extinguish sound
        level.playLocalSound(player.getX(), player.getY(), player.getZ(),
                SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.3F, 1.5F, false);
    }
}
