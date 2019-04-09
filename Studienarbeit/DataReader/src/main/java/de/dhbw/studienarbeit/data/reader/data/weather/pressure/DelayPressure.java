package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public interface DelayPressure
{
	List<DelayData<Pressure>> getDelays() throws IOException;
}
