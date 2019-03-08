package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

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
