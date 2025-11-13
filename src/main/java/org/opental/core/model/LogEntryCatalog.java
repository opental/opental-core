package org.opental.core.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * This is a log entry
 */
public class LogEntryCatalog {
	private final SimpleStringProperty entry;

	/**
	 * construct a log entry
	 * @param anEntry logline
	 */
	public LogEntryCatalog(String anEntry) {
		this.entry = new SimpleStringProperty(anEntry);
	}

	/**
	 * get an entry
	 * @return entry property
	 */
	public String getEntry() {
		return entry.get();
	}
}
