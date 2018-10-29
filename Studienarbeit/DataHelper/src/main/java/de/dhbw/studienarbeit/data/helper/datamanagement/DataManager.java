package de.dhbw.studienarbeit.data.helper.datamanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.Saver;

public class DataManager
{
	private static final Logger LOGGER = Logger.getLogger(DataManager.class.getName());

	private final Saver saver;

	private final Timer queueTimer = new Timer();
	private final List<Timer> requestTimers = new ArrayList<>();

	private final Queue<DataModel> waitingForUpdate = new LinkedBlockingQueue<>();

	public DataManager(final Saver saver, final List<ApiKey> apiKeys)
	{
		this.saver = saver;

		for (ApiKey apiKey : apiKeys)
		{
			final Timer timer = new Timer();
			timer.scheduleAtFixedRate(schedulerTimerTask(apiKey), new Date(), apiKey.delayBetweenRequests());
			requestTimers.add(timer);
		}
	}

	public void add(final DataModel model)
	{
		readyToUpdate(model);
	}

	public void add(final List<DataModel> models)
	{
		for (DataModel dataModel : models)
		{
			add(dataModel);
		}
	}

	private TimerTask schedulerTimerTask(final ApiKey apiKey)
	{
		return new TimerTask()
		{
			@Override
			public void run()
			{
				Optional.ofNullable(waitingForUpdate.poll()).ifPresent(d -> updateAndSaveAndSchedule(d, apiKey));
			}
		};
	}

	private void readyToUpdate(DataModel model)
	{
		waitingForUpdate.add(model);
	}

	private void scheduleUpdate(DataModel model)
	{
		final Date now = new Date();

		if (now.before(model.nextUpdate()))
		{
			queueTimer.schedule(updateTimerTask(model), model.nextUpdate());
		}
		else
		{
			// time is over -> update now
			readyToUpdate(model);
		}
	}

	private TimerTask updateTimerTask(final DataModel model)
	{
		return new TimerTask()
		{
			@Override
			public void run()
			{
				readyToUpdate(model);
			}
		};
	}

	private void updateAndSaveAndSchedule(DataModel model, ApiKey apiKey)
	{
		try
		{
			model.updateData(apiKey);
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
		queueTimer.cancel();
		requestTimers.forEach(Timer::cancel);
	}
}
