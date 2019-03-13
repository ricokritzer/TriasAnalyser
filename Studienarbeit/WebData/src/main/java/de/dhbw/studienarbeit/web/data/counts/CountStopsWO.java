package de.dhbw.studienarbeit.web.data.counts;

import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountStops;
import de.dhbw.studienarbeit.data.reader.data.count.CountStopsDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountStopsWO extends CountListWO<CountStops>
{
	public CountStopsWO(Optional<DataUpdater> updater)
	{
		counter = new CountStopsDB();
		updater.ifPresent(u -> u.updateEvery(5, MINUTES, this));
	}
}
