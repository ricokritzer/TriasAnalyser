package de.dhbw.studienarbeit.data.reader.data.station;

import java.io.IOException;
import java.util.List;

public interface ObservedStation
{
	List<ObservedStationData> getObservedStations() throws IOException;
}
