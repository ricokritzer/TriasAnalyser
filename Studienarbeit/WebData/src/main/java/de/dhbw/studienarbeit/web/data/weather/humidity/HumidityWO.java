package de.dhbw.studienarbeit.web.data.weather.humidity;

import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.CancelledStops;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.Delay;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.CancelledStopsHumidityDB;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidityCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidityDB;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.Humidity;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.weather.WeatherWO;

public class HumidityWO extends WeatherWO<Humidity>
{
	public HumidityWO(Optional<DataUpdater> updater)
	{
		super(updater);
	}

	@Override
	protected Delay<Humidity> getDefaultUpdaterDelays()
	{
		return new DelayHumidityDB();
	}

	@Override
	protected DelayCorrelation<Humidity> getDefaultUpdaterCorrelation()
	{
		return new DelayHumidityCorrelationDB();
	}

	@Override
	protected CancelledStops<Humidity> getDefaultUpdaterCancelledStops()
	{
		return new CancelledStopsHumidityDB();
	}

	@Override
	protected DelayCorrelationData<Humidity> getDefaultCorrelation()
	{
		return new DelayCorrelationData<>(0.0, Humidity.class);
	}
}
