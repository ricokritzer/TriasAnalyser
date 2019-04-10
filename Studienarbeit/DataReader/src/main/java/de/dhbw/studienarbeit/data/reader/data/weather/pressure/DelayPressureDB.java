package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayDB;

public class DelayPressureDB extends DelayDB<Pressure>
{
	private static final String FIELD = "Round(pressure,0)";
	private static final String NAME = "rounded";

	public final List<DelayData<Pressure>> getDelays() throws IOException
	{
		final String sql = buildSQL(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Pressure getElement(ResultSet result) throws SQLException
	{
		return new Pressure(result.getInt(NAME));
	}
}
