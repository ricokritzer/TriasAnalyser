package de.dhbw.studienarbeit.data.reader.data.weather;

public class DelayPressureCorrelation
{
	private final double value;

	public DelayPressureCorrelation(double value)
	{
		super();
		this.value = value;
	}

	public double getCorrelation()
	{
		return value;
	}
}
