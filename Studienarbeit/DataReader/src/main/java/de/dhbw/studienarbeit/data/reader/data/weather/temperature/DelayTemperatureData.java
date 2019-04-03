package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayTemperatureData extends DelayData<Double>
{
	public DelayTemperatureData(DelayMaximum delayMaximum, DelayAverage delayAverage, double temperature)
	{
		super(delayMaximum, delayAverage, temperature);
	}

	@Override
	public String getValueString()
	{
		return value.toString() + "°C";
	}
}
