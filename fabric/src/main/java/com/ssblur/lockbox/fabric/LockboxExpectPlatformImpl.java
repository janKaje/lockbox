package com.ssblur.lockbox.fabric;

import com.ssblur.lockbox.LockboxExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class LockboxExpectPlatformImpl {
  /**
   * This is our actual method to {@link LockboxExpectPlatform#getConfigDirectory()}.
   */
  public static Path getConfigDirectory() {
    return FabricLoader.getInstance().getConfigDir();
  }
}
