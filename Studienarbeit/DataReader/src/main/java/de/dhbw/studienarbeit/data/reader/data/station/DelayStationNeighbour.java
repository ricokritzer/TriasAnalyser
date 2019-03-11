package de.dhbw.studienarbeit.data.reader.data.station;

import java.util.List;
import java.util.Optional;

public interface DelayStationNeighbour
{
	List<DelayStationNeighbourData> convertToStationNeighbours(final List<StationNeighbourData> tracks);

	Optional<DelayStationNeighbourData> convertToStationNeighbour(final StationNeighbourData stationNeighbour);
}
