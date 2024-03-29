package de.dhbw.studienarbeit.data.reader.data;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class CancelledStopsData<T>
{
	private final T value;
	private final CountData total;
	private final CountData cancelled;
	private final Percentage percentage;

	public CancelledStopsData(T value, CountData total, CountData cancelled)
	{
		super();
		this.value = value;
		this.total = total;
		this.cancelled = cancelled;
		this.percentage = new Percentage(total, cancelled);
	}

	public T getValue()
	{
		return value;
	}

	@Deprecated
	public CountData getCount()
	{
		return total;
	}

	public Percentage getPercentage()
	{
		return percentage;
	}

	public CountData getTotal()
	{
		return total;
	}

	public CountData getCancelled()
	{
		return cancelled;
	}

	@Override
	public String toString()
	{
		return value.toString() + ": " + percentage.toString();
	}
}
