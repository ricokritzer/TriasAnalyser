package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.Count;
import de.dhbw.studienarbeit.data.reader.data.count.CountStations;
import de.dhbw.studienarbeit.data.reader.data.count.CountStationsDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountStationsWithRealtimeDataWO extends CountListWO
{
	protected CountStations countStations = new CountStationsDB();

	public CountStationsWithRealtimeDataWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	@Override
	protected void updateData() throws IOException
	{
		final Count stations = this.countStations.countObservedStations();
		final Date lastUpdate = new Date();
		add(new CountWO(stations, lastUpdate));
	}

	public void setCountStations(CountStations countStations)
	{
		this.countStations = countStations;
		update();
	}
}
