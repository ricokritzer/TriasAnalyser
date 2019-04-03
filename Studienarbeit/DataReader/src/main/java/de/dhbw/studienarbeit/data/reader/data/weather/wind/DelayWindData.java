package de.dhbw.studienarbeit.data.reader.data.weather.wind;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayWindData extends DelayData<Double>
{
	public DelayWindData(DelayMaximum delayMaximum, DelayAverage delayAverage, double value)
	{
		super(delayMaximum, delayAverage, value);
	}

	@Override
	public String getValueString()
	{
		return "" + Math.round(value) + "km/h";
	}
}
