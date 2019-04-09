package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

public class Clouds
{
	private final long value;

	public Clouds(long clouds)
	{
		super();
		this.value = clouds;
	}

	public long getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return new StringBuilder().append(value).append("%").toString();
	}

}
