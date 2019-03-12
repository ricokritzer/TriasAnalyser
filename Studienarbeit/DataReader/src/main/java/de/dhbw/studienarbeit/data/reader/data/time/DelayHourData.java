package de.dhbw.studienarbeit.data.reader.data.time;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayHourData extends DelayData<Hour>
{
	public DelayHourData(DelayMaximum delayMaximum, DelayAverage delayAverage, Hour value)
	{
		super(delayMaximum, delayAverage, value);
	}

	@Deprecated
	public Hour getHour()
	{
		return value;
	}
}
