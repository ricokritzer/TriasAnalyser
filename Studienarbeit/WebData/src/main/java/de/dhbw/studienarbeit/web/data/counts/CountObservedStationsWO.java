package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.count.CountStationsWithRealtimeData;
import de.dhbw.studienarbeit.data.reader.data.count.CountStationsWithRealtimeDataDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountObservedStationsWO extends CountListWO
{
	protected CountStationsWithRealtimeData countStationsWithRealtimeData = new CountStationsWithRealtimeDataDB();

	public CountObservedStationsWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

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
