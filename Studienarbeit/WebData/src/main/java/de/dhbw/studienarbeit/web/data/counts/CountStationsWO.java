package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.Count;
import de.dhbw.studienarbeit.data.reader.data.count.CountStations;
import de.dhbw.studienarbeit.data.reader.data.count.CountStationsDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountStationsWO extends CountListWO
{
	protected CountStations countStations = new CountStationsDB();

	public CountStationsWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(5, MINUTES, this));
	}

	public void setCountStations(CountStations countStations)
	{
		this.countStations = countStations;
		update();
	}

	@Override
	protected Count count() throws IOException
	{
		return countStations.countStations();
	}
}
