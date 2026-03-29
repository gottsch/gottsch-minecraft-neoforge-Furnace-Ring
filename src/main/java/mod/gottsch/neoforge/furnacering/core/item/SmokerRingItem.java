package mod.gottsch.neoforge.furnacering.core.item;

import mod.gottsch.neoforge.furnacering.core.inventory.SmokerRingMenu;
import mod.gottsch.neoforge.furnacering.core.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * @author by Mark Gottschling on 1/1/2026
 */
public class SmokerRingItem extends AbstractFurnaceRingItem {

    public SmokerRingItem(Properties properties) {
        super(properties, RecipeType.SMOKING);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable(LangUtil.tooltip("smoker_ring.desc")).withStyle(ChatFormatting.GOLD));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    protected AbstractContainerMenu getRingMenu(int id, Inventory inv, ItemStack stack) {
        return new SmokerRingMenu(id, inv, stack);
    }

    /*
     *
     */
    @Override
    protected int getTotalCookTime(Level level, NonNullList<ItemStack> items) {
        return super.getTotalCookTime(level, items) / 2;
    }

    @Override
    protected RecipeType<? extends AbstractCookingRecipe> getRecipeType() {
        return RecipeType.SMOKING;
    }
}

