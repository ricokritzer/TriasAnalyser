package de.dhbw.studienarbeit.data.trias.analyse.lines;

import java.sql.Timestamp;

public class LineStation
{
	private final String stationID;
	private final int lineID;
	private final Timestamp firstOccurence;
	private LineStation nextStation;

	public LineStation(String stationID, int lineID, Timestamp firstOccurence)
	{
		this.stationID = stationID;
		this.lineID = lineID;
		this.firstOccurence = firstOccurence;
	}
	
	public void setNextStation(LineStation station)
	{
		nextStation = station;
	}
	
	public LineStation getNextStation()
	{
		return nextStation;
	}
	
	public String getStationID()
	{
		return stationID;
	}

	public int getLineID()
	{
		return lineID;
	}

	public Timestamp getFirstOccurence()
	{
		return firstOccurence;
	}
}
