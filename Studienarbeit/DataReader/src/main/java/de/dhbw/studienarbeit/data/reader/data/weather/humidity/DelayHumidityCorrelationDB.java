package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

import java.io.IOException;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherCorrelationDB;

public class DelayHumidityCorrelationDB extends DelayWeatherCorrelationDB implements DelayCorrelation<Humidity>
{
	private static final String WHAT = "humidity";

	@Override
	public DelayCorrelationData<Humidity> getDelayCorrelation() throws IOException
	{
		return new DelayCorrelationData<>(getCorrelationData(WHAT), Humidity.class);
	}
}
