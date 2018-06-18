package com.mama.dandy.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	public static Properties readProperties(String fileName)
			throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(PropertiesUtils.class.getClassLoader()
				.getResourceAsStream(fileName));
		return properties;
	}
}
