package de.dhbw.studienarbeit.data.weatherStoplinker;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.reader.data.count.CountDB;

public class WeatherStopLinker
{
	private static final Logger LOGGER = Logger.getLogger(WeatherStopLinker.class.getName());

	public static void main(String[] args)
	{
		new WeatherStopLinker().link();
	}

	private void link()
	{
		final long count = CountDB.countStops().getValue();
		final long consiciousError = 1000;

		LOGGER.log(Level.INFO, "Linker starts with: " + count);
		linkForeward(count - consiciousError);
		linkReverse(count - consiciousError);
	}

	private void linkForeward(long start)
	{
		final long count = CountDB.countStops().getValue();

		for (long i = start; i < count; i++)
		{
			link(i);
		}

		new Timer().schedule(new MyTimerTask(() -> linkForeward(count)), 60 * 60 * 1000l);
	}

	private void linkReverse(long start)
	{
		linkAsync(1, start);
	}

	private void linkAsync(long start, long end)
	{
		new Thread(() -> link(start, end)).start();
	}

	private void link(long start, long end)
	{
		for (long i = end; i >= start; i--)
		{
			link(i);
		}
	}

	private void link(long idx)
	{
		new StopWeather(idx).save();
	}
}
