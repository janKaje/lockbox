package com.ssblur.lockbox;

import com.ssblur.lockbox.SparkConfig.Config;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LockboxConfigFabric {
	@Config(
		value = "allowFrameBreak",
		comment = """
			Whether or not to allow players who are not the owner
			of the lockbox break the surrounding frame.
			"""
	)
	public static boolean ALLOW_FRAME_BREAK = false;
	@Config(
		value = "maxSize",
		comment = """
			Maximum size of a lockbox.
			Size is the length of each side of a cube.
			""",
		min = 3,
		max = 256
	)
	public static int MAX_SIZE = 32;

	private LockboxConfigFabric() {}

	public static void init() {
		Path configFile = FabricLoader.getInstance().getConfigDir().resolve(LockboxConstants.MOD_ID + ".properties");

		if (Files.exists(configFile)) {
			LockboxConstants.logInfo("Config file found");
			try {
				SparkConfig.read(configFile, LockboxConfigFabric.class);
			} catch (IOException | IllegalAccessException e) {
				LockboxConstants.logError(e, "Failed to read config file {}", configFile);
			}
		} else {
			LockboxConstants.logInfo("No config file found - creating it");
			try {
				SparkConfig.write(configFile, LockboxConfigFabric.class);
			} catch (IOException | IllegalAccessException e) {
				LockboxConstants.logError(e, "Failed to write config file {}", configFile);
			}
		}
	}
}
