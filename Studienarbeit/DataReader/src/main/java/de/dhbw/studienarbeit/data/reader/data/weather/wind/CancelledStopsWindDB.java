package de.dhbw.studienarbeit.data.reader.data.weather.wind;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.reader.data.weather.CancelledStopsDB;

public class CancelledStopsWindDB extends CancelledStopsDB<Wind>
{
	private static final String FIELD = "ROUND(wind, 0)";
	private static final String NAME = "rounded";

	@Override
	protected String getSQL()
	{
		return buildSQLCountCancelledStops(FIELD, NAME);
	}

	@Override
	protected Wind getElement(ResultSet result) throws SQLException
	{
		return new Wind(result.getInt(NAME));
	}
}
