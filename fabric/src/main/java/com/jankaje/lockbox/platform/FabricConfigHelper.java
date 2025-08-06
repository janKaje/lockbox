package com.jankaje.lockbox.platform;

import com.jankaje.lockbox.platform.services.ConfigHelper;
import com.jankaje.lockbox.LockboxConfigFabric;

public class FabricConfigHelper implements ConfigHelper {
	@Override
	public boolean allowFrameBreak() {
		return LockboxConfigFabric.ALLOW_FRAME_BREAK;
	}

	@Override
	public int maxSize() { return LockboxConfigFabric.MAX_SIZE; }
}
