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
	
	@Override
	public String toString()
	{
		double delay = value;
		int hour = 0;
		int min = 0;
		
		while (delay >= 3600)
		{
			hour++;
			delay -= 3600;
		}
		while (delay >= 60)
		{
			min++;
			delay -= 60;
		}
		
		if (hour == 0)
		{
			if (min == 0)
			{
				return Math.round(delay * 100) / 100d + "s";
			}
			return min + "m " + Math.round(delay) + "s";
		}
		return hour + "h " + min + "m " + Math.round(delay) + "s";
	}
}
