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

public class DelayTempDB
{
	private static final Logger LOGGER = Logger.getLogger(DelayTempDB.class.getName());
	private static final String FIELD = "Round(temp, 0)";
	private static final String NAME = "rounded";

	private final double average;
	private final double maximum;
	private final double value;

	public DelayTempDB(double delayAverage, double delayMaximum, double value)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.value = value;
	}

	public double getAverage()
	{
		return average;
	}

	public double getMaximum()
	{
		return maximum;
	}

	public double getValue()
	{
		return value;
	}

	private static final Optional<DelayTempDB> getDelayLine(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");
			final double wind = result.getDouble(NAME);

			return Optional.of(new DelayTempDB(delayAverage, delayMaximum, wind));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayTempDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public static final List<DelayTempDB> getDelays() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQL(FIELD, NAME);

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayTempDB> list = new ArrayList<>();
			database.select(r -> DelayTempDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
