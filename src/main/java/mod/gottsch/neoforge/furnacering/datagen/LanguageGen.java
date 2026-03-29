package mod.gottsch.neoforge.furnacering.datagen;

import mod.gottsch.neoforge.furnacering.core.FurnaceRing;
import mod.gottsch.neoforge.furnacering.core.item.FurnaceRingItems;
import mod.gottsch.neoforge.furnacering.core.util.LangUtil;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;


/**
 *
 * @author Mark Gottschling on Mar 26, 2025
 *
 */
public class LanguageGen extends LanguageProvider {

    public LanguageGen(PackOutput gen, String locale) {
        super(gen, FurnaceRing.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {

        // items
        add(FurnaceRingItems.FURNACE_RING.get(), "Furnace Ring");
        add(FurnaceRingItems.BLAST_FURNACE_RING.get(), "Blast Furnace Ring");
        add(FurnaceRingItems.SMOKER_RING.get(), "Smoker Ring");

        /*
         *  Util.tooltips
         */
        // general
        add(LangUtil.tooltip("hold_shift"), "Hold [SHIFT] to expand");

        add(LangUtil.tooltip("furnace_ring.desc"), "A Furnace Ring is a portable Furnace.");
        add(LangUtil.tooltip("blast_furnace_ring.desc"), "A Blast Furnace Ring is a portable Blast Furnace.");
        add(LangUtil.tooltip("smoker_ring.desc"), "A Smoker Ring is a portable Smoker.");

        // TODO this should be a general usage verbiage
        add(LangUtil.tooltip("furnace_ring.usage"), "Equip to enable ring:~Vanilla Only - Place ring in hotbar. First two rings found will be equipped.~Curios Mod - Place ring in Ring Slots.");

        // TODO expand on how to Equip ie for vanilla and Curios
        // TODO explain when active it will be displayed with orange bg and maybe
        //  and icon change.
    }
}
