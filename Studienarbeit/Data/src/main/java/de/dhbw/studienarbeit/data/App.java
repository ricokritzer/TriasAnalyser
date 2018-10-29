package de.dhbw.studienarbeit.data;

import java.sql.SQLException;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.DatabaseReader;
import de.dhbw.studienarbeit.data.helper.StationDB;
import de.dhbw.studienarbeit.data.trias.DataTriasApp;
import de.dhbw.studienarbeit.data.weather.DataWeatherApp;

public class App
{
	public static void main(String[] args) throws SQLException, ReflectiveOperationException
	{
		final DatabaseReader reader = new DatabaseReader();
		final List<StationDB> stations = reader.readStations();
		new DataWeatherApp().startDataCollection(stations);
		new DataTriasApp().startDataCollection(stations);
	}
}
