package de.dhbw.studienarbeit.data.weatherStoplinker;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.reader.database.Count;

public class WeatherStopLinker
{
	private static final Logger LOGGER = Logger.getLogger(WeatherStopLinker.class.getName());

	public static void main(String[] args)
	{
		new WeatherStopLinker().link();
	}

	private void link()
	{
		final long end = Count.countStops().getValue();

		LOGGER.log(Level.INFO, "Linker starts with: " + end);

		for (long i = end; i > 0; i--)
		{
			new StopWeather(i).save();
		}

		new Timer().schedule(new MyTimerTask(this::link), 60 * 60 * 1000l);
	}
}
