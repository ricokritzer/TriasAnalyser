package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public interface DelayHumidity
{
	List<DelayData<Humidity>> getDelays() throws IOException;
}
