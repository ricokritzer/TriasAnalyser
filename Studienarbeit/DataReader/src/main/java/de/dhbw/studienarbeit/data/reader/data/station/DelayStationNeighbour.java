package de.dhbw.studienarbeit.data.reader.data.station;

import java.util.List;

public interface DelayStationNeighbour
{
	List<DelayStationNeighbourData> convertToStationNeighbours(final List<StationNeighbourData> tracks);
}
