package de.dhbw.studienarbeit.data.reader.data.line;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class DelayLineData extends DelayData implements LineData
{
	private final Line line;

	public DelayLineData(double delayAverage, double delayMaximum, Line line)
	{
		super(delayMaximum, delayAverage);
		this.line = line;
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
