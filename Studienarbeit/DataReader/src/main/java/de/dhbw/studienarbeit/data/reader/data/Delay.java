package de.dhbw.studienarbeit.data.reader.data;

import java.util.Objects;

public class Delay implements Comparable<Delay>
{
	private static final int SECONDS_PER_MINUTE = 60;
	private static final int SECONDS_PER_HOUR = 60 * SECONDS_PER_MINUTE;

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
		if (value > 0)
		{
			return getPositivString(value);
		}

		return getNegativeString(value);
	}

	public static String getPositivString(double value)
	{
		double delay = value;

		final int hour = (int) delay / SECONDS_PER_HOUR;
		delay -= hour * SECONDS_PER_HOUR;

		final int min = (int) delay / SECONDS_PER_MINUTE;
		delay -= min * SECONDS_PER_MINUTE;

		if (hour > 0)
		{
			return hour + "h " + min + "m " + Math.round(delay) + "s";
		}

		if (min > 0)
		{
			return min + "m " + Math.round(delay) + "s";
		}

		return Math.round(delay * 100) / 100d + "s";
	}

	public static String getNegativeString(double value)
	{
		return "-" + getPositivString(Math.abs(value));
	}

	public static double difference(Delay delay1, Delay delay2)
	{
		return delay1.value - delay2.value;
	}
}
