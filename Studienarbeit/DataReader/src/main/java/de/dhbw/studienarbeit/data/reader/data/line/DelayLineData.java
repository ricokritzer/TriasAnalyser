package de.dhbw.studienarbeit.data.reader.data.line;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class DelayLineData extends DelayData<Line>
{
	public DelayLineData(DelayMaximum delayMaximum, DelayAverage delayAverage, CountData count, Line line)
	{
		super(delayMaximum, delayAverage, count, line);
	}

	@Override
	public String getValueString()
	{
		return value.toString();
	}
}
