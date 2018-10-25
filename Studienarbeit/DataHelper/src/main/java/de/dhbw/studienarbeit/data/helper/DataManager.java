package de.dhbw.studienarbeit.data.helper;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager
{
	private static final Logger LOGGER = Logger.getLogger(DataManager.class.getName());

	private final Timer timer;
	private final Saver saver;

	private Queue<Thread> waitingThreads = new LinkedBlockingQueue<>();
	private final Timer scheduler = new Timer();

	@Deprecated
	public DataManager(List<DataModel> models)
	{
		this(models, new TextSaver("ausgabe.txt"));
	}

	@Deprecated
	public DataManager(List<DataModel> models, Saver saver)
	{
		this(models, saver, 60);
	}

	public DataManager(List<DataModel> models, Saver saver, int requestsPerMinute)
	{
		this.timer = new Timer();
		this.saver = saver;
		final long millisBetweenRequests = 60000 / requestsPerMinute;

		for (DataModel dataModel : models)
		{
			readyToUpdate(dataModel);
		}

		scheduler.scheduleAtFixedRate(schedulerTimerTast(), new Date(), millisBetweenRequests);
	}

	private TimerTask schedulerTimerTast()
	{
		return new TimerTask()
		{
			@Override
			public void run()
			{
				Optional.ofNullable(waitingThreads.poll()).ifPresent(Thread::start);
			}
		};
	}

	private void readyToUpdate(DataModel model)
	{
		waitingThreads.add(new Thread(() -> updateAndSaveAndSchedule(model)));
	}

	private void scheduleUpdate(DataModel model)
	{
		final Date now = new Date();
		final Date updateDate = model.nextUpdate();

		if (now.before(updateDate))
		{
			TimerTask task = new TimerTask()
			{
				@Override
				public void run()
				{
					readyToUpdate(model);
				}
			};
			timer.schedule(task, model.nextUpdate());
		}
		else
		{
			// time is over -> update now
			readyToUpdate(model);
		}
	}

	private void updateAndSaveAndSchedule(DataModel model)
	{
		try
		{
			model.updateData(1);
			saver.save(model);
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update " + model.toString(), e);
		}
		finally
		{
			scheduleUpdate(model);
		}
	}

	public void stop()
	{
		timer.cancel();
	}
}
