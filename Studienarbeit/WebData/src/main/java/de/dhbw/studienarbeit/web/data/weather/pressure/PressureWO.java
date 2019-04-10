package de.dhbw.studienarbeit.web.data.weather.pressure;

import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.CancelledStops;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.Delay;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.CancelledStopsPressureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.Pressure;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.weather.WeatherWO;

public class PressureWO extends WeatherWO<Pressure>
{
	public PressureWO(Optional<DataUpdater> updater)
	{
		super(updater);
	}

	@Override
	protected Delay<Pressure> getDefaultUpdaterDelays()
	{
		return new DelayPressureDB();
	}

	@Override
	protected DelayCorrelation<Pressure> getDefaultUpdaterCorrelation()
	{
		return new DelayPressureCorrelationDB();
	}

	@Override
	protected CancelledStops<Pressure> getDefaultUpdaterCancelledStops()
	{
		return new CancelledStopsPressureDB();
	}

	@Override
	protected DelayCorrelationData<Pressure> getDefaultCorrelation()
	{
		return new DelayCorrelationData<>(0.0, Pressure.class);
	}
}
