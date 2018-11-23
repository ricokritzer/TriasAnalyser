package de.dhbw.studienarbeit.data.helper.datamanagement;

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

public class DataManager
{
	private static final Logger LOGGER = Logger.getLogger(DataManager.class.getName());

	private final Timer queueTimer = new Timer();
	private final List<Timer> requestTimers = new ArrayList<>();
	private final String name;

	private final Queue<Manageable> waitingForUpdate = new LinkedBlockingQueue<>();
	private final Object waitingForUpdateSynchronObject = new Object();
	private boolean serverAvailable = true;
	private final Object serverAvailableSynchronObject = new Object();

	public DataManager(String name, final List<ApiKey> apiKeys)
	{
		this.name = name;
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

	public void add(final Manageable model)
	{
		readyToUpdate(model);
	}

	public void add(final List<? extends Manageable> models)
	{
		models.forEach(this::add);
	}

	private TimerTask schedulerTimerTask(final ApiKey apiKey)
	{
		return new MyTimerTask(
				() -> new Thread(() -> firstWaitingDataModel().ifPresent(d -> updateSaveAndSchedule(d, apiKey)))
						.start());
	}

	private Optional<Manageable> firstWaitingDataModel()
	{
		synchronized (serverAvailableSynchronObject)
		{
			if (!serverAvailable)
			{
				return Optional.empty();
			}
		}
		synchronized (waitingForUpdateSynchronObject)
		{
			return Optional.ofNullable(waitingForUpdate.poll());
		}
	}

	private void readyToUpdate(Manageable model)
	{
		synchronized (waitingForUpdateSynchronObject)
		{
			waitingForUpdate.add(model);
		}
	}

	private void scheduleUpdate(Manageable model)
	{
		queueTimer.schedule(updateTimerTask(model), Optional.ofNullable(model.nextUpdate()).orElse(new Date()));
	}

	private TimerTask updateTimerTask(final Manageable model)
	{
		return new MyTimerTask(() -> readyToUpdate(model));
	}

	private void updateSaveAndSchedule(final Manageable model, final ApiKey apiKey)
	{
		try
		{
			model.updateAndSaveData(apiKey);
		}
		catch (UpdateException e)
		{
			LOGGER.log(Level.WARNING, "Update does not succeed: " + model.toString(), e);
		}
		catch (ServerNotAvailableException e)
		{
			disableUpdate();
			LOGGER.log(Level.WARNING, "Server not available, unable to update: " + model.toString(), e);
		}

		scheduleUpdate(model);
	}

	private void disableUpdate()
	{
		synchronized (serverAvailableSynchronObject)
		{
			if (serverAvailable)
			{
				serverAvailable = false;
				final long MILLIS_PER_MINUTE = 60000l;
				new Timer().schedule(new MyTimerTask(() -> serverAvailable = true), 5 * MILLIS_PER_MINUTE);
			}
		}
	}

	public void stop()
	{
		queueTimer.cancel();
		requestTimers.forEach(Timer::cancel);
		LOGGER.log(Level.INFO, "Timer stopped.");
	}

	public WaitingQueueCount getWaitingQueueCount()
	{
		synchronized (waitingForUpdateSynchronObject)
		{
			return new WaitingQueueCount(name, waitingForUpdate.size());
		}
	}
}
