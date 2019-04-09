package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;

public interface CancelledStopsPressure
{
	List<CancelledStopsData<Pressure>> getCancelledStops() throws IOException;
}
