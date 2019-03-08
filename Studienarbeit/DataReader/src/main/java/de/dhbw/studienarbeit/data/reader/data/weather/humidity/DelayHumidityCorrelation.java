package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

import java.io.IOException;

public interface DelayHumidityCorrelation
{
	DelayHumidityCorrelationData getDelayHumidityCorrelation() throws IOException;
}
