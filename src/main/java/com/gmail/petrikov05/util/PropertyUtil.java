package com.gmail.petrikov05.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertyUtil {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String CONFIG_PROPERTY_FILE_LOCATION = "config.properties";

    private Properties properties;

    public PropertyUtil() {
        this.properties = getProperties();
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_PROPERTY_FILE_LOCATION)) {
            properties.load(input);
        } catch (
                IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Config property file is not found!");
        }
        return properties;
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }

}
