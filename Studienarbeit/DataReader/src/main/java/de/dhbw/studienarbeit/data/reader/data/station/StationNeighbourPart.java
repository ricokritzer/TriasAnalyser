package de.dhbw.studienarbeit.data.reader.data.station;

import de.dhbw.studienarbeit.data.reader.database.Operator;

public class StationNeighbourPart implements StationData
{
	private final StationID id;
	private final StationName name;
	private final Position position;
	private final Operator operator;

	public StationNeighbourPart(StationID stationID, StationName name, Position position, Operator operator)
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
	public String getStationName()
	{
		return name.getStationName();
	}

	@Override
	public StationName getName()
	{
		return name;
	}

	@Override
	public double getLat()
	{
		return position.getLat();
	}

	@Override
	public double getLon()
	{
		return position.getLon();
	}

	@Override
	public Position getPosition()
	{
		return position;
	}

	@Override
	public String getOperator()
	{
		return operator.getName();
	}
}
