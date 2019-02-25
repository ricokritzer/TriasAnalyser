package de.dhbw.studienarbeit.data.weatherStoplinker;

import java.util.Timer;

import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.reader.database.Count;

public class WeatherStopLinker
{
	private static final int MAXIMUM_CONNECTIONS = 3;

	public static void main(String[] args)
	{
		for (int i = 1; i <= MAXIMUM_CONNECTIONS; i++)
		{
			final int start = i;
			new Thread(() -> new WeatherStopLinker().linkWeatherAndStops(start)).start();
		}
	}

	private void linkWeatherAndStops(final int start)
	{
		final Count stopCount = Count.countStops();

		int i = start;

		for (; i <= stopCount.getValue(); i += MAXIMUM_CONNECTIONS)
		{
			new StopWeather(i).save();
		}

		final int newStart = i;
		new Timer().schedule(new MyTimerTask(() -> linkWeatherAndStops(newStart)), 60 * 60 * 1000l);
	}
}
