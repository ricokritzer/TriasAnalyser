package de.dhbw.studienarbeit.WebView.data;

import java.util.Date;

import de.dhbw.studienarbeit.data.reader.database.Count;

public class DatabaseBean
{
	public static final DatabaseBean EMPTY = new DatabaseBean(Count.UNABLE_TO_COUNT, Count.UNABLE_TO_COUNT,
			Count.UNABLE_TO_COUNT, Count.UNABLE_TO_COUNT, new Date());

	private Count countStations = Count.UNABLE_TO_COUNT;
	private Count countLines = Count.UNABLE_TO_COUNT;
	private Count countStops = Count.UNABLE_TO_COUNT;
	private Count countWeather = Count.UNABLE_TO_COUNT;
	private Date lastUpdate = new Date();

	public DatabaseBean(Count countStation, Count countLines, Count countStops, Count countWeather,
			Date lastUpdate)
	{
		this.countStations = countStation;
		this.countLines = countLines;
		this.countStops = countStops;
		this.countWeather = countWeather;
		this.lastUpdate = lastUpdate;
	}

	public Count getCountStations()
	{
		return countStations;
	}

	public Count getCountLines()
	{
		return countLines;
	}

	public Count getCountStops()
	{
		return countStops;
	}

	public Count getCountWeather()
	{
		return countWeather;
	}

	public Date getLastUpdate()
	{
		return lastUpdate;
	}
}
