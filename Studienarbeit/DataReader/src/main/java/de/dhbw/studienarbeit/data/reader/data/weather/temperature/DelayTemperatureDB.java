package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayDB;

public class DelayTemperatureDB extends DelayDB<Temperature>
{
	private static final String FIELD = "Round(temp, 0)";
	private static final String NAME = "rounded";

	public final List<DelayData<Temperature>> getDelays() throws IOException
	{
		final String sql = buildSQL(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Temperature getElement(ResultSet result) throws SQLException
	{
		return new Temperature(result.getInt(NAME));
	}
}
