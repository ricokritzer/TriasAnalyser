package de.dhbw.studienarbeit.data.reader.data.station;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class DelayStationData implements StationData, DelayData
{
	private final double maximum;
	private final double average;
	private final StationID stationID;
	private final StationName stationName;
	private final OperatorName operator;
	private final Position position;
	private final int count;

	public DelayStationData(double maximum, double average, StationID stationID, StationName stationName,
			OperatorName operator, Position position, int count)
	{
		super();
		this.maximum = maximum;
		this.average = average;
		this.stationID = stationID;
		this.stationName = stationName;
		this.operator = operator;
		this.position = position;
		this.count = count;
	}

	public int getCount()
	{
		return count;
	}

	@Override
	public double getDelayMaximum()
	{
		return maximum;
	}

	@Override
	public double getDelayAverage()
	{
		return average;
	}

	@Override
	public StationID getStationID()
	{
		return stationID;
	}

	@Override
	public StationName getName()
	{
		return stationName;
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
