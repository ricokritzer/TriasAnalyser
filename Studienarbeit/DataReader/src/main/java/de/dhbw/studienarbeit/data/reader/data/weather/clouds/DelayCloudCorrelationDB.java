package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import java.io.IOException;

import de.dhbw.studienarbeit.data.helper.statistics.Correlation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherCorrelationDB;

public class DelayCloudCorrelationDB extends DelayWeatherCorrelationDB implements DelayCloudCorrelation
{
	private static final String WHAT = "clouds";

	public DelayCloudCorrelationData getDelayCloudCorrelation() throws IOException
	{
		return new DelayCloudCorrelationData(Correlation.of(getDelay(WHAT)));
	}
}
