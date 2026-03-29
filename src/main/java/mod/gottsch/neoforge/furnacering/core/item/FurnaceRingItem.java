package mod.gottsch.neoforge.furnacering.core.item;

import mod.gottsch.neoforge.furnacering.core.inventory.FurnaceRingMenu;
import mod.gottsch.neoforge.furnacering.core.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * @author by Mark Gottschling on 12/17/2025
 */
public class FurnaceRingItem extends AbstractFurnaceRingItem {

    public FurnaceRingItem(Properties properties) {
        super(properties.stacksTo(1), RecipeType.SMELTING);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable(LangUtil.tooltip("furnace_ring.desc")).withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public  void appendHoverSpecials(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flag) {
    }

    public void appendHoverExtras(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flag) {
    }

    @Override
    protected AbstractContainerMenu getRingMenu(int id, Inventory inv, ItemStack stack) {
        return new FurnaceRingMenu(id, inv, stack);
    }
}
