package de.dhbw.studienarbeit.data.reader.data.station;

import java.util.Objects;

import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.DelayAverage;

public class DelayStationNeighbourData implements Comparable<DelayStationNeighbourData>
{
	private final StationName stationName1;
	private final Position position1;
	private final DelayAverage delayAverage1;

	private final StationName stationName2;
	private final Position position2;
	private final DelayAverage delayAverage2;

	/*
	 * @Deprecated use the other constructor.
	 */
	@Deprecated
	public DelayStationNeighbourData(StationName stationName1, Position position1, double avg1,
			StationName stationName2, Position position2, double avg2)
	{
		super();
		this.stationName1 = stationName1;
		this.position1 = position1;
		this.delayAverage1 = new DelayAverage(avg1);
		this.stationName2 = stationName2;
		this.position2 = position2;
		this.delayAverage2 = new DelayAverage(avg2);
	}

	public DelayStationNeighbourData(StationName stationName1, Position position1, DelayAverage delayAverage1,
			StationName stationName2, Position position2, DelayAverage delayAverage2)
	{
		super();
		this.stationName1 = stationName1;
		this.position1 = position1;
		this.delayAverage1 = delayAverage1;
		this.stationName2 = stationName2;
		this.position2 = position2;
		this.delayAverage2 = delayAverage2;
	}

	public StationName getName1()
	{
		return stationName1;
	}

	public Position getPosition1()
	{
		return position1;
	}

	/*
	 * @Deprecated use getDelayAverage instead.
	 */
	@Deprecated
	public double getAvg1()
	{
		return delayAverage1.getValue();
	}

	public DelayAverage getDelayAverage1()
	{
		return delayAverage1;
	}

	public StationName getName2()
	{
		return stationName2;
	}

	public Position getPosition2()
	{
		return position2;
	}

	/*
	 * @Deprecated use getDelayAverage instead.
	 */
	@Deprecated
	public double getAvg2()
	{
		return delayAverage2.getValue();
	}

	public DelayAverage getDelayAverage2()
	{
		return delayAverage2;
	}

	public double getSlope()
	{
		final double distance = Position.getDistance(position1, position2);
		final double delayDifference = Math.abs(Delay.difference(delayAverage1, delayAverage2));

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
