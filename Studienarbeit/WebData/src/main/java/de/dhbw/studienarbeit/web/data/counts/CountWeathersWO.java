package de.dhbw.studienarbeit.web.data.counts;

import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountWeather;
import de.dhbw.studienarbeit.data.reader.data.count.CountWeatherDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountWeathersWO extends CountListWO<CountWeather>
{
	public CountWeathersWO(Optional<DataUpdater> updater)
	{
		counter = new CountWeatherDB();
		updater.ifPresent(u -> u.updateEvery(5, MINUTES, this));
	}
}
