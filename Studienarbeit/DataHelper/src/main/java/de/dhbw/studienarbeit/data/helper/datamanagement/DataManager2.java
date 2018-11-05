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

public class DataManager2
{
	private static final Logger LOGGER = Logger.getLogger(DataManager.class.getName());

	private final Timer queueTimer = new Timer();
	private final List<Timer> requestTimers = new ArrayList<>();

	private final Queue<DataModel2> waitingForUpdate = new LinkedBlockingQueue<>();

	public DataManager2(final List<ApiKey> apiKeys)
	{
		addApiKey(apiKeys);
	}

	public void addApiKey(final ApiKey apiKey)
	{
		final Timer timer = new Timer();
		timer.schedule(schedulerTimerTask(apiKey), new Date(), apiKey.delayBetweenRequests());
		requestTimers.add(timer);
	}

	public void addApiKey(final List<ApiKey> apiKeys)
	{
		apiKeys.forEach(this::addApiKey);
	}

	public void add(final DataModel2 model)
	{
		readyToUpdate(model);
	}

	public void add(final List<? extends DataModel2> models)
	{
		models.forEach(this::add);
	}

	private TimerTask schedulerTimerTask(final ApiKey apiKey)
	{
		return new MyTimerTask(() -> firstWaitingDataModel().ifPresent(d -> updateAndSaveAndSchedule(d, apiKey)));
	}

	private Optional<DataModel2> firstWaitingDataModel()
	{
		return Optional.ofNullable(waitingForUpdate.poll());
	}

	private void readyToUpdate(DataModel2 model)
	{
		waitingForUpdate.add(model);
	}

	private void scheduleUpdate(DataModel2 model)
	{
		final Date now = new Date();
		final boolean timeIsOver = model.nextUpdate().before(now);

		if (timeIsOver)
		{
			readyToUpdate(model);
		}
		else
		{
			queueTimer.schedule(updateTimerTask(model), model.nextUpdate());
		}
	}

	private TimerTask updateTimerTask(final DataModel2 model)
	{
		return new MyTimerTask(() -> readyToUpdate(model));
	}

	private void updateAndSaveAndSchedule(final DataModel2 model, final ApiKey apiKey)
	{
		try
		{
			model.updateAndSaveData(apiKey);
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update " + model.toString(), e);
		}

		scheduleUpdate(model);
	}

	public void stop()
	{
		queueTimer.cancel();
		requestTimers.forEach(Timer::cancel);
	}
}
