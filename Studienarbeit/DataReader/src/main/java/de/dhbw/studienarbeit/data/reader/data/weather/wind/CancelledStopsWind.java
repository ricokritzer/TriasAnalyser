package de.dhbw.studienarbeit.data.reader.data.weather.wind;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;

public interface CancelledStopsWind
{
	List<CancelledStopsData<Wind>> getCancelledStops() throws IOException;
}
