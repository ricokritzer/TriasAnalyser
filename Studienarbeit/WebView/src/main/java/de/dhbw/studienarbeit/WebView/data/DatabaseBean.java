package de.dhbw.studienarbeit.WebView.data;

import java.util.Date;

public class DatabaseBean
{
	private int countStations = 0;
	private int countLines = 0;
	private int countStops = 0;
	private int countWeather = 0;
	private Date lastUpdate = new Date();

	public DatabaseBean(int countStation, int countLines, int countStops, int countWeather, Date lastUpdate)
	{
		this.countStations = countStation;
		this.countLines = countLines;
		this.countStops = countStops;
		this.countWeather = countWeather;
		this.lastUpdate = lastUpdate;
	}

	public int getCountStations()
	{
		return countStations;
	}

	public int getCountLines()
	{
		return countLines;
	}

	public int getCountStops()
	{
		return countStops;
	}

	public int getCountWeather()
	{
		return countWeather;
	}

	public Date getLastUpdate()
	{
		return lastUpdate;
	}
}
