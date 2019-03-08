package de.dhbw.studienarbeit.data.reader.data.time;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class DelayHourData extends DelayData
{
	private final Hour value;

	public DelayHourData(double delayAverage, double delayMaximum, Hour value)
	{
		super(delayMaximum, delayAverage);
		this.value = value;
	}

	public Hour getHour()
	{
		return value;
	}
}
