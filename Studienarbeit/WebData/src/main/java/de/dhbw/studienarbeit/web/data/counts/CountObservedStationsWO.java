package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.count.CountObservedStations;
import de.dhbw.studienarbeit.data.reader.data.count.CountObservedStationsDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountObservedStationsWO extends CountListWO<CountObservedStations>
{
	public CountObservedStationsWO(Optional<DataUpdater> updater)
	{
		counter = new CountObservedStationsDB();
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	/**
	 * @deprecated use setCounter() instead.
	 */
	@Deprecated
	public void setCountStations(CountObservedStations countObservedStations)
	{
		setCounter(countObservedStations);
	}

	@Override
	protected CountData count() throws IOException
	{
		return counter.count();
	}
}
