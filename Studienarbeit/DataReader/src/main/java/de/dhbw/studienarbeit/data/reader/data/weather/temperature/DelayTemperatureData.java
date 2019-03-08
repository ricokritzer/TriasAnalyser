package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayTemperatureData extends DelayData
{
	private final double value;

	public DelayTemperatureData(DelayMaximum delayMaximum, DelayAverage delayAverage, double temperature)
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
