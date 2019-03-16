package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class DelayHumidityDB extends DB<DelayHumidityData> implements DelayHumidity
{
	private static final String FIELD = "ROUND(humidity, 0)";
	private static final String NAME = "rounded";

	public final List<DelayHumidityData> getDelays() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQL(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<DelayHumidityData> getValue(ResultSet result) throws SQLException
	{
		final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
		final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
		final double wind = result.getDouble(NAME);

		return Optional.of(new DelayHumidityData(delayMaximum, delayAverage, wind));
	}
}
