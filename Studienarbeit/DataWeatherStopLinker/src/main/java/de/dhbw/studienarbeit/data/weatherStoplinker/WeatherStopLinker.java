package de.dhbw.studienarbeit.data.weatherStoplinker;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.helper.logging.LogLevelHelper;
import de.dhbw.studienarbeit.data.reader.data.count.CountStops;
import de.dhbw.studienarbeit.data.reader.data.count.CountStopsDB;

public class WeatherStopLinker
{
	private static final Logger LOGGER = Logger.getLogger(WeatherStopLinker.class.getName());

	public static void main(String[] args)
	{
		LogLevelHelper.setLogLevel(Level.FINEST);
		new WeatherStopLinker(new CountStopsDB()).link();
	}

	private final CountStops countStops;

	private WeatherStopLinker(CountStops countStops)
	{
		this.countStops = countStops;
	}

	private void link()
	{
		final long count = countStops.count().getValue();

		LOGGER.log(Level.INFO, "Linker will count until: " + count);
		linkForeward(1);
	}

	private void linkForeward(long start)
	{
		final long count = countStops.count().getValue();

		for (long i = start; i < count; i++)
		{
			link(i);
		}

		new Timer().schedule(new MyTimerTask(() -> linkForeward(count)), 60 * 60 * 1000l);
	}

	private void link(long idx)
	{
		new StopWeather(idx).save();
	}
}
