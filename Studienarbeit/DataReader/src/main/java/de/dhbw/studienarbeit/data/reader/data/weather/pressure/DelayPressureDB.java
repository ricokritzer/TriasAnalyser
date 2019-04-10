package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayDB;

public class DelayPressureDB extends DelayDB<Pressure>
{
	private static final String FIELD = "Round(pressure,0)";
	private static final String NAME = "rounded";

	@Override
	protected Pressure getElement(ResultSet result) throws SQLException
	{
		return new Pressure(result.getInt(NAME));
	}

	@Override
	protected String getSQL()
	{
		return buildSQL(FIELD, NAME);
	}
}
