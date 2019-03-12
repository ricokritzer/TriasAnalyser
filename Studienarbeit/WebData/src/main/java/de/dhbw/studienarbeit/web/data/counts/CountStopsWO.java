package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.Count;
import de.dhbw.studienarbeit.data.reader.data.count.CountStops;
import de.dhbw.studienarbeit.data.reader.data.count.CountStopsDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountStopsWO extends CountListWO
{
	protected CountStops countStops = new CountStopsDB();

	public CountStopsWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(5, MINUTES, this));
	}

	@Override
	protected void updateData() throws IOException
	{
		final Count stations = countStops.countStops();
		final Date lastUpdate = new Date();
		add(new CountWO(stations, lastUpdate));
	}

	public void setCountStops(CountStops countStops)
	{
		this.countStops = countStops;
		update();
	}
}
