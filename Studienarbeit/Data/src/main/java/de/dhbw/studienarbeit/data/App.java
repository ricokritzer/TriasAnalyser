package de.dhbw.studienarbeit.data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.database.DatabaseReader;
import de.dhbw.studienarbeit.data.helper.database.StationDB;
import de.dhbw.studienarbeit.data.trias.DataTriasApp;
import de.dhbw.studienarbeit.data.weather.DataWeatherApp;

public class App
{
	public static void main(String[] args) throws IOException, SQLException, ReflectiveOperationException
	{
		final DatabaseReader reader = new DatabaseReader();
		final List<StationDB> stations = reader.readStations();
		new DataWeatherApp().startDataCollection(stations);
		//new DataTriasApp().startDataCollection(stations);
	}
}
