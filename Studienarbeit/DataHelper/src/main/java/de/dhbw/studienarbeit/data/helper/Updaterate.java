package de.dhbw.studienarbeit.data.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

public enum Updaterate
{
	UPDATE_EVERY_MINUTE(60 * 100), UPDATE_EVERY_FIVE_MINUTES(5 * 60 * 100);

	private static final Logger LOGGER = Logger.getLogger(Updaterate.class.getName());
	private long waitingTime;

	private Updaterate(long waitingTime)
	{
		this.waitingTime = waitingTime;
	}

	public void waitTime()
	{
		try
		{
			Thread.sleep(waitingTime);
		}
		catch (InterruptedException e)
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
}
