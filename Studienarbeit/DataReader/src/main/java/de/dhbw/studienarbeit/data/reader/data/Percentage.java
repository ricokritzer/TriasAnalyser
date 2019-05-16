package de.dhbw.studienarbeit.data.reader.data;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class Percentage
{
	private final double value;

	public Percentage(CountData countTotal, CountData countPart)
	{
		value = (double) countPart.getValue() / countTotal.getValue() * 100;
	}

	public double getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		final double rounded = Math.round(value * 100) / 100.0;
		return new StringBuilder().append(rounded).append("%").toString();
	}
}
