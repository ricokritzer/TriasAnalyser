package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

import java.io.IOException;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherCorrelationDB;

public class DelayTemperatureCorrelationDB extends DelayWeatherCorrelationDB implements DelayCorrelation<Temperature>
{
	private static final String WHAT = "temp";

	@Override
	public DelayCorrelationData<Temperature> getDelayCorrelation() throws IOException
	{
		return new DelayCorrelationData<>(getCorrelationData(WHAT), Temperature.class);
	}
}
