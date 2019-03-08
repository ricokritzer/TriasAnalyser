package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

import de.dhbw.studienarbeit.data.helper.statistics.Correlatable;
import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class DelayPressureData extends DelayData implements Correlatable
{
	private final double value;

	public DelayPressureData(double delayMaximum, double delayAverage, double pressure)
	{
		super(delayMaximum, delayAverage);
		value = pressure;
	}

	public double getValue()
	{
		return value;
	}

	@Override
	public double getX()
	{
		return getDelayAverage();
	}

	@Override
	public double getY()
	{
		return getValue();
	}
}
