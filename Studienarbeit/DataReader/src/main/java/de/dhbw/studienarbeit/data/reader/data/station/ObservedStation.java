package de.dhbw.studienarbeit.data.reader.data.station;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.operator.OperatorID;

public interface ObservedStation
{
	List<ObservedStationData> getObservedStations() throws IOException;

	List<ObservedStationData> getObservedStations(OperatorID operatorID) throws IOException;
}
