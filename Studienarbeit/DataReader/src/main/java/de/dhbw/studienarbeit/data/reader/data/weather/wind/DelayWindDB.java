package de.dhbw.studienarbeit.data.reader.data.weather.wind;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayDB;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;

public class DelayWindDB extends DelayDB<Wind>
{
	private static final String FIELD = "Round(wind,0)";
	private static final String NAME = "rounded";

	public final List<DelayData<Wind>> getDelays() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQL(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Wind getElement(ResultSet result) throws SQLException
	{
		return new Wind(result.getInt(NAME));
	}
}
