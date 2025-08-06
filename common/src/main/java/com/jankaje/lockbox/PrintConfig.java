package com.jankaje.lockbox;

import com.jankaje.lockbox.platform.Services;
import com.jankaje.lockbox.platform.services.ConfigHelper;

public class PrintConfig {
    public static void printConfigs() {
        ConfigHelper config = Services.CONFIG;
        LockboxConstants.logInfo("Configs:" +
                "\nAllow Frame Break: " + config.allowFrameBreak() +
                "\nMax Size: " + config.maxSize()
        );
    }
}
