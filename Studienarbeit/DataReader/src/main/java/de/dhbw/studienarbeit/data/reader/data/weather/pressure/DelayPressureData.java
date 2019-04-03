package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayPressureData extends DelayData<Double>
{
	public DelayPressureData(DelayMaximum delayMaximum, DelayAverage delayAverage, double pressure)
	{
		super(delayMaximum, delayAverage, pressure);
	}

	@Override
	public String getValueString()
	{
		return value.toString() + "hPa";
	}
}
