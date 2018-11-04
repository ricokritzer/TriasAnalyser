package de.dhbw.studienarbeit.data.weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.database.model.StationDB;
import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableApi;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataManager;

public class DataWeatherApp
{
	public static void main(String[] args) throws IOException
	{
		List<StationDB> testStations = new ArrayList<>();
		testStations.add(new StationDB("de:test:Karlsruhe", "Karlsruhe", 49.01, 8.40, "Stadt", true));
		testStations.add(new StationDB("de:test:Berlin", "Berlin", 52.521918, 13.413215, "Stadt", true));
		new DataWeatherApp().startDataCollection(testStations);
	}

	public void startDataCollection(final List<StationDB> stations) throws IOException
	{
		final List<ApiKey> apiKeys = new DatabaseTableApi().selectApisByName("weather");
		final DataManager manager = new DataManager(new DatabaseSaver(), apiKeys);
		final List<Weather> weather = convertToWeather(stations);
		manager.add(weather);
	}

	protected List<Weather> convertToWeather(List<StationDB> stations)
	{
		final List<Weather> weather = new ArrayList<>();
		stations.forEach(s -> {
			final Weather w = convertToWeather(s);
			if (!weather.contains(w))
			{
				weather.add(w);
			}
		});
		return weather;
	}

	private Weather convertToWeather(final StationDB station)
	{
		return new Weather(station.getLat(), station.getLon());
	}
}
