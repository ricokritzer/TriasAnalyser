package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.count.CountStops;
import de.dhbw.studienarbeit.data.reader.data.count.CountStopsDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountStopsWO extends CountListWO<CountStops>
{
	protected CountStops countStops = new CountStopsDB();

	public CountStopsWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(5, MINUTES, this));
	}

	/**
	 * @deprecated use setCounter() instead.
	 */
	@Deprecated
	public void setCountStops(CountStops countStops)
	{
		this.countStops = countStops;
		update();
	}

	@Override
	protected CountData count() throws IOException
	{
		return countStops.count();
	}
}
