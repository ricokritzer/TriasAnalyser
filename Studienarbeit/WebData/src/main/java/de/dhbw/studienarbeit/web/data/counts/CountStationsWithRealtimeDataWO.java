package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.count.CountStationsWithRealtimeData;
import de.dhbw.studienarbeit.data.reader.data.count.CountStationsWithRealtimeDataDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountStationsWithRealtimeDataWO extends CountListWO<CountStationsWithRealtimeData>
{
	protected CountStationsWithRealtimeData countStationsWithRealtimeData = new CountStationsWithRealtimeDataDB();

	public CountStationsWithRealtimeDataWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	/**
	 * @deprecated use setCounter() instead.
	 */
	@Deprecated
	public void setCountStations(CountStationsWithRealtimeData countStationsWithRealtimeData)
	{
		this.countStationsWithRealtimeData = countStationsWithRealtimeData;
		update();
	}

	@Override
	protected CountData count() throws IOException
	{
		return countStationsWithRealtimeData.count();
	}
}
