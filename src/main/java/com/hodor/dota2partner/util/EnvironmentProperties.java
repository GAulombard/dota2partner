package com.hodor.dota2partner.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class EnvironmentProperties {

    public static String retrieveEnvironmentProperty(String property) throws IOException {

        FileReader reader = new FileReader("env.properties");

        Properties p = new Properties();
        p.load(reader);

        return p.getProperty(property);

    }
}
