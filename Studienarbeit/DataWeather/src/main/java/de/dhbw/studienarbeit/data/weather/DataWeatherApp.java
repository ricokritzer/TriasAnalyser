package de.dhbw.studienarbeit.data.weather;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.DataManager;
import de.dhbw.studienarbeit.data.helper.DataModel;
import de.dhbw.studienarbeit.data.helper.DatabaseSaver;
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

	public void startDataCollection(List<Station> stations) throws SQLException, ReflectiveOperationException
	{
		final List<DataModel> data = new ArrayList<>();
		stations.forEach(s -> {
			try
			{
				data.add(new Weather(s.getStationID(), s.getLat(), s.getLon()));
			}
			catch (IOException e)
			{
				LOGGER.log(Level.WARNING, "Not able to get Data.", e);
			}
		});
		LOGGER.log(Level.INFO, "Stations converted into Weather.");
		final int updatesPerMinute = 60; // Maximum of Requests
		new DataManager(data, new DatabaseSaver(), updatesPerMinute);
	}
}
