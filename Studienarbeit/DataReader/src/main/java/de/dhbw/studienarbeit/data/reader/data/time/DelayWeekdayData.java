package de.dhbw.studienarbeit.data.reader.data.time;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class DelayWeekdayData implements DelayData
{
	private final double average;
	private final double maximum;
	private final Weekday value;

	public DelayWeekdayData(double delayAverage, double delayMaximum, Weekday value)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.value = value;
	}

	public Weekday getWeekday()
	{
		return value;
	}

	@Override
	public double getDelayMaximum()
	{
		return maximum;
	}

	@Override
	public double getDelayAverage()
	{
		return average;
	}
}
