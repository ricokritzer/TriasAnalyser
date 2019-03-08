package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import de.dhbw.studienarbeit.data.helper.statistics.Correlatable;

public class DelayCloudCorrelationData implements Correlatable
{
	private final double delay;
	private final double value;

	public DelayCloudCorrelationData(double delay, double value)
	{
		super();
		this.delay = delay;
		this.value = value;
	}

	@Override
	public double getX()
	{
		return delay;
	}

	@Override
	public double getY()
	{
		return value;
	}
}
