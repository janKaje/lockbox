package com.jankaje.lockbox.platform.services;

public interface ConfigHelper {
	/**
	 * Whether or not to allow players who are not the owner of the lockbox break the surrounding frame.
	 */
	boolean allowFrameBreak();

	/**
	 * Maximum size of a lockbox.
	 */
	int maxSize();
}