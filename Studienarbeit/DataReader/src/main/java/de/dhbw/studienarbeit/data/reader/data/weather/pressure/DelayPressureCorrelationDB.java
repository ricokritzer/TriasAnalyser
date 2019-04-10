package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

import java.io.IOException;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherCorrelationDB;

public class DelayPressureCorrelationDB extends DelayWeatherCorrelationDB implements DelayCorrelation<Pressure>
{
	private static final String WHAT = "pressure";

	@Override
	public DelayCorrelationData<Pressure> getDelayCorrelation() throws IOException
	{
		return new DelayCorrelationData<>(getCorrelationData(WHAT), Pressure.class);
	}
}
