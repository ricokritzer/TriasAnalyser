package de.dhbw.studienarbeit.data.reader.data.request;

import java.util.Objects;

import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class DelayCountData implements Comparable<DelayCountData>
{
	private final Delay delay;
	private final CountData count;

	public DelayCountData(Delay delay, CountData count)
	{
		super();
		this.delay = delay;
		this.count = count;
	}

	public Delay getDelay()
	{
		return delay;
	}

	public CountData getCount()
	{
		return count;
	}

	public double getDelayValue()
	{
		return delay.getValue();
	}

	public int getDelayInMinutes()
	{
		return (int) Math.round(delay.getValue() / 60);
	}

	public long getCountValue()
	{
		return count.getValue();
	}

	@Override
	public int compareTo(DelayCountData o)
	{
		return this.delay.compareTo(o.delay);
	}

	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(delay, count);
	}

	@Override
	public String toString()
	{
		return delay.toString() + ": " + count.toString();
	}
}
