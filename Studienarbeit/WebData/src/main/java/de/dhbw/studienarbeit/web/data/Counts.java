package de.dhbw.studienarbeit.web.data;

import java.util.Date;

import de.dhbw.studienarbeit.data.reader.database.Count;

public class Counts
{
	private final Count countStations;
	private final Count countLines;
	private final Count countStops;
	private final Count countWeathers;
	private final Count countOperators;
	private final Date lastUpdate;

	public Counts(Count countStations, Count countLines, Count countStops, Count countWeathers, Count countOperators,
			Date lastUpdate)
	{
		super();
		this.countStations = countStations;
		this.countLines = countLines;
		this.countStops = countStops;
		this.countWeathers = countWeathers;
		this.countOperators = countOperators;
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

	public Count getCountWeathers()
	{
		return countWeathers;
	}

	public Count getCountOperators()
	{
		return countOperators;
	}

	public Date getLastUpdate()
	{
		return lastUpdate;
	}
}
