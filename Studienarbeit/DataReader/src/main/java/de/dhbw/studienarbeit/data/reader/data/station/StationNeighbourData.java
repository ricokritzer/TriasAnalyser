package de.dhbw.studienarbeit.data.reader.data.station;

public class StationNeighbourData
{
	private final StationNeighbourPart stationFrom;
	private final StationNeighbourPart stationTo;

	public StationNeighbourData(StationNeighbourPart stationFrom, StationNeighbourPart stationTo)
	{
		this.stationFrom = stationFrom;
		this.stationTo = stationTo;
	}

	public StationNeighbourPart getStationFrom()
	{
		return stationFrom;
	}

	public StationNeighbourPart getStationTo()
	{
		return stationTo;
	}
}
