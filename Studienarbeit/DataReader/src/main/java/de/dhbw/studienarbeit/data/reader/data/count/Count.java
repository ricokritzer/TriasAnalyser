package de.dhbw.studienarbeit.data.reader.data.count;

public class Count
{
	public static final Count UNABLE_TO_COUNT = new Count(-1);

	private final long value;

	public Count(long value)
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
