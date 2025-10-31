package org.opental.core;

import org.opental.core.model.Command;
import org.opental.core.model.Response;

public interface TestAdaptionResource {

	Response execute(Command aCmd);
	
	String getCommandClassName(Command aCmd);
	
	Long getDuration();
	
	void setStart();
	
	void setFinished();
}