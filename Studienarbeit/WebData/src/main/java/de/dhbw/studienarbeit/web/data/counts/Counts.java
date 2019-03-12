package de.dhbw.studienarbeit.web.data.counts;

import java.util.Date;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;

@Deprecated
public class Counts
{
	private final CountData countStations;
	private final CountData countObservedStations;
	private final CountData countStationsWithRealtimeData;
	private final CountData countLines;
	private final CountData countStops;
	private final CountData countWeathers;
	private final CountData countOperators;
	private final Date lastUpdate;

	public Counts(CountData countStations, CountData countObservedStations, CountData countStationsWithRealtimeData,
			CountData countLines, CountData countStops, CountData countWeathers, CountData countOperators, Date lastUpdate)
	{
		super();
		this.countStations = countStations;
		this.countObservedStations = countObservedStations;
		this.countStationsWithRealtimeData = countStationsWithRealtimeData;
		this.countLines = countLines;
		this.countStops = countStops;
		this.countWeathers = countWeathers;
		this.countOperators = countOperators;
		this.lastUpdate = lastUpdate;
	}

	public CountData getCountStations()
	{
		return countStations;
	}

	public CountData getCountObservedStations()
	{
		return countObservedStations;
	}

	public CountData getCountStationsWithRealtimeData()
	{
		return countStationsWithRealtimeData;
	}

	public CountData getCountLines()
	{
		return countLines;
	}

	public CountData getCountStops()
	{
		return countStops;
	}

	public CountData getCountWeathers()
	{
		return countWeathers;
	}

	public CountData getCountOperators()
	{
		return countOperators;
	}

	public Date getLastUpdate()
	{
		return lastUpdate;
	}
}
