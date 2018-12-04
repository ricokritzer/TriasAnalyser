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

public class DelayWeatherDB
{
	private static final Logger LOGGER = Logger.getLogger(DelayWeatherDB.class.getName());
	private static final String WHAT = "temp, wind, pressure, humidity, clouds";

	private final double average;
	private final double maximum;
	private final double humidity;
	private final double pressure;
	private final double wind;
	private final double clouds;
	private final double temp;
	private final String text;

	public DelayWeatherDB(double delayAverage, double delayMaximum, double humidity, double pressure, double wind,
			double clouds, double temp)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.humidity = humidity;
		this.pressure = pressure;
		this.wind = wind;
		this.clouds = clouds;
		this.temp = temp;
		this.text = "no description.";
	}

	public double getAverage()
	{
		return average;
	}

	public double getMaximum()
	{
		return maximum;
	}

	public double getHumidity()
	{
		return humidity;
	}

	public double getPressure()
	{
		return pressure;
	}

	public double getWind()
	{
		return wind;
	}

	public double getClouds()
	{
		return clouds;
	}

	public double getTemp()
	{
		return temp;
	}

	@Deprecated
	public String getText()
	{
		return text;
	}

	private static final Optional<DelayWeatherDB> getDelayLine(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");
			final double humidity = result.getDouble("humidity");
			final double pressure = result.getDouble("pressure");
			final double wind = result.getDouble("wind");
			final double clouds = result.getDouble("clouds");
			final double temp = result.getDouble("temp");

			return Optional.of(new DelayWeatherDB(delayAverage, delayMaximum, humidity, pressure, wind, clouds, temp));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to Weather.", e);
			return Optional.empty();
		}
	}

	public static final List<DelayWeatherDB> getDelays() throws IOException
	{
		final String sql = DelayWeatherDBHelper.getSQL(WHAT);

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayWeatherDB> list = new ArrayList<>();
			database.select(r -> DelayWeatherDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
