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

import de.dhbw.studienarbeit.data.reader.data.weather.DelayHumidityData;

public class DelayHumidityDB implements DelayHumidityData
{
	private static final Logger LOGGER = Logger.getLogger(DelayHumidityDB.class.getName());
	private static final String FIELD = "ROUND(humidity, 0)";
	private static final String NAME = "rounded";

	private final double average;
	private final double maximum;
	private final double value;

	public DelayHumidityDB(double delayAverage, double delayMaximum, double value)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.value = value;
	}

	private static final Optional<DelayHumidityData> getDelayLine(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");
			final double wind = result.getDouble(NAME);

			return Optional.of(new DelayHumidityDB(delayAverage, delayMaximum, wind));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayHumidityDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public static final List<DelayHumidityData> getDelays() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQL(FIELD, NAME);

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayHumidityData> list = new ArrayList<>();
			database.select(r -> DelayHumidityDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	@Override
	public double getDelayMaximum()
	{
		return maximum;
	}

	@Override
	public double getDelayAverage()
	{
		return average;
	}

	@Override
	public double getHumidity()
	{
		return value;
	}
}
