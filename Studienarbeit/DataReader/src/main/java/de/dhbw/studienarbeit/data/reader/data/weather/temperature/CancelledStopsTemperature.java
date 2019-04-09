package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;

public interface CancelledStopsTemperature
{
	List<CancelledStopsData<Temperature>> getCancelledStops() throws IOException;
}
