package de.dhbw.studienarbeit.data.weather;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.DataManager;
import de.dhbw.studienarbeit.data.helper.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.Settings;
import de.dhbw.studienarbeit.data.helper.Station;

public class DataWeatherApp
{
	private static final Logger LOGGER = Logger.getLogger(DataWeatherApp.class.getName());

	public static void main(String[] args) throws SQLException, ReflectiveOperationException
	{
		List<Station> testStations = new ArrayList<>();
		testStations.add(new Station("de:test:Karlsruhe", 49.01, 8.4));
		testStations.add(new Station("de:test:Berlin", 52.521918, 13.413215));
		new DataWeatherApp().startDataCollection(testStations);
	}

	public void startDataCollection(final List<Station> stations) throws SQLException, ReflectiveOperationException
	{
		final int apiKeys = Settings.getInstance().getDataWeatherApiKeys().size();
		final List<DataManager> managers = new ArrayList<>();
		final int updatesPerMinute = 58; // Maximum of allowed requests - 2 Requests for tests

		for (int i = 0; i < apiKeys; i++)
		{
			// generate one Manager for each API-Key
			managers.add(new DataManager(new DatabaseSaver(), updatesPerMinute));
		}

		for (int i = 0; i < stations.size(); i++)
		{
			try
			{
				final Station s = stations.get(i);
				final int idxManager = i % apiKeys;
				managers.get(idxManager).add(new Weather(s.getStationID(), s.getLat(), s.getLon(),
						Settings.getInstance().getDataWeatherApiKeys().get(idxManager)));
			}
			catch (IOException e)
			{
				LOGGER.log(Level.WARNING, "Not able to get Data.", e);
			}
		}
		LOGGER.log(Level.INFO, "Stations converted into Weather.");
	}
}
