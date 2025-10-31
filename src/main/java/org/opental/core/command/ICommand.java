package org.opental.core.command;

import org.opental.core.model.CommandType;
import org.opental.core.model.Response;

public interface ICommand {
	
	CommandType getType();
	
	Response executeImpl(Object aDriver);

}
