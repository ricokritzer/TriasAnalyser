package de.dhbw.studienarbeit.data.reader.data.time;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class DelayHourData implements DelayData
{
	private final double average;
	private final double maximum;
	private final Hour value;

	public DelayHourData(double delayAverage, double delayMaximum, Hour value)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.value = value;
	}

	public Hour getHour()
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
