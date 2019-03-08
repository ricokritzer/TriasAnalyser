package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

import java.io.IOException;
import java.util.List;

public interface DelayTemperature
{
	List<DelayTemperatureData> getDelays() throws IOException;
}
