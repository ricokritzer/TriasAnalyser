package de.dhbw.studienarbeit.data.reader.data.weather.wind;

import java.io.IOException;

import de.dhbw.studienarbeit.data.helper.statistics.Correlation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherCorrelationDB;

public class DelayWindCorrelationDB extends DelayWeatherCorrelationDB implements DelayWindCorrelation
{
	private static final String WHAT = "wind";

	@Override
	public DelayWindCorrelationData getDelayWindCorrelation() throws IOException
	{
		return new DelayWindCorrelationData(Correlation.of(getDelay(WHAT)));
	}
}
