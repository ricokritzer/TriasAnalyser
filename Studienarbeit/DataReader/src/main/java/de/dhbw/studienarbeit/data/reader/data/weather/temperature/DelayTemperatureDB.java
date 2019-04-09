package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class DelayTemperatureDB extends DB<DelayData<Temperature>> implements DelayTemperature
{
	private static final String FIELD = "Round(temp, 0)";
	private static final String NAME = "rounded";

	public final List<DelayData<Temperature>> getDelays() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQL(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<DelayData<Temperature>> getValue(ResultSet result) throws SQLException
	{
		final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
		final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
		final CountData count = new CountData(result.getInt("total"));
		final Temperature temperature = new Temperature(result.getInt(NAME));

		return Optional.of(new DelayData<Temperature>(delayMaximum, delayAverage, count, temperature));
	}
}
