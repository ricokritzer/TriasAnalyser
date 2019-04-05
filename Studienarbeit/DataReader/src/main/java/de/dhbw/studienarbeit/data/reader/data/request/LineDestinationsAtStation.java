package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;

public interface LineDestinationsAtStation
{
	List<LineDestination> getLineDestinationsAt(StationID stationID, List<LineName> lineNames) throws IOException;
}
