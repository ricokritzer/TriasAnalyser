package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.count.CountStations;
import de.dhbw.studienarbeit.data.reader.data.count.CountStationsDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountStationsWO extends CountListWO<CountStations>
{

	public CountStationsWO(Optional<DataUpdater> updater)
	{
		counter = new CountStationsDB();
		updater.ifPresent(u -> u.updateEvery(5, MINUTES, this));
	}

	/**
	 * @deprecated use setCounter() instead.
	 */
	@Deprecated
	public void setCountStations(CountStations countStations)
	{
		setCounter(countStations);
	}

	@Override
	protected CountData count() throws IOException
	{
		return counter.count();
	}
}
