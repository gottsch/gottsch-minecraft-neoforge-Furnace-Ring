package mod.gottsch.neoforge.furnacering.datagen;

import mod.gottsch.neoforge.furnacering.core.FurnaceRing;
import mod.gottsch.neoforge.furnacering.core.item.FurnaceRingItems;
import mod.gottsch.neoforge.furnacering.core.loot.modifier.AddItemModifier;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

/**
 * @author by Mark Gottschling on 12/26/2025
 */
public class LootModifiersProvider extends GlobalLootModifierProvider {
    public LootModifiersProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, FurnaceRing.MOD_ID);
    }

    @Override
    protected void start() {

        // furnace ring
        add("furnace_ring_from_zombie",
                new AddItemModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/zombie")).build(),
                        LootItemKilledByPlayerCondition.killedByPlayer().build(),
                        LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, 0.015f, 0.01f).build()
                        // 1.5% base + 1% per Looting level
                }, FurnaceRingItems.FURNACE_RING.get()));

        add("furnace_ring_from_husk",
                new AddItemModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/husk")).build(),
                        LootItemKilledByPlayerCondition.killedByPlayer().build(),
                        LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, 0.015f, 0.01f).build()
                        // 1.5% base + 1% per Looting level
                }, FurnaceRingItems.FURNACE_RING.get()));

        add("furnace_ring_in_mineshaft",
                new AddItemModifier(
                        new LootItemCondition[]{
                                LootTableIdCondition.builder(BuiltInLootTables.ABANDONED_MINESHAFT.location()).build(),
                                LootItemRandomChanceCondition.randomChance(0.05f).build() // 5% chance
                        }, FurnaceRingItems.FURNACE_RING.get()));

        // blast furnace rings
        add("blast_furnace_ring_from_iron_golem",
                new AddItemModifier(new LootItemCondition[] {
                        LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().of(EntityType.IRON_GOLEM)).build(),
                        LootItemKilledByPlayerCondition.killedByPlayer().build(),
                        LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, 0.005f, 0.01f).build()
                }, FurnaceRingItems.BLAST_FURNACE_RING.get()));

        add("blast_furnace_ring_in_ruined_portal",
                new AddItemModifier(
                        new LootItemCondition[]{
                                LootTableIdCondition.builder(BuiltInLootTables.RUINED_PORTAL.location()).build(),
                                LootItemRandomChanceCondition.randomChance(0.05f).build() // 5% chance
                        }, FurnaceRingItems.BLAST_FURNACE_RING.get()));

        add("blast_furnace_ring_in_nether_fortress",
                new AddItemModifier(
                        new LootItemCondition[]{
                                LootTableIdCondition.builder(BuiltInLootTables.NETHER_BRIDGE.location()).build(),
                                LootItemRandomChanceCondition.randomChance(0.05f).build() // 5% chance
                        }, FurnaceRingItems.BLAST_FURNACE_RING.get()));


        // smoker ring
        add("smoker_ring_from_witch",
                new AddItemModifier(new LootItemCondition[] {
                        LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().of(EntityType.WITCH)).build(),
                        LootItemKilledByPlayerCondition.killedByPlayer().build(),
                        LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, 0.02f, 0.01f).build()
                }, FurnaceRingItems.SMOKER_RING.get()));

        add("smoker_ring_in_butcher",
                new AddItemModifier(
                        new LootItemCondition[]{
                                LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_BUTCHER.location()).build(),
                                LootItemRandomChanceCondition.randomChance(0.05f).build() // 5% chance
                        }, FurnaceRingItems.SMOKER_RING.get()));

    }
}