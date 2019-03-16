package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class DelayTemperatureDB extends DB<DelayTemperatureData> implements DelayTemperature
{
	private static final String FIELD = "Round(temp, 0)";
	private static final String NAME = "rounded";

	public final List<DelayTemperatureData> getDelays() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQL(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<DelayTemperatureData> getValue(ResultSet result) throws SQLException
	{
		final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
		final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
		final double temperature = result.getDouble(NAME);

		return Optional.of(new DelayTemperatureData(delayMaximum, delayAverage, temperature));
	}
}
