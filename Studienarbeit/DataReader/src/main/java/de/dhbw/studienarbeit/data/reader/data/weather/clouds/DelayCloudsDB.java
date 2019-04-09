package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class DelayCloudsDB extends DB<DelayData<Clouds>> implements DelayClouds
{
	private static final String FIELD = "ROUND(clouds, 0)";
	private static final String NAME = "rounded";

	public final List<DelayData<Clouds>> getDelays() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQL(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<DelayData<Clouds>> getValue(ResultSet result) throws SQLException
	{
		final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
		final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
		final Clouds clouds = new Clouds(result.getInt(NAME));

		return Optional.of(new DelayData<Clouds>(delayMaximum, delayAverage, clouds));
	}
}
