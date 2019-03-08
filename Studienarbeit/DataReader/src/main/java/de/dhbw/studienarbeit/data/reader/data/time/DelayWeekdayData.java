package de.dhbw.studienarbeit.data.reader.data.time;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class DelayWeekdayData extends DelayData
{
	private final Weekday value;

	public DelayWeekdayData(double delayAverage, double delayMaximum, Weekday value)
	{
		super(delayMaximum, delayAverage);
		this.value = value;
	}

	public Weekday getWeekday()
	{
		return value;
	}
}
