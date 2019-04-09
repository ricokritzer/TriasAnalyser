package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

public class Humidity
{
	private final int value;

	public Humidity(int value)
	{
		super();
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return new StringBuilder().append(value).append("%").toString();
	}
}
