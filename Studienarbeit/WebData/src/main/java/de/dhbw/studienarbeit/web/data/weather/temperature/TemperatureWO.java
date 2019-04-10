package de.dhbw.studienarbeit.web.data.weather.temperature;

import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.CancelledStops;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.Delays;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.CancelledStopsTemperatureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperatureCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperatureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.Temperature;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.weather.WeatherWO;

public class TemperatureWO extends WeatherWO<Temperature>
{
	public TemperatureWO(Optional<DataUpdater> updater)
	{
		super(updater);
	}

	@Override
	protected Delays<Temperature> getDefaultUpdaterDelays()
	{
		return new DelayTemperatureDB();
	}

	@Override
	protected DelayCorrelation<Temperature> getDefaultUpdaterCorrelation()
	{
		return new DelayTemperatureCorrelationDB();
	}

	@Override
	protected CancelledStops<Temperature> getDefaultUpdaterCancelledStops()
	{
		return new CancelledStopsTemperatureDB();
	}

	@Override
	protected DelayCorrelationData<Temperature> getDefaultCorrelation()
	{
		return new DelayCorrelationData<>(0.0, Temperature.class);
	}
}
