package de.dhbw.studienarbeit.data.reader.data.request;

import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class DelayCountData
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

	public long getCountValue()
	{
		return count.getValue();
	}
}
