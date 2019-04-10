package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDB;

public class DelayCloudsDB extends DelayWeatherDB<Clouds>
{
	private static final String FIELD = "ROUND(clouds, 0)";
	private static final String NAME = "rounded";

	@Override
	protected Clouds getElement(ResultSet result) throws SQLException
	{
		return new Clouds(result.getInt(NAME));
	}

	@Override
	protected String getSQL()
	{
		return buildSQL(FIELD, NAME);
	}
}
