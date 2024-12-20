package org.example.taxiuber.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingConfig {
    private static final Logger logger = LogManager.getLogger(LoggingConfig.class);

    static {
        logger.info("LoggingConfig initialized.");
    }

    public LoggingConfig() {
    }
}
