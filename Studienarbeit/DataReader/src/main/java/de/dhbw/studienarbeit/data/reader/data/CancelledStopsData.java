package de.dhbw.studienarbeit.data.reader.data;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class CancelledStopsData<T>
{
	private final T value;
	private final CountData count;

	public CancelledStopsData(T value, CountData count)
	{
		super();
		this.value = value;
		this.count = count;
	}

	public T getValue()
	{
		return value;
	}

	public CountData getCount()
	{
		return count;
	}

	@Override
	public String toString()
	{
		return value.toString() + ": " + count.toString();
	}
}
