package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;

public interface CancelledStopsHumidity
{
	List<CancelledStopsData<Humidity>> getCancelledStops() throws IOException;
}
