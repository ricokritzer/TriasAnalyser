package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;

public interface LinesAtStation
{
	List<Line> getLinesAt(StationID stationID) throws IOException;
}
