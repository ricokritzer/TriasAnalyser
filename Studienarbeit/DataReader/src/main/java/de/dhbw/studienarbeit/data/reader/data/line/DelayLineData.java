package de.dhbw.studienarbeit.data.reader.data.line;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayLineData extends DelayData<Line> implements Line
{
	public DelayLineData(DelayMaximum delayMaximum, DelayAverage delayAverage, Line line)
	{
		super(delayMaximum, delayAverage, line);
	}

	@Override
	public LineID getID()
	{
		return value.getID();
	}

	@Override
	public LineName getName()
	{
		return value.getName();
	}

	@Override
	public LineDestination getDestination()
	{
		return value.getDestination();
	}

	@Override
	public String getLineName()
	{
		return value.getLineName();
	}

	@Override
	public String getLineDestination()
	{
		return value.getLineDestination();
	}
}
