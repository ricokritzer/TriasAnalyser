package de.dhbw.studienarbeit.data.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

public enum Updaterate
{
	UPDATE_EVERY_MINUTE(minutes(1)), UPDATE_EVERY_FIVE_MINUTES(minutes(5));

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

	private static long minutes(int minutes)
	{
		return minutes * 60 * 1000l;
	}
}
