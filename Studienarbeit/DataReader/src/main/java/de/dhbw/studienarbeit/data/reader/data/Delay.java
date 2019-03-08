package de.dhbw.studienarbeit.data.reader.data;

import java.util.Objects;

public class Delay implements Comparable<Delay>
{
	private final double value;

	public Delay(double value)
	{
		super();
		this.value = value;
	}

	public final double getValue()
	{
		return value;
	}

	@Override
	public int compareTo(Delay o)
	{
		return Double.compare(this.getValue(), o.getValue());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Delay)
		{
			final Delay delay = (Delay) obj;
			return delay.value == this.value;
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(value);
	}
}
