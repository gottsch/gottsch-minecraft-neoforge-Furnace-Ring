package mod.gottsch.neoforge.furnacering.core.item;

import mod.gottsch.neoforge.furnacering.core.inventory.BlastFurnaceRingMenu;
import mod.gottsch.neoforge.furnacering.core.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * @author by Mark Gottschling on 1/1/2026
 */
public class BlastFurnaceRingItem extends AbstractFurnaceRingItem {

    public BlastFurnaceRingItem(Properties properties) {
        super(properties, RecipeType.BLASTING);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable(LangUtil.tooltip("blast_furnace_ring.desc")).withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    protected AbstractContainerMenu getRingMenu(int id, Inventory inv, ItemStack stack) {
        return new BlastFurnaceRingMenu(id, inv, stack);
    }

    /*
     *
     */
    @Override
    protected int getTotalCookTime(Level level, NonNullList<ItemStack> items) {
        return super.getTotalCookTime(level, items) / 2;
    }
}
