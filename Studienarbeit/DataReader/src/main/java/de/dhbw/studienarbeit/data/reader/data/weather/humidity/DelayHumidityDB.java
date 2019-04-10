package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayDB;

public class DelayHumidityDB extends DelayDB<Humidity>
{
	private static final String FIELD = "ROUND(humidity, 0)";
	private static final String NAME = "rounded";

	public final List<DelayData<Humidity>> getDelays() throws IOException
	{
		final String sql = buildSQL(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Humidity getElement(ResultSet result) throws SQLException
	{
		return new Humidity(result.getInt(NAME));
	}
}
