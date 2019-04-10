package de.dhbw.studienarbeit.data.reader.data.weather;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;

public interface CancelledStops<T>
{
	List<CancelledStopsData<T>> getCancelledStops() throws IOException;
}
