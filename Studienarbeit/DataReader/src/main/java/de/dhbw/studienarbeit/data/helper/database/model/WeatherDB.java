package de.dhbw.studienarbeit.data.helper.database.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherDB
{
	private static final Logger LOGGER = Logger.getLogger(WeatherDB.class.getName());

	final int lat;
	final int lon;
	final Date timeStamp;
	final double temp;
	final double humidity;
	final double pressure;
	final double wind;
	final double clouds;
	final String text;

	public WeatherDB(int lat, int lon, Date timeStamp, double temp, double humidity, double pressure, double wind,
			double clouds, String text)
	{
		this.lat = lat;
		this.lon = lon;
		this.timeStamp = timeStamp;
		this.temp = temp;
		this.humidity = humidity;
		this.pressure = pressure;
		this.wind = wind;
		this.clouds = clouds;
		this.text = text;
	}

	public int getLat()
	{
		return lat;
	}

	public int getLon()
	{
		return lon;
	}

	public Date getTimeStamp()
	{
		return timeStamp;
	}

	public double getTemp()
	{
		return temp;
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

	public static final Optional<WeatherDB> getWeather(ResultSet result)
	{
		try
		{
			final int lat = result.getInt("lat");
			final int lon = result.getInt("lon");
			final Date timeStamp = result.getDate("timeStamp");
			final double temp = result.getDouble("temp");
			final double humidity = result.getDouble("humidity");
			final double pressure = result.getDouble("pressure");
			final double wind = result.getDouble("wind");
			final double clouds = result.getDouble("clouds");
			final String text = result.getString("text");

			return Optional.of(new WeatherDB(lat, lon, timeStamp, temp, humidity, pressure, wind, clouds, text));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to line.", e);
			return Optional.empty();
		}
	}
}
