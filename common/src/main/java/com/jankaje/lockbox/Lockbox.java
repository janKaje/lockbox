package com.jankaje.lockbox;

import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class Lockbox {
  public static final String MOD_ID = "lockbox";
  public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(MOD_ID, Registries.CREATIVE_MODE_TAB);
//  public static final RegistrySupplier<CreativeModeTab> CREATIVE_TAB = TABS.register("lockbox", () ->
//      CreativeTabRegistry.create(Component.translatable("itemGroup." + MOD_ID + ".lockbox"),
//          () -> new ItemStack(Lockbox.LOCKBOX_CORE_ITEM.get())));

  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
  public static final RegistrySupplier<Block> LOCKBOX_FRAME = BLOCKS.register("lockbox_frame", () ->
    new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(1.5f, 6.0f)
    ));
  public static final RegistrySupplier<Block> LOCKBOX_CORE = BLOCKS.register("lockbox_core", () ->
    new LockboxBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(1.5f, 6.0f)
    ));
  public static final RegistrySupplier<Block> HARDENED_LOCKBOX_FRAME = BLOCKS.register("hardened_lockbox_frame", () ->
          new Block(BlockBehaviour.Properties.of()
                  .mapColor(MapColor.STONE)
                  .instrument(NoteBlockInstrument.BASEDRUM)
                  .requiresCorrectToolForDrops()
                  .strength(1.5f, 6.0f)
                  .explosionResistance(1200.0f)
          ));
  public static final RegistrySupplier<Block> HARDENED_LOCKBOX_CORE = BLOCKS.register("hardened_lockbox_core", () ->
          new LockboxBlock(BlockBehaviour.Properties.of()
                  .mapColor(MapColor.STONE)
                  .instrument(NoteBlockInstrument.BASEDRUM)
                  .requiresCorrectToolForDrops()
                  .strength(1.5f, 6.0f)
                  .explosionResistance(1200.0f)
          ));

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
  public static final RegistrySupplier<Item> LOCKBOX_FRAME_ITEM = ITEMS.register("lockbox_frame", () ->
      new BlockItem(LOCKBOX_FRAME.get(), new Item.Properties().arch$tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)));
  public static final RegistrySupplier<Item> LOCKBOX_CORE_ITEM = ITEMS.register("lockbox_core", () ->
    new BlockItem(LOCKBOX_CORE.get(), new Item.Properties().arch$tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)));
  public static final RegistrySupplier<Item> HARDENED_LOCKBOX_FRAME_ITEM = ITEMS.register("hardened_lockbox_frame", () ->
    new BlockItem(HARDENED_LOCKBOX_FRAME.get(), new Item.Properties().arch$tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)));
  public static final RegistrySupplier<Item> HARDENED_LOCKBOX_CORE_ITEM = ITEMS.register("hardened_lockbox_core", () ->
    new BlockItem(HARDENED_LOCKBOX_CORE.get(), new Item.Properties().arch$tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)));

  public static final LockboxEvents EVENTS = new LockboxEvents();
  public static void init() {
    TABS.register();
    BLOCKS.register();
    ITEMS.register();

    BlockEvent.BREAK.register(EVENTS);
    BlockEvent.PLACE.register(EVENTS);
    InteractionEvent.RIGHT_CLICK_BLOCK.register(EVENTS);

    System.out.println(LockboxExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
  }
}
