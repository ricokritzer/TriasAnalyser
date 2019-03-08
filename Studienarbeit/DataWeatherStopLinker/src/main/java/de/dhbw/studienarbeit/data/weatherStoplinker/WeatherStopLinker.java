package de.dhbw.studienarbeit.data.weatherStoplinker;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.reader.data.count.CountStops;
import de.dhbw.studienarbeit.data.reader.data.count.CountStopsDB;

public class WeatherStopLinker
{
	private static final Logger LOGGER = Logger.getLogger(WeatherStopLinker.class.getName());

	public static void main(String[] args)
	{
		new WeatherStopLinker(new CountStopsDB()).link();
	}

	private final CountStops countStops;

	private WeatherStopLinker(CountStops countStops)
	{
		this.countStops = countStops;
	}

	private void link()
	{
		final long count = countStops.countStops().getValue();
		final long consiciousError = 1000;

		LOGGER.log(Level.INFO, "Linker starts with: " + count);
		linkForeward(count - consiciousError);
		linkReverse(count - consiciousError);
	}

	private void linkForeward(long start)
	{
		final long count = countStops.countStops().getValue();

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
