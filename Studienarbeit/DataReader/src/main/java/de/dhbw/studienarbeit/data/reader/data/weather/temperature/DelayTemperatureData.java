package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class DelayTemperatureData extends DelayData
{
	private final double value;

	public DelayTemperatureData(double delayMaximum, double delayAverage, double temperature)
	{
		super(delayMaximum, delayAverage);
		this.value = temperature;
	}

	public double getValue()
	{
		return value;
	}

	@Deprecated
	public double getTemperature()
	{
		return getValue();
	}
}
