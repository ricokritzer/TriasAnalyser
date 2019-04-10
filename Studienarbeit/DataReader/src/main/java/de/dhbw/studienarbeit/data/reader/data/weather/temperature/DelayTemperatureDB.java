package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDB;

public class DelayTemperatureDB extends DelayWeatherDB<Temperature>
{
	private static final String FIELD = "Round(temp, 0)";
	private static final String NAME = "rounded";

	@Override
	protected Temperature getElement(ResultSet result) throws SQLException
	{
		return new Temperature(result.getInt(NAME));
	}

	@Override
	protected String getSQL()
	{
		return buildSQL(FIELD, NAME);
	}
}
