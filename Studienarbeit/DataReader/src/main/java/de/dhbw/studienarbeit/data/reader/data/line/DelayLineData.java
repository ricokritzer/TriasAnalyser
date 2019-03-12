package de.dhbw.studienarbeit.data.reader.data.line;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayLineData extends DelayData<Line>
{
	public DelayLineData(DelayMaximum delayMaximum, DelayAverage delayAverage, Line line)
	{
		super(delayMaximum, delayAverage, line);
	}

	public LineID getID()
	{
		return value.getID();
	}

	public LineName getName()
	{
		return value.getName();
	}

	public LineDestination getDestination()
	{
		return value.getDestination();
	}

	public String getLineName()
	{
		return value.getLineName();
	}

	public String getLineDestination()
	{
		return value.getLineDestination();
	}
}
