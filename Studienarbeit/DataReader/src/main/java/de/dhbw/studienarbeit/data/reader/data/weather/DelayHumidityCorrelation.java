package de.dhbw.studienarbeit.data.reader.data.weather;

public class DelayHumidityCorrelation
{
	private final double value;

	public DelayHumidityCorrelation(double value)
	{
		super();
		this.value = value;
	}

	public double getCorrelation()
	{
		return value;
	}
}
