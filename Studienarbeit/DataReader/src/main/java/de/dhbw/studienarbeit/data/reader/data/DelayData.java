package de.dhbw.studienarbeit.data.reader.data;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class DelayData<T>
{
	private final DelayMaximum delayMaximum;
	private final DelayAverage delayAverage;
	private final CountData count;
	protected final T value;

	public DelayData(DelayMaximum delayMaximum, DelayAverage delayAverage, CountData count, T value)
	{
		super();
		this.value = value;
		this.count = count;
		this.delayMaximum = delayMaximum;
		this.delayAverage = delayAverage;
	}

	public final DelayMaximum getMaximum()
	{
		return delayMaximum;
	}

	public final DelayAverage getAverage()
	{
		return delayAverage;
	}

	public final T getValue()
	{
		return value;
	}

	public String getValueString()
	{
		return value.toString();
	}

	public CountData getCount()
	{
		return count;
	}

	@Override
	public String toString()
	{
		return new StringBuilder().append(value).append(": avg: ").append(delayAverage).append(", max: ")
				.append(delayMaximum).append(", count: ").append(count).toString();
	}
}
