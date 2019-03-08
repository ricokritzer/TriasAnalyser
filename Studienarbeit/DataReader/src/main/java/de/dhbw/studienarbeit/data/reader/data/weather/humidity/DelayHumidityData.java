package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class DelayHumidityData extends DelayData
{
	private final double value;

	public DelayHumidityData(double delayAverage, double delayMaximum, double value)
	{
		super(delayMaximum, delayAverage);
		this.value = value;
	}

	public double getHumidity()
	{
		return value;
	}
}
