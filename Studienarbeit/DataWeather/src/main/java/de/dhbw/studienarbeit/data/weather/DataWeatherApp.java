package de.dhbw.studienarbeit.data.weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.Settings;
import de.dhbw.studienarbeit.data.helper.database.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.database.StationDB;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataManager;

public class DataWeatherApp
{
	private static final Logger LOGGER = Logger.getLogger(DataWeatherApp.class.getName());

	public static void main(String[] args)
	{
		List<StationDB> testStations = new ArrayList<>();
		testStations.add(new StationDB("de:test:Karlsruhe", "Karlsruhe", 49.01, 8.40, "Stadt"));
		testStations.add(new StationDB("de:test:Berlin", "Berlin", 52.521918, 13.413215, "Stadt"));
		new DataWeatherApp().startDataCollection(testStations);
	}

	public void startDataCollection(final List<StationDB> stations)
	{
		try
		{
			final List<ApiKey> apiKeys = Settings.getInstance().getApiKeys("weather");
			final DataManager manager = new DataManager(new DatabaseSaver(), apiKeys);
			final List<Weather> weather = convertToWeather(stations);

			manager.add(weather);

			LOGGER.log(Level.INFO, stations.size() + " stations converted into " + weather.size() + " Weather.");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to start data collection.", e);
		}
	}

	protected List<Weather> convertToWeather(List<StationDB> stations)
	{
		final List<Weather> weather = new ArrayList<>();

		stations.forEach(s -> {
			final Weather w = new Weather(s.getLat(), s.getLon());
			if (!weather.contains(w))
			{
				weather.add(w);
			}
		});

		return weather;
	}
}
