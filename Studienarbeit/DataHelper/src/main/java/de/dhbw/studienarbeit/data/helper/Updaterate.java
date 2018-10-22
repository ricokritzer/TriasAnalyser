package de.dhbw.studienarbeit.data.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

public enum Updaterate
{
	UPDATE_EVERY_MINUTE(minutes(1)), UPDATE_EVERY_FIVE_MINUTES(minutes(5));

	private static final Logger LOGGER = Logger.getLogger(Updaterate.class.getName());
	private int waitingTime;

	private Updaterate(int waitingTime)
	{
		this.waitingTime = waitingTime;
	}

	@Deprecated
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

	public int getDelay()
	{
		return waitingTime;
	}

	private static int minutes(int min)
	{
		return 1000 * 60 * min;
	}
}
