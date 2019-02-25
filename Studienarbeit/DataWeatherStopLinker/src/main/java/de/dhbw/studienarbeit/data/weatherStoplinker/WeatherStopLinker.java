package de.dhbw.studienarbeit.data.weatherStoplinker;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.reader.database.Count;

public class WeatherStopLinker
{
	private static final Logger LOGGER = Logger.getLogger(WeatherStopLinker.class.getName());

	private long idx = 1;

	public static void main(String[] args)
	{
		new WeatherStopLinker().linkWeatherAndStops();
	}

	private void linkWeatherAndStops()
	{
		final Count stopCount = Count.countStops();
		while (idx <= stopCount.getValue())
		{
			DatabaseSaver.saveData(new StopWeather(idx));
			LOGGER.log(Level.INFO, "Stop with index " + idx + " linked to weather.");
			idx++;
		}

		new Timer().schedule(new MyTimerTask(this::linkWeatherAndStops), 60 * 60 * 1000l);
	}
}
