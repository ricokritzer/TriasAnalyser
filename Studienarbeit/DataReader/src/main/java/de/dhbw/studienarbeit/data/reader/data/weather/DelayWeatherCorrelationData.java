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
}
