package de.dhbw.studienarbeit.data.helper.database.model;

import java.util.Date;

public class WeatherDB
{
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
}
