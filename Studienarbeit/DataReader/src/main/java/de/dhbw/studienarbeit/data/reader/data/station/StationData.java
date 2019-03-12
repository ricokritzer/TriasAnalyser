package de.dhbw.studienarbeit.data.reader.data.station;

public class StationData
{
	private final StationID id;
	private final StationName name;
	private final Position position;
	private final OperatorName operator;

	public StationData(StationID stationID, StationName name, Position position, OperatorName operator)
	{
		this.id = stationID;
		this.name = name;
		this.position = position;
		this.operator = operator;
	}

	public StationID getStationID()
	{
		return id;
	}

	public StationName getName()
	{
		return name;
	}

	public Position getPosition()
	{
		return position;
	}

	public OperatorName getOperator()
	{
		return operator;
	}
}
