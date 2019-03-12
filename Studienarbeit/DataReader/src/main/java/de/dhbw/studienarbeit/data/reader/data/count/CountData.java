package de.dhbw.studienarbeit.data.reader.data.count;

public class CountData
{
	public static final CountData UNABLE_TO_COUNT = new CountData(-1);

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
	public String toString()
	{
		return Long.toString(value);
	}
}
