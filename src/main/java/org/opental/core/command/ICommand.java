package org.opental.core.command;

import org.opental.core.model.CommandType;
import org.opental.core.model.Response;

/**
 * interface describing a command
 */
public interface ICommand {
	
	/**
	 * type of command
	 * @return CommandType
	 */
	CommandType getType();
	
	/**
	 * method to execute the step
	 * @param aDriver specific driver
	 * @return Response
	 */
	Response executeImpl(Object aDriver);

}
