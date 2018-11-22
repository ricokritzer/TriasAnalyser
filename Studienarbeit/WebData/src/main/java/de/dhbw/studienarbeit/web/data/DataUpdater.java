package de.dhbw.studienarbeit.web.data;

import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;

public class DataUpdater
{
	private static final Logger LOGGER = Logger.getLogger(DataUpdater.class.getName());
	private static final long MILLIS_PER_SECOND = 1000l;

	private DataUpdater()
	{}

	public static void scheduleUpdate(Runnable what, int seconds, String whatIsGoingToBeUpdated)
	{
		new Timer().schedule(new MyTimerTask(what), new Date(), seconds * MILLIS_PER_SECOND);
		LOGGER.log(Level.INFO, "Updates scheduled for " + whatIsGoingToBeUpdated + " every " + seconds + " seconds.");
	}
}
