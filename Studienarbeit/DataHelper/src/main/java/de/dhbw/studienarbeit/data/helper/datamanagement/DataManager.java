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

import de.dhbw.studienarbeit.data.helper.database.saver.Saver;

/**
 *
 * @deprecated Use DataManager2.
 */

@Deprecated
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

	public void add(final DataModel model)
	{
		readyToUpdate(model);
	}

	public void add(final List<? extends DataModel> models)
	{
		models.forEach(this::add);
	}

	private TimerTask schedulerTimerTask(final ApiKey apiKey)
	{
		return new MyTimerTask(() -> firstWaitingDataModel().ifPresent(d -> updateAndSaveAndSchedule(d, apiKey)));
	}

	private Optional<DataModel> firstWaitingDataModel()
	{
		return Optional.ofNullable(waitingForUpdate.poll());
	}

	private void readyToUpdate(DataModel model)
	{
		waitingForUpdate.add(model);
	}

	private void scheduleUpdate(DataModel model)
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

	private TimerTask updateTimerTask(final DataModel model)
	{
		return new MyTimerTask(() -> readyToUpdate(model));
	}

	private void updateAndSaveAndSchedule(final DataModel model, final ApiKey apiKey)
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

		scheduleUpdate(model);
	}

	public void stop()
	{
		queueTimer.cancel();
		requestTimers.forEach(Timer::cancel);
	}
}
