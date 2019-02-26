package de.dhbw.studienarbeit.data.weatherStoplinker;

import java.util.Timer;

import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.reader.database.Count;

public class WeatherStopLinker
{
	public static void main(String[] args)
	{
		new WeatherStopLinker().link();
	}

	private void link()
	{
		final long end = Count.countStops().getValue();

		for (long i = end; i > 0; i--)
		{
			new StopWeather(i);
		}

		new Timer().schedule(new MyTimerTask(this::link), 60 * 60 * 1000l);
	}
}
