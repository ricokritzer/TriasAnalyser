package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;

public interface CancelledStopsClouds
{
	List<CancelledStopsData<Clouds>> getCancelledStops() throws IOException;
}
