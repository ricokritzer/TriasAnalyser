package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.reader.data.weather.CancelledStopsDB;

public class CancelledStopsPressureDB extends CancelledStopsDB<Pressure>
{
	private static final String FIELD = "ROUND(pressure, 0)";
	private static final String NAME = "rounded";

	@Override
	protected String getSQL()
	{
		return buildSQLCountCancelledStops(FIELD, NAME);
	}

	@Override
	protected Pressure getElement(ResultSet result) throws SQLException
	{
		return new Pressure(result.getInt(NAME));
	}
}
