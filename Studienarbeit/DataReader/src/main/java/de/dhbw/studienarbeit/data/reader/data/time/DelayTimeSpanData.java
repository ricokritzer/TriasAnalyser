package de.dhbw.studienarbeit.data.reader.data.time;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayTimeSpanData extends DelayData<TimeSpan>
{
	public DelayTimeSpanData(DelayMaximum delayMaximum, DelayAverage delayAverage, TimeSpan value)
	{
		super(delayMaximum, delayAverage, value);
	}

	@Deprecated
	public TimeSpan getHour()
	{
		return value;
	}
}
