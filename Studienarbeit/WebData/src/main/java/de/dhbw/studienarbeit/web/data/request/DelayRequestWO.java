package de.dhbw.studienarbeit.web.data.request;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.line.LineData;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.database.DelayRequestDB;
import de.dhbw.studienarbeit.data.reader.database.LinesAtStationDB;

public class DelayRequestWO extends DelayRequestDB
{
	public DelayRequestWO(StationID stationID)
	{
		super(stationID);
	}

	public List<LineData> getLines() throws IOException
	{
		return LinesAtStationDB.getLinesAt(super.stationID);
	}
}
