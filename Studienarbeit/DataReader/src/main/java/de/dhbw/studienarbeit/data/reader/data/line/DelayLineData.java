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
	public LineID getID()
	{
		return value.getID();
	}

	/*
	 * @Deprecated use getValue()
	 */
	@Deprecated
	public LineName getName()
	{
		return value.getName();
	}

	/*
	 * @Deprecated use getValue()
	 */
	@Deprecated
	public LineDestination getDestination()
	{
		return value.getDestination();
	}

	/*
	 * @Deprecated use getValue()
	 */
	@Deprecated
	public String getLineName()
	{
		return value.getLineName();
	}

	/*
	 * @Deprecated use getValue()
	 */
	@Deprecated
	public String getLineDestination()
	{
		return value.getLineDestination();
	}
}
