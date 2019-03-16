package de.dhbw.studienarbeit.data.reader.data.weather.wind;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class DelayWindDB extends DB<DelayWindData> implements DelayWind
{
	private static final String FIELD = "Round(wind,0)";
	private static final String NAME = "rounded";

	public final List<DelayWindData> getDelays() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQL(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<DelayWindData> getValue(ResultSet result) throws SQLException
	{
		final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
		final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
		final double wind = result.getDouble(NAME);

		return Optional.of(new DelayWindData(delayMaximum, delayAverage, wind));
	}
}
