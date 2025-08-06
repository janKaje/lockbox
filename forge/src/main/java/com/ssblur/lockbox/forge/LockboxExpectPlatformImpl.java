package com.jankaje.lockbox.forge;

import com.jankaje.lockbox.LockboxExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class LockboxExpectPlatformImpl {
  /**
   * This is our actual method to {@link LockboxExpectPlatform#getConfigDirectory()}.
   */
  public static Path getConfigDirectory() {
    return FMLPaths.CONFIGDIR.get();
  }
}
