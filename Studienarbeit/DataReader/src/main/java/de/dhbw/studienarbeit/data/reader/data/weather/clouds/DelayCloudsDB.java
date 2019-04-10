package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayDB;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;

public class DelayCloudsDB extends DelayDB<Clouds>
{
	private static final String FIELD = "ROUND(clouds, 0)";
	private static final String NAME = "rounded";

	public final List<DelayData<Clouds>> getDelays() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQL(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Clouds getElement(ResultSet result) throws SQLException
	{
		return new Clouds(result.getInt(NAME));
	}
}
