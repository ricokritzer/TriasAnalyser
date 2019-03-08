package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

import java.io.IOException;

import de.dhbw.studienarbeit.data.helper.statistics.Correlation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherCorrelationDB;

public class DelayTemperatureCorrelationDB extends DelayWeatherCorrelationDB implements DelayTemperatureCorrelation
{
	private static final String WHAT = "temp";

	@Override
	public DelayTemperatureCorrelationData getDelayTemperatureCorrelation() throws IOException
	{
		return new DelayTemperatureCorrelationData(Correlation.of(getDelay(WHAT)));
	}
}
