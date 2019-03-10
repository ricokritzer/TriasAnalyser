package de.dhbw.studienarbeit.data.reader.data.weather;

public abstract class DelayWeatherCorrelationData
{
	private final double value;

	public DelayWeatherCorrelationData(double value)
	{
		super();
		this.value = value;
	}

	public double getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return String.valueOf(Math.round(value * 100) / 100d);
	}
}
