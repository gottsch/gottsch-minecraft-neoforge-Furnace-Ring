# Changelog for Neoforge Furnace Ring 1.21.1

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.1.0] - 2026-03-29

### 🐛 Fixed

#### Networking
- **GUI open codec mismatch** — `use()` (right-click in hand) was encoding the ring `ItemStack` with `STREAM_CODEC` while the client constructor decoded it with `OPTIONAL_STREAM_CODEC`. The wire formats differ, causing a deserialization error and a broken GUI whenever a ring was opened from the hotbar or offhand. Encoding now uses `OPTIONAL_STREAM_CODEC` on both sides.

#### Curios Integration
- **Unequip callbacks never fired on dedicated servers** — `CuriosIntegration.init()` was called inside `FMLClientSetupEvent`, a client-only lifecycle event. On dedicated servers the method never ran, so `onUnequip` callbacks (which reset the smelting state when a ring is removed) were silently skipped. Moved to `FMLCommonSetupEvent` so callbacks register on both client and server.

#### Smelting Logic
- **Ring limit ignored Blast Furnace and Smoker rings** — the hotbar 2-ring cap (non-Curios mode) checked `instanceof FurnaceRingItem`, so Blast Furnace and Smoker rings bypassed the limit entirely. Check updated to `instanceof AbstractFurnaceRingItem`.
- **Fuel value always queried as SMELTING** — `getBurnTime(RecipeType.SMELTING)` was hardcoded in `inventoryTick`, meaning Blast Furnace and Smoker rings used the wrong recipe type when consuming fuel. Changed to `getBurnTime(getRecipeType())` so each subclass uses its own type.
- **`BlastFurnaceRingMenu` used wrong recipe type** — the menu was constructed with `RecipeType.SMELTING` and `RecipeBookType.FURNACE`. The recipe book showed smelting recipes instead of blasting recipes, and `canSmelt()` accepted non-blastable items. Corrected to `RecipeType.BLASTING` / `RecipeBookType.BLAST_FURNACE`.

### 🔧 Internal

- **Dead `ContainerData` in `use()`** — a 30-line anonymous `ContainerData` was created but never passed to `getRingMenu()`; `BaseFurnaceRingMenu` builds its own via `createData()`. Removed along with the now-unused `ContainerData` import.
- **Redundant `instanceof` in `decodeStack`** — `BaseFurnaceRingMenu.decodeStack(RegistryFriendlyByteBuf)` checked `instanceof RegistryFriendlyByteBuf` on its own parameter (always true) with an unreachable fallback. Collapsed to a direct decode call.
- **Magic index in wet-sponge handling** — `items.set(1, ...)` replaced with `items.set(FUEL_SLOT, ...)` for consistency with the rest of the method.
- **Invalid mod version string** — `mod_version` in `gradle.properties` was `neoforge-1.21.1-1.1.0`; the `neoforge-` prefix is not a valid Maven version qualifier and caused a `Illegal version number` crash at mod load. Changed to `1.21.1-1.1.0`.

---

## [1.0.0] - 2026-01-15

### Initial Release
