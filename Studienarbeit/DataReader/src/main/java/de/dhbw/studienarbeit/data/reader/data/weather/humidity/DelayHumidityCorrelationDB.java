package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

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
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherCorrelationHelper;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelayHumidityCorrelationDB implements Correlatable
{
	private static final Logger LOGGER = Logger.getLogger(DelayHumidityCorrelationDB.class.getName());
	private static final String WHAT = "humidity";

	private final double delay;
	private final double value;

	public DelayHumidityCorrelationDB(double delay, double value)
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

	private static final Optional<DelayHumidityCorrelationDB> getDelay(ResultSet result)
	{
		try
		{
			final double delay = result.getDouble("delay");
			final double value = result.getDouble(WHAT);

			return Optional.of(new DelayHumidityCorrelationDB(delay, value));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayHumidityCorrelationDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public static final List<DelayHumidityCorrelationDB> getDelays() throws IOException
	{
		final String sql = DelayWeatherCorrelationHelper.getSqlFor(WHAT);

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayHumidityCorrelationDB> list = new ArrayList<>();
			database.select(r -> getDelay(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public static DelayHumidityCorrelationData getCorrelationCoefficient() throws IOException
	{
		return new DelayHumidityCorrelationData(Correlation.of(getDelays()));
	}
}
