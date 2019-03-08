package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

import java.io.IOException;
import java.util.List;

public interface DelayPressure
{
	List<DelayPressureData> getDelays() throws IOException;
}
