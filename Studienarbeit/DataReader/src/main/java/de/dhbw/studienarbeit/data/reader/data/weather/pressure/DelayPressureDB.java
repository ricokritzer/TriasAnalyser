package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class DelayPressureDB extends DB<DelayPressureData> implements DelayPressure
{
	private static final String FIELD = "Round(pressure,0)";
	private static final String NAME = "rounded";

	public final List<DelayPressureData> getDelays() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQL(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<DelayPressureData> getValue(ResultSet result) throws SQLException
	{
		final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
		final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
		final int pressure = result.getInt(NAME);

		return Optional.of(new DelayPressureData(delayMaximum, delayAverage, pressure));
	}
}
