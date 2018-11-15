package de.dhbw.studienarbeit.WebView.data;

import java.util.Date;

public class DatabaseBean
{
	private long countStations = 0;
	private long countLines = 0;
	private long countStops = 0;
	private long countWeather = 0;
	private Date lastUpdate = new Date();

	public DatabaseBean(long countStation, long countLines, long countStops, long countWeather, Date lastUpdate)
	{
		this.countStations = countStation;
		this.countLines = countLines;
		this.countStops = countStops;
		this.countWeather = countWeather;
		this.lastUpdate = lastUpdate;
	}

	public long getCountStations()
	{
		return countStations;
	}

	public long getCountLines()
	{
		return countLines;
	}

	public long getCountStops()
	{
		return countStops;
	}

	public long getCountWeather()
	{
		return countWeather;
	}

	public Date getLastUpdate()
	{
		return lastUpdate;
	}
}
