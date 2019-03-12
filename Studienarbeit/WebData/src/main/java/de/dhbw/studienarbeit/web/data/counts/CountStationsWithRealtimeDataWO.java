package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.count.CountStationsWithRealtimeData;
import de.dhbw.studienarbeit.data.reader.data.count.CountStationsWithRealtimeDataDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountStationsWithRealtimeDataWO extends CountListWO<CountStationsWithRealtimeData>
{
	public CountStationsWithRealtimeDataWO(Optional<DataUpdater> updater)
	{
		counter = new CountStationsWithRealtimeDataDB();
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	/**
	 * @deprecated use setCounter() instead.
	 */
	@Deprecated
	public void setCountStations(CountStationsWithRealtimeData countStationsWithRealtimeData)
	{
		setCounter(countStationsWithRealtimeData);
	}

	@Override
	protected CountData count() throws IOException
	{
		return counter.count();
	}
}
