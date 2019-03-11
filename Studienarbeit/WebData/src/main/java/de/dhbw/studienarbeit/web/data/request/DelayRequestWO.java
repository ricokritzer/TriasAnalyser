package de.dhbw.studienarbeit.web.data.request;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.line.LineData;
import de.dhbw.studienarbeit.data.reader.data.request.DelayRequestDB;
import de.dhbw.studienarbeit.data.reader.data.request.LinesAtStation;
import de.dhbw.studienarbeit.data.reader.data.request.LinesAtStationDB;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;

public class DelayRequestWO extends DelayRequestDB
{
	private LinesAtStation linesAtStation = new LinesAtStationDB();

	public DelayRequestWO(StationID stationID)
	{
		super(stationID);
	}

	public List<LineData> getLines() throws IOException
	{
		return linesAtStation.getLinesAt(super.stationID);
	}

	public void setLinesAtStation(LinesAtStation linesAtStation)
	{
		this.linesAtStation = linesAtStation;
	}
}
