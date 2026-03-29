package mod.gottsch.neoforge.furnacering.datagen;

import mod.gottsch.neoforge.furnacering.core.item.FurnaceRingItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Mark Gottschling on Nov 26, 2022
 *
 */
public class Recipes extends RecipeProvider {

	public Recipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput output) {
		/*
		 * furnace ring
		 */
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, FurnaceRingItems.FURNACE_RING.get())
				.pattern("nin")
				.pattern("ifi")
				.pattern("nin")
				.define('i', Items.IRON_INGOT)
				.define('f', Items.FURNACE)
				.define('n', Items.NETHER_BRICK)
				.unlockedBy("has_ingredients", InventoryChangeTrigger.TriggerInstance.hasItems(Items.FURNACE))
				.save(output);

		/*
		 * blast furnace ring
		 */
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, FurnaceRingItems.BLAST_FURNACE_RING.get())
				.pattern("nin")
				.pattern("ifi")
				.pattern("nin")
				.define('i', Items.IRON_INGOT)
				.define('f', Items.BLAST_FURNACE)
				.define('n', Items.BLAZE_ROD)
				.unlockedBy("has_ingredients", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BLAST_FURNACE))
				.save(output);

		/*
		 * smoker ring
		 */
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, FurnaceRingItems.SMOKER_RING.get())
				.pattern("nin")
				.pattern("ifi")
				.pattern("nin")
				.define('i', Items.COPPER_INGOT)
				.define('f', Items.SMOKER)
				.define('n', Items.DRIED_KELP)
				.unlockedBy("has_ingredients", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SMOKER))
				.save(output);
	}
}
