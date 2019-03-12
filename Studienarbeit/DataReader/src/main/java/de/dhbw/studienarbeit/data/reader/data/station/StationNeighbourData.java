package de.dhbw.studienarbeit.data.reader.data.station;

public class StationNeighbourData
{
	private final StationData stationFrom;
	private final StationData stationTo;

	public StationNeighbourData(StationData stationFrom, StationData stationTo)
	{
		this.stationFrom = stationFrom;
		this.stationTo = stationTo;
	}

	public StationData getStationFrom()
	{
		return stationFrom;
	}

	public StationData getStationTo()
	{
		return stationTo;
	}
}
