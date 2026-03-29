package mod.gottsch.neoforge.furnacering.core.inventory;

import mod.gottsch.neoforge.furnacering.core.FurnaceRing;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 
 * @author Mark Gottschling on Dec 17, 2025
 *
 */
public class FurnaceRingContainers {
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, FurnaceRing.MOD_ID);
	// containers
	public static final DeferredHolder<MenuType<?>, MenuType<FurnaceRingMenu>> FURNACE_RING_MENU =
			registerMenuType("furnace_ring_menu", FurnaceRingMenu::new);
	public static final DeferredHolder<MenuType<?>, MenuType<BlastFurnaceRingMenu>> BLAST_FURNACE_RING_MENU =
			registerMenuType("blast_furnace_ring_menu", BlastFurnaceRingMenu::new);
	public static final DeferredHolder<MenuType<?>, MenuType<SmokerRingMenu>> SMOKER_RING_MENU =
			registerMenuType("smoker_ring_menu", SmokerRingMenu::new);


	private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
		return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
	}

	public static void register(IEventBus bus) {
		MENUS.register(bus);
	}
}
