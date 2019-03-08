package de.dhbw.studienarbeit.data.reader.data.station;

import java.util.Objects;

public class DelayStationNeighbourData implements Comparable<DelayStationNeighbourData>
{
	private final StationName stationName1;
	private final Position position1;
	private final double avg1;

	private final StationName stationName2;
	private final Position position2;
	private final double avg2;

	public DelayStationNeighbourData(StationName stationName1, Position position1, double avg1,
			StationName stationName2, Position position2, double avg2)
	{
		super();
		this.stationName1 = stationName1;
		this.position1 = position1;
		this.avg1 = avg1;
		this.stationName2 = stationName2;
		this.position2 = position2;
		this.avg2 = avg2;
	}

	public StationName getName1()
	{
		return stationName1;
	}

	@Deprecated
	public double getLat1()
	{
		return position1.getLat();
	}

	@Deprecated
	public double getLon1()
	{
		return position1.getLon();
	}

	public Position getPosition1()
	{
		return position1;
	}

	public double getAvg1()
	{
		return avg1;
	}

	public StationName getName2()
	{
		return stationName2;
	}

	@Deprecated
	public double getLat2()
	{
		return position2.getLat();
	}

	@Deprecated
	public double getLon2()
	{
		return position2.getLon();
	}

	public Position getPosition2()
	{
		return position2;
	}

	public double getAvg2()
	{
		return avg2;
	}

	public double getSlope()
	{
		final double distance = Position.getDistance(position1, position2);
		final double delayDifference = Math.abs(avg1 - avg2);

		return delayDifference / distance;
	}

	@Override
	public int compareTo(DelayStationNeighbourData o)
	{
		return Double.compare(this.getSlope(), o.getSlope());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof DelayStationNeighbourData)
		{
			final DelayStationNeighbourData o = (DelayStationNeighbourData) obj;
			return o.getPosition1().equals(this.position1) && o.getPosition2().equals(this.position2);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(position1, position2);
	}
}
