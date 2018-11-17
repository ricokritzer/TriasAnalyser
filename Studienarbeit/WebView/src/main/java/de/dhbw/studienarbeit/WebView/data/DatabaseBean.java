package de.dhbw.studienarbeit.WebView.data;

import java.util.Date;

import de.dhbw.studienarbeit.data.helper.database.model.CountDB;

public class DatabaseBean
{
	public static final DatabaseBean EMPTY = new DatabaseBean(CountDB.UNABLE_TO_COUNT, CountDB.UNABLE_TO_COUNT,
			CountDB.UNABLE_TO_COUNT, CountDB.UNABLE_TO_COUNT, new Date());

	private CountDB countStations = CountDB.UNABLE_TO_COUNT;
	private CountDB countLines = CountDB.UNABLE_TO_COUNT;
	private CountDB countStops = CountDB.UNABLE_TO_COUNT;
	private CountDB countWeather = CountDB.UNABLE_TO_COUNT;
	private Date lastUpdate = new Date();

	public DatabaseBean(CountDB countStation, CountDB countLines, CountDB countStops, CountDB countWeather,
			Date lastUpdate)
	{
		this.countStations = countStation;
		this.countLines = countLines;
		this.countStops = countStops;
		this.countWeather = countWeather;
		this.lastUpdate = lastUpdate;
	}

	public CountDB getCountStations()
	{
		return countStations;
	}

	public CountDB getCountLines()
	{
		return countLines;
	}

	public CountDB getCountStops()
	{
		return countStops;
	}

	public CountDB getCountWeather()
	{
		return countWeather;
	}

	public Date getLastUpdate()
	{
		return lastUpdate;
	}
}
