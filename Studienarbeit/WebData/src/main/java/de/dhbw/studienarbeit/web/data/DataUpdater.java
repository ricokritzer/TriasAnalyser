package de.dhbw.studienarbeit.web.data;

import java.util.Date;
import java.util.Optional;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;

public class DataUpdater
{
	private static final Logger LOGGER = Logger.getLogger(DataUpdater.class.getName());

	private static final int MAXIMUM_PARALLEL = 1;

	public static final long SECONDS = 1000l;
	public static final long MINUTES = 60 * SECONDS;
	public static final long HOURS = 60 * MINUTES;
	public static final long DAYS = 24 * HOURS;

	private final Queue<Updateable> waitingForUpdate = new LinkedBlockingQueue<>();

	private static final DataUpdater INSTANCE = new DataUpdater();

	private DataUpdater()
	{
		for (int i = 0; i < MAXIMUM_PARALLEL; i++)
		{
			new Thread(this::updateAsync).start();
		}
	}

	public static DataUpdater getInstance()
	{
		return INSTANCE;
	}

	private void updateAsync()
	{
		while (true)
		{
			synchronized (waitingForUpdate)
			{
				System.out.println("jetzt");
				Optional<Updateable> opt = Optional.ofNullable(waitingForUpdate.poll());
				opt.ifPresent(this::update);
			}
		}
	}

	private void update(Updateable updateable)
	{
		final String classname = updateable.getClass().getName();
		LOGGER.log(Level.INFO, "Started updating " + classname);
		final Date start = new Date();
		updateable.update();
		final Date end = new Date();
		final long time = end.getTime() - start.getTime();
		LOGGER.log(Level.INFO, classname + " updated after " + time + "ms.");
	}

	public static void scheduleUpdate(Updateable updateable, int time, long timeRange)
	{
		final String classname = updateable.getClass().getName();

		new Timer(classname).schedule(new MyTimerTask(() -> {
			synchronized (getInstance().waitingForUpdate)
			{
				getInstance().waitingForUpdate.add(updateable);
			}
		}), new Date(), time * timeRange);

		LOGGER.log(Level.INFO, "Updates scheduled for " + classname + " every " + time * timeRange + " ms.");
	}
}
