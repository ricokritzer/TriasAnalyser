package de.dhbw.studienarbeit.data.reader.data.station;

public class StationNeighbourPart implements StationData
{
	private final StationID id;
	private final StationName name;
	private final Position position;
	private final OperatorName operator;

	public StationNeighbourPart(StationID stationID, StationName name, Position position, OperatorName operator)
	{
		super();
		this.id = stationID;
		this.name = name;
		this.position = position;
		this.operator = operator;
	}

	public StationID getStationID()
	{
		return id;
	}

	@Override
	public StationName getName()
	{
		return name;
	}

	@Override
	public Position getPosition()
	{
		return position;
	}

	@Override
	public OperatorName getOperator()
	{
		return operator;
	}
}
