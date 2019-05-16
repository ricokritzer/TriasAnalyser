package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.reader.data.weather.CancelledStopsDB;

public class CancelledStopsTemperatureDB extends CancelledStopsDB<Temperature>
{
	private static final String FIELD = "ROUND(temp, 0)";
	private static final String NAME = "rounded";

	@Override
	protected String getSQL()
	{
		return buildSQLCountCancelledStops(FIELD, NAME);
	}

	@Override
	protected Temperature getElement(ResultSet result) throws SQLException
	{
		return new Temperature(result.getInt(NAME));
	}
}
