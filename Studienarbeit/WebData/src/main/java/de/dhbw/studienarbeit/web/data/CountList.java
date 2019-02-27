package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.database.Count;

public class CountList extends Updateable
{
	private static final int MAX_COUNT_ITEMS = 10;

	private List<Counts> data = new ArrayList<>();

	public CountList(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(5, MINUTES, this));
	}

	public List<Counts> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		Count countStations = Count.countStations();
		Count countObservedStations = Count.countObservedStations();
		Count countStationsWithRealtimeData = Count.countStationsWithRealtimeData();
		Count countLines = Count.countLines();
		Count countStops = Count.countStops();
		Count countWeathers = Count.countWeather();
		Count countOperators = Count.countObservedOperators();
		Date lastUpdate = new Date();

		data.add(0, new Counts(countStations, countObservedStations, countStationsWithRealtimeData, countLines,
				countStops, countWeathers, countOperators, lastUpdate));
		reduceToTen(data);
	}

	private static void reduceToTen(List<? extends Object> list)
	{
		while (list.size() > MAX_COUNT_ITEMS)
		{
			list.remove(MAX_COUNT_ITEMS);
		}
	}
}
