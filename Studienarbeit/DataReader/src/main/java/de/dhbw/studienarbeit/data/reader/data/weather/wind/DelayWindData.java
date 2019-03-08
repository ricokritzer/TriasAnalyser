package de.dhbw.studienarbeit.data.reader.data.weather.wind;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayWindData extends DelayData
{
	private final double value;

	public DelayWindData(DelayMaximum delayMaximum, DelayAverage delayAverage, double value)
	{
		super(delayMaximum, delayAverage);
		this.value = value;
	}

	public final double getValue()
	{
		return value;
	}
}
