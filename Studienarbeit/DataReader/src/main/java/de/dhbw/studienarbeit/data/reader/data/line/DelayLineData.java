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

	/*
	 * @Deprecated use getValue()
	 */
	@Deprecated
	public String getLineName()
	{
		return value.getName().getValue();
	}

	/*
	 * @Deprecated use getValue()
	 */
	@Deprecated
	public String getLineDestination()
	{
		return value.getDestination().getValue();
	}
}
