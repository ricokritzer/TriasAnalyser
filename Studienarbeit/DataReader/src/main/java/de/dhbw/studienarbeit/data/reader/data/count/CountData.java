package de.dhbw.studienarbeit.data.reader.data.count;

import java.util.Objects;

public class CountData
{
	public static final CountData UNABLE_TO_COUNT = new CountData(0);

	private final long value;

	public CountData(long value)
	{
		super();
		this.value = value;
	}

	public long getValue()
	{
		return value;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof CountData)
		{
			return ((CountData) obj).getValue() == value;
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
		return Long.toString(value);
	}

	public CountData difference(CountData compared)
	{
		return new CountData(Math.abs(compared.value - this.value));
	}
}
