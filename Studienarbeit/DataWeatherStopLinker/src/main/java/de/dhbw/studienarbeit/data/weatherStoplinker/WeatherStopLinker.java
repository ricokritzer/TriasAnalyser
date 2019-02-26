package de.dhbw.studienarbeit.data.weatherStoplinker;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.reader.database.Count;

public class WeatherStopLinker
{
	private static final Logger LOGGER = Logger.getLogger(WeatherStopLinker.class.getName());
	private static final int MAXIMUM_CONNECTIONS = 5;

	public static void main(String[] args)
	{
		new WeatherStopLinker().link();
	}

	private void link()
	{
		final long start = 1;
		final long end = Count.countStops().getValue();

		LOGGER.log(Level.FINE, "Linker starts with: " + end);
		linkAsync(start, end);

		new Timer().schedule(new MyTimerTask(this::link), 60 * 60 * 1000l);
	}

	private void linkAsync(long start, long end)
	{
		long distance = end - start;
		long countPerConnection = distance / MAXIMUM_CONNECTIONS;

		final List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < MAXIMUM_CONNECTIONS; i++)
		{
			final int idx = i;
			threads.add(new Thread(() -> link(countPerConnection * idx + 1, countPerConnection * (idx + 1))));
		}
		threads.forEach(Thread::start);
		threads.forEach(this::waitFor);
	}

	private void link(long start, long end)
	{
		for (long i = end; i >= start; i--)
		{
			link(i);
		}
	}

	private void waitFor(Thread t)
	{
		try
		{
			t.join();
		}
		catch (InterruptedException e)
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}

	private void link(long idx)
	{
		if (idx > 1)
		{
			new StopWeather(idx).save();
		}
	}
}
