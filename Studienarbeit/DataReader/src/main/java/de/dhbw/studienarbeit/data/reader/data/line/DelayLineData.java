package de.dhbw.studienarbeit.data.reader.data.line;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class DelayLineData implements LineData, DelayData
{
	private final double maximum;
	private final double average;
	private final Line line;

	public DelayLineData(double delayAverage, double delayMaximum, Line line)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.line = line;
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
	public LineID getID()
	{
		return line.getID();
	}

	@Override
	public LineName getName()
	{
		return line.getName();
	}

	@Override
	public LineDestination getDestination()
	{
		return line.getDestination();
	}

	@Override
	public String getLineName()
	{
		return line.getLineName();
	}

	@Override
	public String getLineDestination()
	{
		return line.getLineDestination();
	}
}
