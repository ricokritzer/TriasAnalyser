package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public interface DelayTemperature
{
	List<DelayData<Temperature>> getDelays() throws IOException;
}
