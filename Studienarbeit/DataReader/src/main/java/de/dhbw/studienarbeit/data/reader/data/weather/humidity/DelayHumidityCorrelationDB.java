package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

import java.io.IOException;

import de.dhbw.studienarbeit.data.helper.statistics.Correlation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherCorrelationDB;

public class DelayHumidityCorrelationDB extends DelayWeatherCorrelationDB implements DelayHumidityCorrelation
{
	private static final String WHAT = "humidity";

	@Override
	public DelayHumidityCorrelationData getDelayHumidityCorrelation() throws IOException
	{
		return new DelayHumidityCorrelationData(Correlation.of(getDelay(WHAT)));
	}
}
