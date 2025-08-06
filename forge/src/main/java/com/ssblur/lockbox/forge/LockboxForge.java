package com.jankaje.lockbox.forge;

import dev.architectury.platform.forge.EventBuses;
import com.jankaje.lockbox.Lockbox;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Lockbox.MOD_ID)
public class LockboxForge {
  public LockboxForge() {
    // Submit our event bus to let architectury register our content on the right time
    EventBuses.registerModEventBus(Lockbox.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
    Lockbox.init();
  }
}
