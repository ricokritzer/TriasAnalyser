package de.dhbw.studienarbeit.web.data;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;

public class DataUpdater
{
	private static final Logger LOGGER = Logger.getLogger(DataUpdater.class.getName());
	private static final long MILLIS_PER_SECOND = 1000l;

	public static final long SECONDS = 1000l;
	public static final long MINUTES = 60 * SECONDS;
	public static final long HOURS = 60 * MINUTES;
	public static final long DAYS = 24 * HOURS;

	private DataUpdater()
	{}

	private static Date inFiveMinutes()
	{
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 5);
		return c.getTime();
	}

	public static void scheduleUpdate(Runnable what, int seconds, String whatIsGoingToBeUpdated)
	{
		new Timer(whatIsGoingToBeUpdated).schedule(new MyTimerTask(() -> runnable(what, whatIsGoingToBeUpdated)),
				inFiveMinutes(), seconds * MILLIS_PER_SECOND);
		LOGGER.log(Level.INFO, "Updates scheduled for " + whatIsGoingToBeUpdated + " every " + seconds + " seconds.");
	}

	public static void scheduleUpdate(Updateable updateable, int time, long timeRange)
	{
		final String classname = updateable.getClass().getName();
		new Timer(classname).schedule(new MyTimerTask(() -> runnable(updateable::update, classname)), inFiveMinutes(),
				time * timeRange);
		LOGGER.log(Level.INFO, "Updates scheduled for " + classname + " every " + time * timeRange + " ms.");
	}

	private static void runnable(Runnable what, String whatIsGoingToBeUpdated)
	{
		LOGGER.log(Level.INFO, "Started updating " + whatIsGoingToBeUpdated);
		what.run();
		LOGGER.log(Level.INFO, whatIsGoingToBeUpdated + " updated.");
	}
}
