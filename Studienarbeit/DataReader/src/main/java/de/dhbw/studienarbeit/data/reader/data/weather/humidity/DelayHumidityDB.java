package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDB;

public class DelayHumidityDB extends DelayWeatherDB<Humidity>
{
	private static final String FIELD = "ROUND(humidity, 0)";
	private static final String NAME = "rounded";

	@Override
	protected Humidity getElement(ResultSet result) throws SQLException
	{
		return new Humidity(result.getInt(NAME));
	}

	@Override
	protected String getSQL()
	{
		return buildSQL(FIELD, NAME);
	}
}
