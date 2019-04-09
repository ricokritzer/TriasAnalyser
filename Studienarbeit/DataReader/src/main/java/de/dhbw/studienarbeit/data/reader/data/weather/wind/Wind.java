package de.dhbw.studienarbeit.data.reader.data.weather.wind;

public class Wind
{
	private final int value;

	public Wind(int value)
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
		return new StringBuilder().append(value).append("km/h").toString();
	}
}
