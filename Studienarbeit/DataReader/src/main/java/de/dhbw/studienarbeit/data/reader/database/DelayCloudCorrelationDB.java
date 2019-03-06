package de.dhbw.studienarbeit.data.reader.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.statistics.Correlatable;
import de.dhbw.studienarbeit.data.helper.statistics.Correlation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCloudsCorrelation;

public class DelayCloudCorrelationDB implements Correlatable
{
	private static final Logger LOGGER = Logger.getLogger(DelayCloudCorrelationDB.class.getName());
	private static final String WHAT = "clouds";

	private final double delay;
	private final double value;

	public DelayCloudCorrelationDB(double delay, double value)
	{
		super();
		this.delay = delay;
		this.value = value;
	}

	@Override
	public double getX()
	{
		return delay;
	}

	@Override
	public double getY()
	{
		return value;
	}

	private static final Optional<DelayCloudCorrelationDB> getDelay(ResultSet result)
	{
		try
		{
			final double delay = result.getDouble("delay");
			final double value = result.getDouble(WHAT);

			return Optional.of(new DelayCloudCorrelationDB(delay, value));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayCloudCorrelationDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public static final List<DelayCloudCorrelationDB> getDelayClouds() throws IOException
	{
		final String sql = DelayWeatherCorrelationHelper.getSqlFor(WHAT);

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayCloudCorrelationDB> list = new ArrayList<>();
			database.select(r -> getDelay(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public static DelayCloudsCorrelation getDelayCloudsCorrelation() throws IOException
	{
		return new DelayCloudsCorrelation(Correlation.of(getDelayClouds()));
	}
}
