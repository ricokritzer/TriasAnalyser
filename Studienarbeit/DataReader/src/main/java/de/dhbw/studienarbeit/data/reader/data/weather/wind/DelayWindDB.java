package de.dhbw.studienarbeit.data.reader.data.weather.wind;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayDB;

public class DelayWindDB extends DelayDB<Wind>
{
	private static final String FIELD = "Round(wind,0)";
	private static final String NAME = "rounded";

	@Override
	protected Wind getElement(ResultSet result) throws SQLException
	{
		return new Wind(result.getInt(NAME));
	}

	@Override
	protected String getSQL()
	{
		return buildSQL(FIELD, NAME);
	}
}
