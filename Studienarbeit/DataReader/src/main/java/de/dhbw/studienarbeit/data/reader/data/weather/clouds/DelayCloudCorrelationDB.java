package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.statistics.Correlation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherCorrelationHelper;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelayCloudCorrelationDB implements DelayCloudCorrelation
{
	private static final Logger LOGGER = Logger.getLogger(DelayCloudCorrelationDB.class.getName());
	private static final String WHAT = "clouds";

	private static final Optional<DelayCloudCorrelationData> getDelay(ResultSet result)
	{
		try
		{
			final double delay = result.getDouble("delay");
			final double value = result.getDouble(WHAT);

			return Optional.of(new DelayCloudCorrelationData(delay, value));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayCloudCorrelationDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public static final List<DelayCloudCorrelationData> getDelayClouds() throws IOException
	{
		final String sql = DelayWeatherCorrelationHelper.getSqlFor(WHAT);

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayCloudCorrelationData> list = new ArrayList<>();
			database.select(r -> getDelay(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public DelayCloudsCorrelation getDelayCloudsCorrelation() throws IOException
	{
		return new DelayCloudsCorrelation(Correlation.of(getDelayClouds()));
	}
}
