package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

public class Temperature
{
	private final int value;

	public Temperature(int value)
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
		return new StringBuilder().append(value).append("°C").toString();
	}
}
