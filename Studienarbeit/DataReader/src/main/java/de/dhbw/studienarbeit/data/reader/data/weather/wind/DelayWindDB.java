package de.dhbw.studienarbeit.data.reader.data.weather.wind;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelayWindDB implements DelayWind
{
	private static final Logger LOGGER = Logger.getLogger(DelayWindDB.class.getName());
	private static final String FIELD = "Round(wind,0)";
	private static final String NAME = "rounded";

	private static final Optional<DelayWindData> getDelayLine(ResultSet result)
	{
		try
		{
			final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
			final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
			final double wind = result.getDouble(NAME);

			return Optional.of(new DelayWindData(delayMaximum, delayAverage, wind));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayWindDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public final List<DelayWindData> getDelays() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQL(FIELD, NAME);

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayWindData> list = new ArrayList<>();
			database.select(r -> DelayWindDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
