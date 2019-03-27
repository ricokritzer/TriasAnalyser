package de.dhbw.studienarbeit.data.reader.data.time;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayWeekdayData extends DelayData<Weekday>
{
	public DelayWeekdayData(DelayMaximum delayMaximum, DelayAverage delayAverage, Weekday value)
	{
		super(delayMaximum, delayAverage, value);
	}
}
