package de.dhbw.studienarbeit.data.reader.data.station;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayStationData extends DelayData<StationData>
{
	private final int count;

	public DelayStationData(DelayMaximum maximum, DelayAverage average, StationID stationID, StationName stationName,
			OperatorName operator, Position position, int count)
	{
		super(maximum, average, new StationData(stationID, stationName, position, operator));
		this.count = count;
	}

	public int getCount()
	{
		return count;
	}

	public StationID getStationID()
	{
		return value.getStationID();
	}

	public StationName getName()
	{
		return value.getName();
	}

	public Position getPosition()
	{
		return value.getPosition();
	}

	public OperatorName getOperator()
	{
		return value.getOperator();
	}

	@Override
	public String getValueString()
	{
		return value.toString();
	}
}
