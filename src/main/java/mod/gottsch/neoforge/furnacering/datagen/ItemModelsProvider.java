package mod.gottsch.neoforge.furnacering.datagen;

import mod.gottsch.neoforge.furnacering.core.FurnaceRing;
import mod.gottsch.neoforge.furnacering.core.item.FurnaceRingItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * 
 * @author Mark Gottschling on 11/9/2025
 *
 */
public class ItemModelsProvider extends ItemModelProvider {

	public ItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, FurnaceRing.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {

		// 1. generate the "lit" models first (the one the override points to)
		ItemModelBuilder litModel = withExistingParent("furnace_ring_lit", "furnacering:item/jewelry")
				.texture("layer0", modLoc("item/furnace_ring_lit"));

		ItemModelBuilder blastFurnaceLitModel = withExistingParent("blast_furnace_ring_lit", "furnacering:item/jewelry")
				.texture("layer0", modLoc("item/blast_furnace_ring_lit"));

		ItemModelBuilder smokerRingLitModel = withExistingParent("smoker_ring_lit", "furnacering:item/jewelry")
				.texture("layer0", modLoc("item/smoker_ring_lit"));

		// 2. generate the "base" models with the override logic
		withExistingParent("furnace_ring", "furnacering:item/jewelry")
				.texture("layer0", modLoc("item/furnace_ring"))
				.override()
				.predicate(modLoc("lit"), 1.0f) // The property we registered in ModItemProperties
				.model(litModel)               // Point to the litModel builder above
				.end();

		withExistingParent("blast_furnace_ring", "furnacering:item/jewelry")
				.texture("layer0", modLoc("item/blast_furnace_ring"))
				.override()
				.predicate(modLoc("lit"), 1.0f)
				.model(blastFurnaceLitModel)
				.end();

		withExistingParent("smoker_ring", "furnacering:item/jewelry")
				.texture("layer0", modLoc("item/smoker_ring"))
				.override()
				.predicate(modLoc("lit"), 1.0f)
				.model(smokerRingLitModel)
				.end();
	}

	// Helper method for generating a simple generated item model
//	private void simpleItem(Item item) {
		// The item's model is generated using the 'item/generated' parent model
		// which is used for flat, 2D items (like ingots, berries, etc.).
//		withExistingParent(item.asItem().get.getPath(), // Resource name of the item
//				new ResourceLocation("item/generated"))
//				// The texture for layer0 is set to the item's name under "item/"
//				.texture("layer0", new ResourceLocation(FurnanceRing.MOD_ID, "item/" + item.asItem().registryName().getPath()));
//	}
}
