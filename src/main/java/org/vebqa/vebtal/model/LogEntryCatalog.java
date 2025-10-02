package org.vebqa.vebtal.model;

import javafx.beans.property.SimpleStringProperty;

public class LogEntryCatalog {
	private final SimpleStringProperty entry;

	public LogEntryCatalog(String anEntry) {
		this.entry = new SimpleStringProperty(anEntry);
	}

	public String getEntry() {
		return entry.get();
	}
}
