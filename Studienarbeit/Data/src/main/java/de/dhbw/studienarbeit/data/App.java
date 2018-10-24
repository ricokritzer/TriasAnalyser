package de.dhbw.studienarbeit.data;

import java.sql.SQLException;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.DatabaseReader;
import de.dhbw.studienarbeit.data.helper.Station;
import de.dhbw.studienarbeit.data.weather.DataWeatherApp;

public class App
{
	public static void main(String[] args) throws SQLException, ReflectiveOperationException
	{
		final DatabaseReader reader = new DatabaseReader();
		final String sql = "SELECT * FROM Station";
		final List<Station> stations = reader.readDatabase(sql);
		new DataWeatherApp().startDataCollection(stations);
	}
}
