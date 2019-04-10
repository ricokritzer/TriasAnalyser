package de.dhbw.studienarbeit.data.reader.data.weather.wind;

import java.io.IOException;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherCorrelationDB;

public class DelayWindCorrelationDB extends DelayWeatherCorrelationDB implements DelayCorrelation<Wind>
{
	private static final String WHAT = "wind";

	@Override
	public DelayCorrelationData<Wind> getDelayCorrelation() throws IOException
	{
		return new DelayCorrelationData<>(getCorrelationData(WHAT), Wind.class);
	}
}