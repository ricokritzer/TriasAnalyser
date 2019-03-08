package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

import java.io.IOException;

public interface DelayPressureCorrelation
{
	DelayPressureCorrelationData getDelayPressureCorrelation() throws IOException;
}
