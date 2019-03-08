package de.dhbw.studienarbeit.web.data.request;

import de.dhbw.studienarbeit.data.reader.data.station.StationName;
import de.dhbw.studienarbeit.data.reader.database.DelayRequestDB;

public class DelayRequestWO extends DelayRequestDB
{
	public DelayRequestWO(StationName stationName)
	{
		super(stationName);
	}
}
