package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.count.CountWeather;
import de.dhbw.studienarbeit.data.reader.data.count.CountWeatherDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountWeathersWO extends CountListWO<CountWeather>
{

	public CountWeathersWO(Optional<DataUpdater> updater)
	{
		counter = new CountWeatherDB();
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	/**
	 * @deprecated use setCounter() instead.
	 */
	@Deprecated
	public void setCountWeather(CountWeather countWeather)
	{
		setCounter(countWeather);
	}

	@Override
	protected CountData count() throws IOException
	{
		return counter.count();
	}
}
