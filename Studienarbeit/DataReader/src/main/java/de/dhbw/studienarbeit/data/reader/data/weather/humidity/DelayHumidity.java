package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

import java.io.IOException;
import java.util.List;

public interface DelayHumidity
{
	List<DelayHumidityData> getDelays() throws IOException;
}
