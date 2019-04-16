package de.dhbw.studienarbeit.web.data.weather.wind;

import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.Delay;
import de.dhbw.studienarbeit.data.reader.data.weather.CancelledStops;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.CancelledStopsWindDB;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWindCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWindDB;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.Wind;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.weather.WeatherWO;

public class WindWO extends WeatherWO<Wind>
{
	public WindWO(Optional<DataUpdater> updater)
	{
		super(updater);
	}

	@Override
	protected Delay<Wind> getDefaultUpdaterDelays()
	{
		return new DelayWindDB();
	}

	@Override
	protected DelayCorrelation<Wind> getDefaultUpdaterCorrelation()
	{
		return new DelayWindCorrelationDB();
	}

	@Override
	protected CancelledStops<Wind> getDefaultUpdaterCancelledStops()
	{
		return new CancelledStopsWindDB();
	}

	@Override
	protected DelayCorrelationData<Wind> getDefaultCorrelation()
	{
		return new DelayCorrelationData<>(0.0, Wind.class);
	}
}
