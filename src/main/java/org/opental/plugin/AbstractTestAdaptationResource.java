package org.opental.plugin;

import java.util.Calendar;

import org.opental.core.TestAdaptionResource;
import org.opental.core.model.Command;
import org.opental.core.model.Response;

public class AbstractTestAdaptationResource implements TestAdaptionResource {

	private Long startTime;
	private Long finishTime;
	
	public AbstractTestAdaptationResource() {
		startTime = 0L;
		finishTime = 0L;
	}
	
	@Override
	public Response execute(Command aCmd) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCommandClassName(Command aCmd) {
		// Test - to be refactored
		// Command instanziieren
		// erst alles klein schreiben
		String tCmd = aCmd.getCommand().toLowerCase().trim();
		// erster Buchstabe gross
		String cmdFL = tCmd.substring(0, 1).toUpperCase(); 
		String cmdRest = tCmd.substring(1);
		return cmdFL + cmdRest;
	}

	
	@Override
	public Long getDuration() {
		return finishTime - startTime;
	}

	@Override
	public void setStart() {
		startTime = Calendar.getInstance().getTimeInMillis();
	}

	@Override
	public void setFinished() {
		finishTime = Calendar.getInstance().getTimeInMillis();
	}

}
