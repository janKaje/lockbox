package com.jankaje.lockbox.platform;

import com.jankaje.lockbox.LockboxConstants;
import com.jankaje.lockbox.platform.services.ConfigHelper;

import java.util.ServiceLoader;

public class Services {
	public static final ConfigHelper CONFIG = load(ConfigHelper.class);

	private static <T> T load(Class<T> clazz) {
		final T service = ServiceLoader.load(clazz)
			.findFirst()
			.orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
		System.out.println("DEBUG");
		LockboxConstants.logDebug("Loaded {} for service {}", service, clazz);
		return service;
	}
}
