package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayHumidityData extends DelayData<Double>
{
	public DelayHumidityData(DelayMaximum delayMaximum, DelayAverage delayAverage, double value)
	{
		super(delayMaximum, delayAverage, value);
	}

	@Deprecated
	public double getHumidity()
	{
		return value;
	}
}
