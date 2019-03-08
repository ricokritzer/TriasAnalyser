package de.dhbw.studienarbeit.data.reader.data.weather;

import de.dhbw.studienarbeit.data.helper.statistics.Correlatable;

public class CorrelationData implements Correlatable
{
	private final double delay;
	private final double value;

	public CorrelationData(double delay, double value)
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
