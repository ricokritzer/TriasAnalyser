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

public class DelayWeatherTextDB
{
	private static final Logger LOGGER = Logger.getLogger(DelayWeatherTextDB.class.getName());
	private static final String FIELD = "text";
	private static final String NAME = "text";

	private final double average;
	private final double maximum;
	private final String value;

	public DelayWeatherTextDB(double delayAverage, double delayMaximum, String value)
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

	public String getValue()
	{
		return value;
	}

	private static final Optional<DelayWeatherTextDB> getDelayLine(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");
			final String value = result.getString(NAME);

			return Optional.of(new DelayWeatherTextDB(delayAverage, delayMaximum, value));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayWeatherTextDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public static final List<DelayWeatherTextDB> getDelays() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQL(FIELD, NAME);

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayWeatherTextDB> list = new ArrayList<>();
			database.select(r -> DelayWeatherTextDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
