package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayHumidityData extends DelayData
{
	private final double value;

	public DelayHumidityData(DelayMaximum delayMaximum, DelayAverage delayAverage, double value)
	{
		super(delayMaximum, delayAverage);
		this.value = value;
	}

	public double getHumidity()
	{
		return value;
	}
}
