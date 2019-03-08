package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

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
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;
import de.dhbw.studienarbeit.data.reader.database.DelayWeatherCorrelationHelper;

public class DelayPressureCorrelationDB implements Correlatable
{
	private static final Logger LOGGER = Logger.getLogger(DelayPressureCorrelationDB.class.getName());
	private static final String WHAT = "pressure";

	private final double delay;
	private final double value;

	public DelayPressureCorrelationDB(double delay, double value)
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

	private static final Optional<DelayPressureCorrelationDB> getDelay(ResultSet result)
	{
		try
		{
			final double delay = result.getDouble("delay");
			final double temp = result.getDouble(WHAT);

			return Optional.of(new DelayPressureCorrelationDB(delay, temp));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayPressureCorrelationDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public static final List<DelayPressureCorrelationDB> getDelaysPressure() throws IOException
	{
		final String sql = DelayWeatherCorrelationHelper.getSqlFor(WHAT);

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayPressureCorrelationDB> list = new ArrayList<>();
			database.select(r -> getDelay(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public static DelayPressureCorrelation getCorrelationCoefficient() throws IOException
	{
		return new DelayPressureCorrelation(Correlation.of(getDelaysPressure()));
	}
}
