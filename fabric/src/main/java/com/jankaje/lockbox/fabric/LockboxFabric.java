package com.jankaje.lockbox.fabric;

import com.jankaje.lockbox.Lockbox;
import com.jankaje.lockbox.PrintConfig;
import net.fabricmc.api.ModInitializer;

public class LockboxFabric implements ModInitializer {
  @Override
  public void onInitialize() {
    Lockbox.init();
    PrintConfig.printConfigs();
  }
}
