package de.dhbw.studienarbeit.web.data.update;

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

	private static final int MAXIMUM_PARALLEL = 3;

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
		boolean run = true;

		while (run)
		{
			Optional<Updateable> opt;
			synchronized (waitingForUpdate)
			{
				opt = Optional.ofNullable(waitingForUpdate.poll());
			}

			opt.ifPresent(this::update);
			run = opt.isPresent();
		}

		new Timer().schedule(new MyTimerTask(this::updateAsync), 60 * 1000l);
	}

	private void update(Updateable updateable)
	{
		final String classname = updateable.getClass().getName();

		LOGGER.log(Level.FINEST, "Started updating " + classname);
		final Date start = new Date();

		updateable.update();

		final Date end = new Date();
		final long time = end.getTime() - start.getTime();
		LOGGER.log(Level.INFO, classname + " updated after " + time + "ms.");
	}

	public void updateEvery(int time, long timeRange, Updateable updateable)
	{
		final String classname = updateable.getClass().getName();
		new Timer(classname).schedule(new MyTimerTask(() -> addUpdateable(updateable)), new Date(), time * timeRange);
		LOGGER.log(Level.INFO, "Updates scheduled for " + classname + " every " + time * timeRange + " ms.");
	}

	private void addUpdateable(Updateable updateable)
	{
		synchronized (getInstance().waitingForUpdate)
		{
			getInstance().waitingForUpdate.add(updateable);
		}
	}
}
