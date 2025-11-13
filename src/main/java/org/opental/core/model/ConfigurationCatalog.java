package org.opental.core.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Our catalog, holding the configuration
 */
public class ConfigurationCatalog {
	private final SimpleStringProperty key;
	private final SimpleStringProperty value;

	/**
	 * Construct an entry with key and value
	 * @param aKey
	 * @param aValue
	 */
	public ConfigurationCatalog(String aKey, String aValue) {
		this.key = new SimpleStringProperty(aKey);
		this.value = new SimpleStringProperty(aValue);
	}

	/**
	 * get the key for this entry
	 * @return key
	 */
	public String getKey() {
		return key.get();
	}

	/**
	 * get the value for this entry
	 * @return value
	 */
	public String getValue() {
		return value.get();
	}
}
