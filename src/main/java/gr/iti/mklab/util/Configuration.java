package gr.iti.mklab.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;

public class Configuration {
	public static String RESOURCES_PATH;
	public static String STOP_CLICKBAIT_PYTHON;

    public static void load(String file) throws ConfigurationException, IOException {
    	Properties prop = new Properties();
    	InputStream input = null;
    	System.out.println(" properties file " + file);
    	input = new FileInputStream(file);
		// load a properties file
		prop.load(input);    	
        RESOURCES_PATH = prop.getProperty("resources");
        STOP_CLICKBAIT_PYTHON = prop.getProperty("stopClickbaitPython");
    }
}
