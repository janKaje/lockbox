package com.ssblur.lockbox.platform;

import com.ssblur.lockbox.platform.services.ConfigHelper;
import com.ssblur.lockbox.LockboxConfigFabric;

public class FabricConfigHelper implements ConfigHelper {
	@Override
	public boolean allowFrameBreak() {
		return LockboxConfigFabric.ALLOW_FRAME_BREAK;
	}

	@Override
	public int maxSize() { return LockboxConfigFabric.MAX_SIZE; }
}
