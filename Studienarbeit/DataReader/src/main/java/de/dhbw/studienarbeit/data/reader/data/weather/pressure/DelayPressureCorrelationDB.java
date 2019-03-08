package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

import java.io.IOException;

import de.dhbw.studienarbeit.data.helper.statistics.Correlation;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidityCorrelationDB;

public class DelayPressureCorrelationDB extends DelayHumidityCorrelationDB implements DelayPressureCorrelation
{
	private static final String WHAT = "pressure";

	@Override
	public DelayPressureCorrelationData getDelayPressureCorrelation() throws IOException
	{
		return new DelayPressureCorrelationData(Correlation.of(getDelay(WHAT)));
	}

}
