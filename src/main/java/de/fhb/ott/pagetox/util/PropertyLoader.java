package de.fhb.ott.pagetox.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author Christoph Ott
 */
public class PropertyLoader {

	/**
	 * load the SystemProperty
	 *
	 * @param path to property file
	 * @return Properties
	 * @throws IOException
	 */
	public static Properties loadProperty(String path) throws IOException {
		Properties props = new Properties();

		ClassLoader loader = PropertyLoader.class.getClassLoader();
		InputStream in = loader.getResourceAsStream(path);
		props.load(in);
		return props;
	}

	public static void loadPropertyToSystemProp(String path) throws IOException {
		Properties properties = loadProperty(path);
		for (Object key : properties.keySet()) {
			System.setProperty(key.toString(), properties.get(key).toString());
		}
	}

	public static List<String> loadPropertiesAsList(String path) throws IOException {
		List<String> list = new ArrayList<>();
		ClassLoader loader = PropertyLoader.class.getClassLoader();
		URL in = loader.getResource(path);
		BufferedReader read;
		if (in != null) {
			read = new BufferedReader(new FileReader(in.getFile()));
			String line;
			while ((line = read.readLine()) != null) {
				list.addAll(Arrays.asList(line.split(" ")));
			}
		}
		return list;
	}
}
