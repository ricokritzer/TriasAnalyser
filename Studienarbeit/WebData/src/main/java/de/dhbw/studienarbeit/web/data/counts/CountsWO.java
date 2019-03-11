package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.Count;
import de.dhbw.studienarbeit.data.reader.data.count.CountLines;
import de.dhbw.studienarbeit.data.reader.data.count.CountLinesDB;
import de.dhbw.studienarbeit.data.reader.data.count.CountOperators;
import de.dhbw.studienarbeit.data.reader.data.count.CountOperatorsDB;
import de.dhbw.studienarbeit.data.reader.data.count.CountStations;
import de.dhbw.studienarbeit.data.reader.data.count.CountStationsDB;
import de.dhbw.studienarbeit.data.reader.data.count.CountStops;
import de.dhbw.studienarbeit.data.reader.data.count.CountStopsDB;
import de.dhbw.studienarbeit.data.reader.data.count.CountWeather;
import de.dhbw.studienarbeit.data.reader.data.count.CountWeatherDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class CountsWO extends Updateable
{
	private static final int MAX_COUNT_ITEMS = 10;

	protected CountStations countStations = new CountStationsDB();
	protected CountLines countLines = new CountLinesDB();
	protected CountStops countStops = new CountStopsDB();
	protected CountWeather countWeather = new CountWeatherDB();
	protected CountOperators countOperators = new CountOperatorsDB();

	private List<Counts> data = new ArrayList<>();

	public CountsWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(5, MINUTES, this));
	}

	public final List<Counts> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		final Count stations = this.countStations.countStations();
		final Count observedStations = this.countStations.countObservedStations();
		final Count stationsWithRealtimeData = this.countStations.countStationsWithRealtimeData();
		final Count lines = this.countLines.countLines();
		final Count stops = this.countStops.countStops();
		final Count weather = this.countWeather.countWeather();
		final Count operators = this.countOperators.countObservedOperators();
		final Date lastUpdate = new Date();

		data.add(0, new Counts(stations, observedStations, stationsWithRealtimeData, lines, stops, weather, operators,
				lastUpdate));

		reduceListElements(data);
	}

	protected static void reduceListElements(List<? extends Object> list)
	{
		while (list.size() > MAX_COUNT_ITEMS)
		{
			list.remove(MAX_COUNT_ITEMS);
		}
	}

	public void setCountStations(CountStations countStations)
	{
		this.countStations = countStations;
		update();
	}

	public void setCountLines(CountLines countLines)
	{
		this.countLines = countLines;
		update();
	}

	public void setCountStops(CountStops countStops)
	{
		this.countStops = countStops;
		update();
	}

	public void setCountWeather(CountWeather countWeather)
	{
		this.countWeather = countWeather;
		update();
	}

	public void setCountOperators(CountOperators countOperators)
	{
		this.countOperators = countOperators;
		update();
	}
}
