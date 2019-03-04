package de.dhbw.studienarbeit.data.stationNeighbourLinker;

public class LineStation
{
	private final String stationID;
	private final int lineID;

	public LineStation(String stationID, int lineID)
	{
		this.stationID = stationID;
		this.lineID = lineID;
	}
	
	public String getStationID()
	{
		return stationID;
	}

	public int getLineID()
	{
		return lineID;
	}
}
