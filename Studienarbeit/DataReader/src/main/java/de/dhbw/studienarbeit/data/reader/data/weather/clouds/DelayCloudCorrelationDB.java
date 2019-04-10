package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import java.io.IOException;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherCorrelationDB;

public class DelayCloudCorrelationDB extends DelayWeatherCorrelationDB implements DelayCorrelation<Clouds>
{
	private static final String WHAT = "clouds";

	@Override
	public DelayCorrelationData<Clouds> getDelayCorrelation() throws IOException
	{
		return new DelayCorrelationData<>(getCorrelationData(WHAT), Clouds.class);
	}
}
