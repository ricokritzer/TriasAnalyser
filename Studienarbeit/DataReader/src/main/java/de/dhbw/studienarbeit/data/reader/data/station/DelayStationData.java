package de.dhbw.studienarbeit.data.reader.data.station;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class DelayStationData extends DelayData<StationData>
{
	public DelayStationData(DelayMaximum maximum, DelayAverage average, StationID stationID, StationName stationName,
			OperatorName operator, Position position, CountData count)
	{
		super(maximum, average, count, new StationData(stationID, stationName, position, operator));
	}

	/*
	 * @Deprecated use getCount instead.
	 */
	@Deprecated
	public int getCountValue()
	{
		return (int) super.getCount().getValue();
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
