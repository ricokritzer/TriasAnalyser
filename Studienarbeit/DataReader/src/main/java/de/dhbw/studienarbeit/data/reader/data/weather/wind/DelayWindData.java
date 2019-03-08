package de.dhbw.studienarbeit.data.reader.data.weather.wind;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class DelayWindData extends DelayData
{
	private final double value;

	public DelayWindData(double delayAverage, double delayMaximum, double value)
	{
		super(delayMaximum, delayAverage);
		this.value = value;
	}

	private final double getValue()
	{
		return value;
	}
}
