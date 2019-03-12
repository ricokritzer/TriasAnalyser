package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.Count;
import de.dhbw.studienarbeit.data.reader.data.count.CountWeather;
import de.dhbw.studienarbeit.data.reader.data.count.CountWeatherDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountWeathersWO extends CountListWO
{
	protected CountWeather countWeather = new CountWeatherDB();

	public CountWeathersWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	@Override
	protected void updateData() throws IOException
	{
		final Count stations = countWeather.countWeather();
		final Date lastUpdate = new Date();
		add(new CountWO(stations, lastUpdate));
	}

	public void setCountWeather(CountWeather countWeather)
	{
		this.countWeather = countWeather;
		update();
	}
}
