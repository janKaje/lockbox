package com.ssblur.lockbox.fabric;

import com.ssblur.lockbox.Lockbox;
import com.ssblur.lockbox.PrintConfig;
import net.fabricmc.api.ModInitializer;

public class LockboxFabric implements ModInitializer {
  @Override
  public void onInitialize() {
    Lockbox.init();
    PrintConfig.printConfigs();
  }
}
