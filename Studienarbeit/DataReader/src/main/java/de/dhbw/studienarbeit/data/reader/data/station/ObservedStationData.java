package de.dhbw.studienarbeit.data.reader.data.station;

import de.dhbw.studienarbeit.data.reader.data.operator.OperatorID;

public class ObservedStationData
{
	private final StationID stationID;
	private final StationName stationName;
	private final Position position;
	private final OperatorID operatorID;

	public ObservedStationData(StationID stationID, StationName stationName, Position position, OperatorID operatorID)
	{
		super();
		this.stationID = stationID;
		this.stationName = stationName;
		this.position = position;
		this.operatorID = operatorID;
	}

	public StationID getStationID()
	{
		return stationID;
	}

	public StationName getStationName()
	{
		return stationName;
	}

	public Position getPosition()
	{
		return position;
	}

	public OperatorID getOperatorID()
	{
		return operatorID;
	}
}
