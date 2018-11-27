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
	private final double average;
	private final double maximum;
	private final double humidity;
	private final double pressure;
	private final double wind;
	private final double clouds;
	private final String text;

	public DelayWeatherDB(double delayAverage, double delayMaximum, double humidity, double pressure, double wind,
			double clouds, String text)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.humidity = humidity;
		this.pressure = pressure;
		this.wind = wind;
		this.clouds = clouds;
		this.text = text;
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
			final String text = result.getString("text");

			return Optional.of(new DelayWeatherDB(delayAverage, delayMaximum, humidity, pressure, wind, clouds, text));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to stop.", e);
			return Optional.empty();
		}
	}

	public static final List<DelayWeatherDB> getDelays() throws IOException
	{
		final String sql = "SELECT avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max, "
				+ "Weather.temp AS temp, " + "Weather.humidity AS humidity, " + "Weather.pressure AS pressure, "
				+ "Weather.wind AS wind, " + "Weather.clouds AS clouds, " + "Weather.text AS text " + "FROM "
				+ "(SELECT max(Weather.timeStamp) AS timeStamp, s.lat, s.lon, s.stopID FROM "
				+ "(SELECT realTime, ROUND(Station.lat) AS lat, ROUND(Station.lon) AS lon, stopID FROM Stop, Station WHERE Stop.stationID = Station.stationID) AS s, "
				+ "Weather "
				+ "WHERE Weather.lat = s.lat AND Weather.lon = s.lon AND Weather.timeStamp < s.realTime AND Weather.timeStamp > DATE_SUB(s.realTime,INTERVAL 1 HOUR) "
				+ "GROUP BY Weather.timeStamp, s.lat, s.lon, s.stopID) AS w, Stop, Weather "
				+ "WHERE Weather.timeStamp = w.timeStamp AND Weather.lat = w.lat AND Weather.lon = w.lon AND Stop.stopID = w.stopID "
				+ "GROUP BY temp, humidity, pressure, wind, clouds, text " + "ORDER BY delay_avg DESC; ";

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
