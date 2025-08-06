package com.jankaje.lockbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockboxConstants {
	private static final String LOG_PREFIX = "Lockbox -> ";

	public static final String MOD_ID = "lockbox";
	public static final String MOD_NAME = "Lockbox";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static void logError(String text, Object... args) {
		LOG.error(LOG_PREFIX + text, args);
	}

	public static void logError(Throwable throwable, String text, Object... args) {
		LOG.error(String.format(LOG_PREFIX + text, args), throwable);
	}

	public static void logWarn(String text, Object... args) {
		LOG.warn(LOG_PREFIX + text, args);
	}

	public static void logInfo(String text, Object... args) {
		LOG.info(LOG_PREFIX + text, args);
	}

	public static void logDebug(String text, Object... args) {
		LOG.debug(LOG_PREFIX + text, args);
	}
}
