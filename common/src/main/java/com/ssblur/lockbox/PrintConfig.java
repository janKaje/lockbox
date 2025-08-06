package com.ssblur.lockbox;

import com.ssblur.lockbox.platform.Services;
import com.ssblur.lockbox.platform.services.ConfigHelper;

public class PrintConfig {
    public static void printConfigs() {
        ConfigHelper config = Services.CONFIG;
        LockboxConstants.logInfo("Configs:" +
                "\nAllow Frame Break: " + config.allowFrameBreak() +
                "\nMax Size: " + config.maxSize()
        );
    }
}
