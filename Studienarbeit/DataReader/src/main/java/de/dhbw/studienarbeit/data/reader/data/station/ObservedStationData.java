package de.dhbw.studienarbeit.data.reader.data.station;

import de.dhbw.studienarbeit.data.reader.data.operator.OperatorID;

public class ObservedStationData
{
	private final StationID stationID;
	private final Position position;
	private final OperatorID operatorID;

	public ObservedStationData(StationID stationID, Position position, OperatorID operatorID)
	{
		super();
		this.stationID = stationID;
		this.position = position;
		this.operatorID = operatorID;
	}

	public StationID getStationID()
	{
		return stationID;
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
