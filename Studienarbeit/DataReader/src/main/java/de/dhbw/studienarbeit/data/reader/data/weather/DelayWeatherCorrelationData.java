package de.dhbw.studienarbeit.data.reader.data.weather;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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
		final NumberFormat formatter = new DecimalFormat("#0.00");
		return formatter.format(Math.round(value * 100) / 100d);
	}
}
