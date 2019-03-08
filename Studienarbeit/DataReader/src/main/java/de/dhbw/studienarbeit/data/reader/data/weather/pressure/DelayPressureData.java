package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayPressureData extends DelayData
{
	private final double value;

	public DelayPressureData(DelayMaximum delayMaximum, DelayAverage delayAverage, double pressure)
	{
		super(delayMaximum, delayAverage);
		value = pressure;
	}

	public double getValue()
	{
		return value;
	}
}
