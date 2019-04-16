package de.dhbw.studienarbeit.web.data.weather.clouds;

import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.Delay;
import de.dhbw.studienarbeit.data.reader.data.weather.CancelledStops;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.CancelledStopsCloudsDB;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.Clouds;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudsDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.weather.WeatherWO;

public class CloudsWO extends WeatherWO<Clouds>
{
	public CloudsWO(Optional<DataUpdater> updater)
	{
		super(updater);
	}

	@Override
	protected Delay<Clouds> getDefaultUpdaterDelays()
	{
		return new DelayCloudsDB();
	}

	@Override
	protected DelayCorrelation<Clouds> getDefaultUpdaterCorrelation()
	{
		return new DelayCloudCorrelationDB();
	}

	@Override
	protected CancelledStops<Clouds> getDefaultUpdaterCancelledStops()
	{
		return new CancelledStopsCloudsDB();
	}

	@Override
	protected DelayCorrelationData<Clouds> getDefaultCorrelation()
	{
		return new DelayCorrelationData<>(0.0, Clouds.class);
	}
}
