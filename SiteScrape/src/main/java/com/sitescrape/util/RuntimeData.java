package com.sitescrape.util;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Created by Deslyxia on 6/22/17.
 */
public enum RuntimeData {
    INSTANCE;

    private static final String CONFIG_FILE_SUFFIX = "-config.xml";

    private CombinedConfiguration runtimeConfiguration = new CombinedConfiguration();

    private Boolean configured = false;

    public void configure(String configurationDirectory) throws IOException {

        // Read in all config files.
        File configDirectory = new File(configurationDirectory);
        File[] configFileList = configDirectory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(CONFIG_FILE_SUFFIX);
            }
        });

        for (File configFile : configFileList) {
            try {
                runtimeConfiguration.addConfiguration(new XMLConfiguration(
                        configFile));
            } catch (ConfigurationException e) {
                System.out.println(e);
                System.exit(1);
            }
        }
        this.configured = true;
    }

    public String getConfigurationValue(String configurationKey) {
        if (!this.configured) {
            throw new RuntimeException("RuntimeData configuration not called.");
        }
        return runtimeConfiguration.getString(configurationKey);
    }
}
