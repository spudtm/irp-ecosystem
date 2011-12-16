package uk.ac.dotrural.irp.ecosystem.queries.observation;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class QueryReader {
	private static final String BUNDLE_NAME = "uk.ac.dotrural.irp.ecosystem.queries.observation.queries";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private QueryReader() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
