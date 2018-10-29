package de.dhbw.studienarbeit.data.weather;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.ApiKey;
import de.dhbw.studienarbeit.data.helper.DataManager;
import de.dhbw.studienarbeit.data.helper.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.Settings;
import de.dhbw.studienarbeit.data.helper.StationDB;

public class DataWeatherApp
{
	private static final Logger LOGGER = Logger.getLogger(DataWeatherApp.class.getName());

	public static void main(String[] args) throws SQLException, ReflectiveOperationException
	{
		List<StationDB> testStations = new ArrayList<>();
		testStations.add(new StationDB("de:test:Karlsruhe", 49.01, 8.4));
		testStations.add(new StationDB("de:test:Berlin", 52.521918, 13.413215));
		new DataWeatherApp().startDataCollection(testStations);
	}

	public void startDataCollection(final List<StationDB> stations) throws SQLException, ReflectiveOperationException
	{
		final List<ApiKey> apiKeys = Settings.getInstance().getDataWeatherApiKeys();
		final DataManager manager = new DataManager(new DatabaseSaver(), apiKeys);

		stations.forEach(s -> manager.add(new Weather(s.getStationID(), s.getLat(), s.getLon())));

		LOGGER.log(Level.INFO, "Stations converted into Weather.");
	}
}
