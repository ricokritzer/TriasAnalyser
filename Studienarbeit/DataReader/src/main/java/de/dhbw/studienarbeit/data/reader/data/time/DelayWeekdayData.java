package de.dhbw.studienarbeit.data.reader.data.time;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class DelayWeekdayData extends DelayData<Weekday>
{
	public DelayWeekdayData(DelayMaximum delayMaximum, DelayAverage delayAverage, CountData count, Weekday value)
	{
		super(delayMaximum, delayAverage, count, value);
	}

	@Override
	public String getValueString()
	{
		return value.getName();
	}
}
